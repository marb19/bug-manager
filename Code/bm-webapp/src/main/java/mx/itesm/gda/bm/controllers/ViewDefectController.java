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
import mx.itesm.gda.bm.biz.DefectManagementBizOp;
import mx.itesm.gda.bm.biz.DefectTypeManagementBizOp;
import mx.itesm.gda.bm.biz.PhaseManagementBizOp;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
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
@RequestMapping("/viewDefect")
public class ViewDefectController extends BaseController {

    @Autowired
    private UserLoginSession session;

    @Autowired
    private TaskManagementBizOp taskMgr;

    @Autowired
    private UserManagementBizOp userMgr;
    
    @Autowired
    private DefectManagementBizOp defectMgr;
    
    @Autowired
    private PhaseManagementBizOp phaseMgr;
    
    @Autowired
    private ProjectManagementBizOp projMgr;
    
    @Autowired
    private DefectTypeManagementBizOp defectTypeMgr;
    
    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged()
    public String displayForm(@RequestParam("project_id") int projectID,
            @RequestParam("defect_id") int defectID,
            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        if(defectID <= 0){
            throw new ControllerException("ID de defecto fuera de rango");
        }

        Map<String, ?> defect = defectMgr.getDefect(defectID);
        List<Map<String, ?>> defects = defectMgr.retrieveProjectDefects(projectID);
        List<Map<String, ?>> users = userMgr.retrieveUsers();
        List<Map<String, ?>> comments = defectMgr.retrieveComments(defectID);
        
        List<Map<String, ?>> phases = phaseMgr.retrieveProjectPhases(projectID);
        List<Map<String, ?>> tasks = taskMgr.retrieveTasks(projectID);
        List<Map<String, ?>> defectTypes = defectTypeMgr.retrieveDefectTypes();

        int referenceID = 0;
        try{
            referenceID = (Integer)defect.get("reference");
        }
        catch(Exception nfe){
            referenceID = 0;
        }
        if(referenceID != 0){
            Map<String, ?> reference = defectMgr.getDefect(referenceID);
            model.put("reference", reference);
        }
        else{
            model.put("reference", "");
        }
        
        model.put("users", users);
        model.put("defect", defect);
        model.put("defects", defects);
        model.put("phases", phases);
        model.put("tasks", tasks);
        model.put("defectTypes", defectTypes);
        model.put("comments", comments);
        model.put("project_id", projectID);
        model.put("defect_id", defectID);

        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged()
    public String modifyDefectAdmin(
            @RequestParam("project_id") int projectID,
            @RequestParam("defect_id") int defectID,
            @RequestParam("defectName") String defectName,
            @RequestParam("defectDesc") String defectDescription,
            @RequestParam("detectionPhase") int detectionPhase,
            @RequestParam("detectionTask") int detectionTask,
            @RequestParam(value = "inyectionPhase", defaultValue = "0") int inyectionPhase,
            @RequestParam(value = "inyectionTask", defaultValue = "0") int inyectionTask,
            @RequestParam(value = "remotionPhase", defaultValue = "0") int remotionPhase,
            @RequestParam(value = "remotionTask", defaultValue = "0") int remotionTask,
            @RequestParam(value = "defectTrigger", defaultValue = "") String defectTrigger,
            @RequestParam(value = "impact", defaultValue = "") String impact,
            @RequestParam(value = "defectType", defaultValue = "0") int defectType,
            @RequestParam(value = "qualifier", defaultValue = "") String qualifier,
            @RequestParam(value = "age", defaultValue = "") String age,
            @RequestParam(value = "source", defaultValue = "") String source,
            @RequestParam(value = "reference", defaultValue = "0") int reference,
            @RequestParam(value = "investedHours", defaultValue = "0") int investedHours,
            @RequestParam(value = "assignedUser", defaultValue = "") String assignedUser,
            @RequestParam("status") String status,
            @RequestParam("newComment") String newComment,
            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        if(defectID <= 0){
            throw new ControllerException("ID de defecto fuera de rango");
        }

        if(defectName.equals("")){
            throw new ControllerException("El nombre del defecto no puede estar vacío");
        }
        
        if(defectDescription.equals("")){
            throw new ControllerException("La descripción del defecto no puede estar vacía");
        }
        
        if(detectionPhase <= 0){
            throw new ControllerException("ID de Fase fuera de rango");
        }
        
        if(detectionTask <= 0){
            throw new ControllerException("ID de Tarea fuera de rango");
        }
        
        if(status.equals("")){
            throw new ControllerException("El estatus no puede estar vacío");
        }
        
        if(!assignedUser.equals("") && userMgr.getUser(assignedUser) == null) {
            throw new ControllerException("Usuario inexistente");
        }
        
        if(defectID == reference){
            throw new ControllerException("Un defecto no se puede tener a sí mismo como referencia.");
        }
        
        int defect = defectMgr.modifyDefect(defectID, defectName, defectDescription, 
                detectionPhase, detectionTask, inyectionPhase, inyectionTask, 
                remotionPhase, remotionTask, defectTrigger, impact, defectType, qualifier, 
                age, source, reference, investedHours, assignedUser, status);
        
        if(!newComment.equals("")) {
            defectMgr.addComment(defectID, session.getLoggedUserName(), newComment);
        }

        return "redirect:listDefects.do?project_id=" + projectID;
    }

}
