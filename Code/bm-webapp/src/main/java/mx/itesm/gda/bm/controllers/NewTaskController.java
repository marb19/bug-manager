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
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
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

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged(adminRequired = true)
    public String displayForm(@RequestParam("project_id") int projectID,
            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        List<Map<String, ?>> users = userMgr.retrieveUsers();
        model.put("users", users);
        model.put("project_id", projectID);

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
            ModelMap model) {

        if(taskName.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
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
        
        int task = taskMgr.createTask(taskName, projectID, description,
                assignedUser, estimatedHours, startDate, endDate);

        return "redirect:listTasks.do?project_id=" + projectID;
    }

}
