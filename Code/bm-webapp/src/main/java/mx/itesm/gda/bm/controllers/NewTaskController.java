/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewTaskController.java 325 2010-11-15 05:46:37Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-14 23:46:37 -0600 (Sun, 14 Nov 2010) $
 * Last Version      : $Revision: 325 $
 *
 * Original Author   : Marco Antonio Rangel Bocardo
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.PhaseManagementBizOp;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.utils.UserLogged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 325 $
 */
@Scope("request")
@Controller
@RequestMapping("/newTask")
public class NewTaskController extends BaseController {

    @Autowired
    private TaskManagementBizOp taskMgr;

    @Autowired
    private UserManagementBizOp userMgr;

    @Autowired
    private ProjectManagementBizOp projMgr;

    @Autowired
    private PhaseManagementBizOp phaseMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged(adminRequired = true)
    public String displayForm(@RequestParam("project_id") int projectID,
            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        List<Map<String, ?>> users = projMgr.getUsers(projectID);
        List<String> taskTypes = taskMgr.getTypes();
        List<Map<String, ?>> phases = phaseMgr.retrieveProjectPhases(projectID);
        model.put("users", users);
        model.put("project_id", projectID);
        model.put("taskTypes", taskTypes);
        model.put("phases", phases);

        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String newTask(
            @RequestParam("taskName") String taskName,
            @RequestParam("project_id") int projectID,
            @RequestParam("taskDescription") String description,
            @RequestParam("assignedUser") String assignedUser,
            @RequestParam("taskEstimatedHours") int estimatedHours,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate,
            @RequestParam("taskType") String taskType,
            @RequestParam("phase_id") int phaseID,
            ModelMap model) {

        if(taskName.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        if(phaseID <= 0){
            throw new ControllerException("ID de fase fuera de rango");
        }

        if(description.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(assignedUser.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(userMgr.getUser(assignedUser) == null) {
            throw new ControllerException("Usuario inexistente");
        }

        if(estimatedHours <= 0){
            throw new ControllerException("Las horas estimadas no pueden ser 0 o menos.");
        }

        if(startDate == null){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(endDate == null){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(taskType.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        boolean wrongType = true;
        for (String type : taskMgr.getTypes()){
            if (taskType.equals(type)){
                wrongType=false;
            }
        }
        if (wrongType){
            throw new ControllerException("Tipo de tarea inexistente");
        }
        
        int task = taskMgr.createTask(taskName, projectID, description,
                assignedUser, estimatedHours, startDate, endDate, taskType, phaseID);

        return "redirect:listTasks.do?project_id=" + projectID;
    }

}
