/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ViewTaskController.java 343 2010-11-18 00:26:31Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-17 18:26:31 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 343 $
 *
 * Original Author   : Marco Antonio Rangel Bocardo
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.PhaseManagementBizOp;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
import mx.itesm.gda.bm.session.UserLoginSession;
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
 * @version $Revision: 343 $
 */
@Scope("request")
@Controller
@RequestMapping("/viewTask")
public class ViewTaskController extends BaseController {

    @Autowired
    private UserLoginSession session;

    @Autowired
    private TaskManagementBizOp taskMgr;

    @Autowired
    private UserManagementBizOp userMgr;

    @Autowired
    private PhaseManagementBizOp phaseMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged()
    public String displayForm(@RequestParam("project_id") int projectID,
            @RequestParam("task_id") int taskID,
            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        if(taskID <= 0){
            throw new ControllerException("ID de tarea fuera de rango");
        }

        Map<String, ?> task = taskMgr.getTask(taskID);
        List<Map<String, ?>> users = userMgr.retrieveUsers();
        List<Map<String, ?>> comments = taskMgr.retrieveComments(taskID);
        List<String> taskTypes = taskMgr.getTypes();
        List<Map<String, ?>> phases = phaseMgr.retrieveAllPhases();

        model.put("users", users);
        model.put("task", task);
        model.put("comments", comments);
        model.put("project_id", projectID);
        model.put("task_id", taskID);
        model.put("taskTypes", taskTypes);
        model.put("phases", phases);

        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged()
    public String modifyTaskAdmin(
            @RequestParam("project_id") int projectID,
            @RequestParam("task_id") int taskID,
            @RequestParam("taskName") String taskName,
            @RequestParam("taskDescription") String description,
            @RequestParam("assignedUser") String assignedUser,
            @RequestParam("status") String status,
            @RequestParam("estimatedHours") int estimatedHours,
            @RequestParam(value = "investedHours", defaultValue = "0") int investedHours,
            @RequestParam("remainingHours") int remainingHours,
            @RequestParam("startDate") Date startDate,
            @RequestParam("endDate") Date endDate,
            @RequestParam("newComment") String newComment,
            @RequestParam("taskType") String taskType,
            @RequestParam("phase_id") int phaseID,
            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        if(taskID <= 0){
            throw new ControllerException("ID de tarea fuera de rango");
        }

        if(taskName.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(description.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(assignedUser.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(status.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(!(status.equals("CANCELED") ||
           status.equals("COMPLETED") ||
           status.equals("NOT_STARTED") ||
           status.equals("STARTED"))){
            throw new ControllerException("Estatus inexistente");
        }

        if(phaseID <= 0){
            throw new ControllerException("ID de fase fuera de rango");
        }

        if(status.equals("COMPLETED") && remainingHours > 0){
            throw new ControllerException("No se puede completar una tarea si las horas restantes son mayores a 0");
        }
        
        if(estimatedHours <= 0){
            throw new ControllerException("Horas estimadas fuera de rango");
        }

        if(remainingHours < 0){
            throw new ControllerException("Las horas restantes no pueden ser menores que 0");
        }

        if(investedHours < 0){
            throw new ControllerException("No se pueden invertir horas negativas");
        }

        if(startDate == null){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(endDate == null){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(userMgr.getUser(assignedUser) == null) {
            throw new ControllerException("Usuario inexistente");
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

        Map<String, ?> t = taskMgr.getTask(taskID);
        if((investedHours + Integer.parseInt(""+t.get("investedHours"))) <= 0 && remainingHours <= 0){
            throw new ControllerException("No se puede completar una tarea sin haber invertido tiempo.");
        }

        int task = taskMgr.modifyTask(taskID, taskName, description,
                assignedUser, status, estimatedHours, investedHours, remainingHours, startDate,
                endDate, taskType, phaseID);

        if(!newComment.equals("")) {
            taskMgr.addComment(taskID, session.getLoggedUserName(), newComment);
        }
        
        return "redirect:listTasks.do?project_id=" + projectID;
    }

}
