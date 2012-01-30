/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListTasksController.java 308 2010-11-10 00:58:54Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-09 18:58:54 -0600 (Tue, 09 Nov 2010) $
 * Last Version      : $Revision: 308 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.PhaseManagementBizOp;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
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
 * @version $Revision: 308 $
 */
@Scope("request")
@Controller
@RequestMapping("/listPhases")
public class ListPhasesController {

    @Autowired
    private PhaseManagementBizOp phaseMgr;

    @Autowired
    private ProjectManagementBizOp projMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged()
    public String listPhases(@RequestParam("project_id") int projectID,
                            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        List<Map<String, ?>> phases = phaseMgr.retrieveProjectPhases(projectID);
        Map<String, ?> project = projMgr.getProject(projectID);
        List<String> phaseTypes = phaseMgr.getTypes();
        
        model.put("phases", phases);
        model.put("project", project);
        model.put("project_id", projectID);
        model.put("phaseTypes", phaseTypes);
        
        return null;
    }
    
    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String newPhase(
            @RequestParam(value = "phaseName", defaultValue="") String phaseName,
            @RequestParam(value = "project_id", defaultValue = "0") int projectID,
            @RequestParam(value = "phaseDescription", defaultValue = "") String description,
            @RequestParam(value = "phaseOrder", defaultValue = "-1") int phaseOrder,
            @RequestParam(value = "phaseType", defaultValue = "") String phaseType,
            @RequestParam(value = "cycleType", defaultValue = "0") int cycleType,
            ModelMap model) {
        
        if(cycleType == 0){
            if(phaseName.equals("")){
                throw new ControllerException("No se admiten campos vacios");
            }

            if(projectID <= 0){
                throw new ControllerException("ID de proyecto fuera de rango");
            }

            if(phaseOrder < 0){
                throw new ControllerException("Orden en el proyecto fuera de rango");
            }

            if(description.equals("")){
                throw new ControllerException("No se admiten campos vacios");
            }

            if(phaseType.equals("")){
                throw new ControllerException("No se admiten campos vacios");
            }

            boolean wrongType = true;
            for (String type : phaseMgr.getTypes()){
                if (phaseType.equals(type)){
                    wrongType=false;
                }
            }
            if (wrongType){
                throw new ControllerException("Tipo de fase inexistente");
            }

            phaseMgr.modifyOrder(projectID, phaseOrder + 1);

            int phase = phaseMgr.createPhase(phaseName, description, projectID, phaseOrder + 1, phaseType);

            return "redirect:listPhases.do?project_id=" + projectID;
        }
        else{
            
            if(projectID <= 0){
                throw new ControllerException("ID de proyecto fuera de rango");
            }
            
            if(cycleType != 1 && cycleType != 2){
                throw new ControllerException("Tipo de ciclo fuera de rango");
            }
            
            phaseMgr.autoCycle(projectID, cycleType);
            return "redirect:listPhases.do?project_id=" + projectID;
        }
    }
}
