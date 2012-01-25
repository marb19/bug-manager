/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ProjectManagementBizOpImpl.java 308 2010-11-10 00:58:54Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-09 18:58:54 -0600 (Tue, 09 Nov 2010) $
 * Last Version      : $Revision: 308 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.BizException;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;

/**
 *
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 308 $
 */
@Scope("prototype")
@Component
public class ProjectManagementBizOpImpl extends AbstractBizOp implements
        ProjectManagementBizOp {

    @Autowired
    UserDAO userDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private DefectDAO defectDAO;

    @Autowired
    private MessageSourceAccessor msgSource;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> retrieveProjects(String userName) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        User user = userDAO.findByUserName(userName);

        if(user != null) {
            List<Project> projects;

            if(user.isAdministrator()) {
                projects = projectDAO.getAll();
            } else {
                projects = projectDAO.searchByUserNameEnrolled(
                        user.getUserName());
            }

            for(Project project : projects) {
                Map<String, Object> p = new HashMap<String, Object>();
                p.put("projectId", project.getProjectId());
                p.put("projectName", project.getProjectName());
                p.put("projectDescription", project.getProjectDescription());
                p.put("projectDueDate", project.getProjectDueDate());
                p.put("projectPlannedDate", project.getProjectPlannedDate());
                p.put("projectActualPhase", project.getActualPhase());
                p.put("empty", project.getTasks().isEmpty());
                ret.add(p);
            }
        }

        return ret;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public int createProject(String projectName, String projectDescription,
            Date projectDueDate, Date projectPlannedDate) {

        Project p = new Project();
        p.setProjectName(projectName);
        p.setProjectDescription(projectDescription);
        p.setProjectDueDate(projectDueDate);
        p.setProjectPlannedDate(projectPlannedDate);
        p.setActualPhase(0);
        projectDAO.save(p);
        return p.getProjectId();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void updateProject(int projectId, String projectName,
            String projectDescription, Date projectDueDate,
            Date projectPlannedDate) {

        Project p = new Project();
        p.setProjectId(projectId);
        p.setProjectName(projectName);
        p.setProjectDescription(projectDescription);
        p.setProjectDueDate(projectDueDate);
        p.setProjectPlannedDate(projectPlannedDate);
        projectDAO.update(p);

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteProject(int project_id) {
        Project p = projectDAO.findById(project_id);
        if(!p.getTasks().isEmpty()) {
            throw new BizException(msgSource.getMessage(
                    "ProjectManagementBizOp.cannotDeleteNonEmptyProject"));
        }
        projectDAO.delete(p);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Map<String, ?> getProject(int project_id) {
        Map<String, Object> p = new HashMap<String, Object>();
        Project project = new Project();
        project = projectDAO.findById(project_id);

        if(project != null) {
            p.put("projectId", project.getProjectId());
            p.put("projectName", project.getProjectName());
            p.put("projectDescription", project.getProjectDescription());
            p.put("projectDueDate", project.getProjectDueDate());
            p.put("projectPlannedDate", project.getProjectPlannedDate());
            p.put("projectActualPhase", project.getActualPhase());
            p.put("empty", project.getTasks().isEmpty());
        }
        return p;
    }

    @Override
    public List<Map<String, ?>> getUsers(int projectId) {
        
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        
        Project project = new Project();
        project = projectDAO.findById(projectId);
        
        for(User user : project.getUsers()) {
            Map<String, Object> u = new HashMap<String, Object>();
            u.put("userName", user.getUserName());
            u.put("isAdministrator", user.isAdministrator());
            u.put("permissions", user.getPermissions());
            u.put("fullName", user.getFullName());
            u.put("email", user.getEmail());
            boolean isEmpty=(taskDAO.getTasksByProjectAndUser(user.getUserName(),projectId).isEmpty() &&
                    defectDAO.searchByProjectAndUser(projectId,user.getUserName()).isEmpty());
            u.put("isEmpty", isEmpty);
            ret.add(u);
        }

        return ret;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addUser(int projectId, String userName){
        Project project = new Project();
        project = projectDAO.findById(projectId);
        User user = userDAO.findByUserName(userName);

        List<User> users = project.getUsers();
        users.add(user);
        project.setUsers(users);
        projectDAO.update(project);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeUser(int projectId, String userName){
        Project project = new Project();
        project = projectDAO.findById(projectId);
        User user = userDAO.findByUserName(userName);

        List<User> users = project.getUsers();
        users.remove(user);
        project.setUsers(users);
        projectDAO.update(project);
    }
}
