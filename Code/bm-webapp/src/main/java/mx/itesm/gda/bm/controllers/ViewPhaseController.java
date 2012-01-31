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
@RequestMapping("/viewPhase")
public class ViewPhaseController extends BaseController {

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
            @RequestParam("phase_id") int phaseID,
            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        if(phaseID <= 0){
            throw new ControllerException("ID de fase fuera de rango");
        }

        Map<String, ?> phase = phaseMgr.getPhase(phaseID);
        List<Map<String, ?>> phases = phaseMgr.retrieveProjectPhases(projectID);
        List<String> phaseTypes = phaseMgr.getTypes();
        
        model.put("phase", phase);
        model.put("phases", phases);
        model.put("project_id", projectID);
        model.put("phase_id", phaseID);
        model.put("phaseTypes", phaseTypes);

        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged()
    public String modifyDefectAdmin(
            @RequestParam("project_id") int projectID,
            @RequestParam("phase_id") int phaseID,
            @RequestParam("phaseName") String phaseName,
            @RequestParam("phaseDesc") String phaseDescription,
            @RequestParam("phaseType") String phaseType,
            @RequestParam("phaseOrder") int projectOrder,
            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }
        
        if(phaseID <= 0){
            throw new ControllerException("ID de fase fuera de rango");
        }
        
        if(phaseName.equals("")){
            throw new ControllerException("No se admiten campos vacíos");
        }
        
        if(phaseDescription.equals("")){
            throw new ControllerException("No se admiten campos vacíos");
        }
        
        if(phaseType.equals("")){
            throw new ControllerException("No se admiten campos vacíos");
        }
        
        if(projectOrder < 0){
            throw new ControllerException("Orden en el proyecto fuera de rango");
        }

        phaseMgr.modifyPhase(phaseID, phaseName, phaseDescription, projectOrder, phaseType);

        return "redirect:listPhases.do?project_id=" + projectID;
    }

}
