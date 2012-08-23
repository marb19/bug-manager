/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectDensityUserReportBizOpImpl.java 279 2011-09-09 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2011-09-09 10:43:29 -0500 (Fri, 09 Sep 2011) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import mx.itesm.gda.bm.biz.DefectDensityReportBizOp;
import mx.itesm.gda.bm.model.*;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.session.UserLoginSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Component
public class DefectDensityReportBizOpImpl extends AbstractBizOp implements DefectDensityReportBizOp{

    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private DefectDAO defectDAO;
    
    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private UserLoginSession loginSession;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getDefectDensityReport(int level){
        String xmlData = null;

        switch(level){
            case 1:
                xmlData = getReportByUser();
                break;
            case 2:
                xmlData = getReportByProject();
                break;
            case 3:
                xmlData = getReportByCompany();
                break;
            default:
                xmlData = getReportByCompany();
                break;
        }

        return xmlData;
    }
    
    private String getReportByUser(){
        String xmlData = null;
        ArrayList<String> usersNames = new ArrayList<String>();
        ArrayList<Double> defectDensityByUsername = new ArrayList<Double>();
        List<User> allUsers = new ArrayList<User>();

        String userlogged = loginSession.getLoggedUserName();
        User loggedUser = userDAO.findByUserName(userlogged);
        if (loggedUser.getPermissions() >= 20){
            allUsers = userDAO.getAllDevelopers();
        }
        else{
            allUsers.add(loggedUser);
        }

        for(User user : allUsers){
            usersNames.add(user.getFullName());
            List<Defect> inyectedDefects = defectDAO.searchByState(DefectState.ACCEPTED);
            List<Defect> fixedDefects = defectDAO.searchByState(DefectState.FIXED);
            inyectedDefects.addAll(fixedDefects);
            int totalDefects = 0;
            for (Defect singleDefect : inyectedDefects){
                Task inyectionTask = singleDefect.getInyectionTask();
                if (inyectionTask != null){
                    User assignedUser = inyectionTask.getAssignedUser();
                    if (user.equals(assignedUser)){
                        totalDefects++;
                    }
                }
            }
            List<Task> tasksByUser = user.getAssignedTasks();
            int totalLOC = 0;
            for (Task singleTask : tasksByUser){
                if ((singleTask.getTaskType() == TaskType.DEVELOPMENT) &&
                        (singleTask.getTaskState() == TaskState.STARTED
                        || singleTask.getTaskState() == TaskState.COMPLETED)){
                    totalLOC += singleTask.getSize();
                }
            }
            if (totalLOC == 0){
                defectDensityByUsername.add(0.0);
            } else {
                defectDensityByUsername.add(roundNumber(1000 * ((double)totalDefects / (double)totalLOC)));
            }
        }
        xmlData += "<chart caption='Densidad de defectos por Usuario' xAxisName='Usuario' yAxisName='Densidad de defectos' bgAlpha='0,0'>";

        for(int i = 0; i < usersNames.size(); i++){
            xmlData += "<set label='" + usersNames.get(i) + "' value='" + defectDensityByUsername.get(i) + "' />";
        }

        xmlData+= "</chart>";
        return xmlData;
    }
    
    private String getReportByProject(){
        String xmlData = null;
        ArrayList<String> projectNames = new ArrayList<String>();
        ArrayList<Double> defectDensityByProject = new ArrayList<Double>();
        List<Project> allProjects = new ArrayList<Project>();

        String userlogged = loginSession.getLoggedUserName();
        User loggedUser = userDAO.findByUserName(userlogged);
        if (loggedUser.getPermissions() >= 20){
            allProjects = projectDAO.getAll();
        }
        else{
            allProjects = loggedUser.getProjects();
        }

        for(Project project : allProjects){
            projectNames.add(project.getProjectName());
            List<Defect> inyectedDefects = defectDAO.searchByState(DefectState.ACCEPTED);
            List<Defect> fixedDefects = defectDAO.searchByState(DefectState.FIXED);
            inyectedDefects.addAll(fixedDefects);
            int totalDefects = 0;
            for (Defect singleDefect : inyectedDefects){
                Task inyectionTask = singleDefect.getInyectionTask();
                if (inyectionTask != null){
                    Project currentProject = inyectionTask.getProject();
                    if (project.equals(currentProject)){
                        totalDefects++;
                    }
                }
            }
            List<Task> tasksByProject = project.getTasks();
            int totalLOC = 0;
            for (Task singleTask : tasksByProject){
                if ((singleTask.getTaskType() == TaskType.DEVELOPMENT) &&
                        (singleTask.getTaskState() == TaskState.STARTED
                        || singleTask.getTaskState() == TaskState.COMPLETED)){
                    totalLOC += singleTask.getSize();
                }
            }
            if (totalLOC == 0){
                defectDensityByProject.add(0.0);
            } else {
                defectDensityByProject.add(roundNumber(1000 * ((double)totalDefects / (double)totalLOC)));
            }
        }
        xmlData += "<chart caption='Densidad de defectos por Proyecto' xAxisName='Proyecto' yAxisName='Densidad de defectos' bgAlpha='0,0'>";

        for(int i = 0; i < projectNames.size(); i++){
            xmlData += "<set label='" + projectNames.get(i) + "' value='" + defectDensityByProject.get(i) + "' />";
        }

        xmlData+= "</chart>";
        return xmlData;
    }
    
    private String getReportByCompany(){
        String xmlData = null;
        Double defectDensity;
        
            List<Defect> inyectedDefects = defectDAO.searchByState(DefectState.ACCEPTED);
            List<Defect> fixedDefects = defectDAO.searchByState(DefectState.FIXED);
            inyectedDefects.addAll(fixedDefects);
            int totalDefects = inyectedDefects.size();
            
            ArrayList<Task> allTasks = (ArrayList<Task>) taskDAO.getAll();
            int totalLOC = 0;
            for (Task singleTask : allTasks){
                if ((singleTask.getTaskType() == TaskType.DEVELOPMENT) &&
                        (singleTask.getTaskState() == TaskState.STARTED
                        || singleTask.getTaskState() == TaskState.COMPLETED)){
                    totalLOC += singleTask.getSize();
                }
            }
            if (totalLOC == 0){
                defectDensity = 0.0;
            } else {
                defectDensity = roundNumber(1000 * ((double)totalDefects / (double)totalLOC));
            }
        
        xmlData += "<chart caption='Densidad de defectos de la Empresa' xAxisName='Empresa' yAxisName='Densidad de defectos' bgAlpha='0,0'>";
        xmlData += "<set label='" + "" + "' value='" + defectDensity + "' />";
        

        xmlData+= "</chart>";
        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
