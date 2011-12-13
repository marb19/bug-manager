/* ***************************************************************************
 *   Copyright (c) 2011 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewUserController.java 312 2010-11-11 06:36:43Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-11 00:36:43 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 312 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.controllers;

import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.DefectManagementBizOp;
import mx.itesm.gda.bm.biz.DefectTypeManagementBizOp;
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
 * @author $Author: inzunzo $
 * @version $Revision: 312 $
 */
@Scope("request")
@Controller
@RequestMapping("/newDefect")
public class NewDefectController extends BaseController{

    @Autowired
    private DefectManagementBizOp defectMgr;

    @Autowired
    private TaskManagementBizOp taskMgr;
    
    @Autowired
    private ProjectManagementBizOp projMgr;
    
    @Autowired
    private PhaseManagementBizOp phaseMgr;
    
    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged(adminRequired = true)
    public String displayForm(@RequestParam("project_id") int projectID, ModelMap model) {
        
        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }
        
        Map<String, ?> project = projMgr.getProject(projectID);
        Map<String, ?> phase = phaseMgr.getPhase((Integer) project.get("projectActualPhase") );
        List<Map<String, ?>> tasks = taskMgr.retrieveTasks(projectID, (Integer)phase.get("phaseID"));
        model.put("tasks", tasks);
        model.put("project", project);
        model.put("phase", phase);
        model.put("project_id", projectID);
        
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String newDefect(
            @RequestParam("defectName") String defectName,
            @RequestParam("defectDescription") String defectDescription,
            @RequestParam("project_id") int projectID,
            @RequestParam("defectPhase") int defectPhase,
            @RequestParam("defectTask") int defectTask,
            ModelMap model) {
        
        if (defectName.equals("") || defectDescription.equals("")) {
            throw new ControllerException("No se admiten campos vacios");
        }
        
        int d = defectMgr.createDefect(defectName, defectDescription, projectID, defectPhase, defectTask);

        return "redirect:listDefects.do?project_id=" + projectID;
    }
}
