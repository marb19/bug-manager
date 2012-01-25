/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewProjectController.java 319 2010-11-12 13:30:55Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-12 07:30:55 -0600 (Fri, 12 Nov 2010) $
 * Last Version      : $Revision: 319 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.controllers;

import java.util.Date;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
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
 * @version $Revision: 319 $
 */
@Scope("request")
@Controller
@RequestMapping("/newProject")
public class NewProjectController extends BaseController {

    @Autowired
    private ProjectManagementBizOp projectMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged(adminRequired = true)
    public String displayForm(ModelMap model) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String newProject(
            @RequestParam("projectName") String projectName,
            @RequestParam("projectDescription") String projectDescription,
            @RequestParam("projectDueDate") Date projectDueDate,
            @RequestParam("projectPlannedDate") Date projectPlannedDate,
            ModelMap model) {

        if (projectName.equals("")) {
            throw new ControllerException("Can't create project with empty name");
        }
        if (projectDescription.equals("")) {
            throw new ControllerException("Can't create project with empty description");
        }
        
        int projectId = projectMgr.createProject(projectName, projectDescription,
                projectDueDate, projectPlannedDate);

        //model.put("projectId", projectId);
        return "redirect:listPhases.do?project_id=" + projectId;
    }
}
