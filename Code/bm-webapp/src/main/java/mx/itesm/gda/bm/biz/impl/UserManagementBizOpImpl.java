/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserManagementBizOpImpl.java 347 2010-11-18 03:01:01Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-17 21:01:01 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 347 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.BizException;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 347 $
 */
@Scope("prototype")
@Component
public class UserManagementBizOpImpl extends AbstractBizOp implements
        UserManagementBizOp {

    @Autowired
    UserDAO userDAO;
    
    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    ProjectManagementBizOp projectMgr;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> retrieveUsers() {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        for(User user : userDAO.getAll()) {
            Map<String, Object> u = new HashMap<String, Object>();
            u.put("userName", user.getUserName());
            u.put("isAdministrator", user.isAdministrator());
            u.put("permissions", user.getPermissions());
            u.put("fullName", user.getFullName());
            u.put("email", user.getEmail());
            boolean isEmpty=(user.getAssignedTasks().isEmpty() && 
                    user.getAuthoredComments().isEmpty() &&
                    user.getAuthoredCompletionReports().isEmpty() &&
                    user.getProjects().isEmpty());
            u.put("isEmpty", isEmpty);
            ret.add(u);
        }

        return ret;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public String createUser(String userName, int permissions, String fullName, String password, String email) {
        User u = new User();
        u.setUserName(userName);
        u.setPermissions(permissions);
        u.setFullName(fullName);
        u.setPassword(password);
        u.setEmail(email);
        userDAO.save(u);
        return u.getUserName();
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteUser(String userName) {
        User u = userDAO.findByUserName(userName);
        if (u==null){
            throw new BizException("Cannot delete non-existing user");
        }
        if(!(u.getAssignedTasks().isEmpty() && u.getAuthoredComments().isEmpty() && u.getAuthoredCompletionReports().isEmpty() && u.getProjects().isEmpty())) {
            throw new BizException("Cannot delete non-empty user");
        }
        userDAO.delete(u);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void modifyUser(String userName, int permissions, String fullName, String email){
        User u = userDAO.findByUserName(userName);
        if (u==null){
            throw new BizException("Cannot modify non-existing user");
        }
        u.setPermissions(permissions);
        u.setFullName(fullName);
        u.setEmail(email);
        userDAO.update(u);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void modifyUserPassword(String userName, String password){
        User u = userDAO.findByUserName(userName);
        if (u==null){
            throw new BizException("Cannot modify password of a non-existing user");
        }
        u.setPassword(password);
        userDAO.update(u);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Map<String, ?> getUser(String userName) {
        User user = userDAO.findByUserName(userName);
        if (user==null){
            return null;
        }
        Map<String, Object> u = new HashMap<String, Object>();
        u.put("userName", user.getUserName());
        u.put("isAdministrator", user.isAdministrator());
        u.put("permissions", user.getPermissions());
        u.put("fullName", user.getFullName());
        u.put("email", user.getEmail());
        return u;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Map<String, ?> getUserByEmail(String email) {
        User user = userDAO.findByEmail(email);
        if (user==null){
            return null;
        }
        Map<String, Object> u = new HashMap<String, Object>();
        u.put("userName", user.getUserName());
        u.put("isAdministrator", user.isAdministrator());
        u.put("permissions", user.getPermissions());
        u.put("fullName", user.getFullName());
        u.put("email", user.getEmail());
        return u;
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> getProjects(String userName){
        
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        User user = userDAO.findByUserName(userName);

        if(user != null) {
            List<Project> projects;
            projects = user.getProjects();

            for(Project project : projects) {
                Map<String, Object> p = new HashMap<String, Object>();
                p.put("projectId", project.getProjectId());
                p.put("projectName", project.getProjectName());
                p.put("projectDescription", project.getProjectDescription());
                p.put("projectDueDate", project.getProjectDueDate());
                p.put("projectPlannedDate", project.getProjectPlannedDate());
                p.put("empty", project.getTasks().isEmpty());
                ret.add(p);
            }
        }

        return ret;
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void addProject(int projectId, String userName){
        Project project = projectDAO.findById(projectId);
        User user = userDAO.findByUserName(userName);

        List<Project> projects = user.getProjects();
        projects.add(project);
        user.setProjects(projects);
        userDAO.update(user);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeProject(int projectId, String userName){
        Project project = projectDAO.findById(projectId);
        User user = userDAO.findByUserName(userName);

        List<Project> projects = user.getProjects();
        projects.remove(project);
        user.setProjects(projects);
        userDAO.update(user);
    }

}
