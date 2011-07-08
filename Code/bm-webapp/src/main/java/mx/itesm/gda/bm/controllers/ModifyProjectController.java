/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ModifyProjectController.java 319 2010-11-12 13:30:55Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-12 07:30:55 -0600 (Fri, 12 Nov 2010) $
 * Last Version      : $Revision: 319 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.controllers;

import java.util.Date;
import java.util.Map;
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
@RequestMapping("/modifyProject")
public class ModifyProjectController extends BaseController {

    @Autowired
    private ProjectManagementBizOp projectMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged
    public String getProjectData(@RequestParam("project_id") int project_id,
            ModelMap model) {
        if (project_id <= 0) {
            throw new ControllerException("Illegal Project_ID (Less or equal than zero)");
        }

        Map<String, ?> p = projectMgr.getProject(project_id);
        model.put("p", p);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String updateProject(
            @RequestParam("projectName") String projectName,
            @RequestParam("projectDescription") String projectDescription,
            @RequestParam("projectDueDate") Date projectDueDate,
            @RequestParam("projectPlannedDate") Date projectPlannedDate,
            @RequestParam("projectId") int projectId,
            ModelMap model) {

        if (projectName.equals("")) {
            throw new ControllerException("Can't modify project with empty name");
        }
        if (projectDescription.equals("")) {
            throw new ControllerException("Can't modify project with empty description");
        }
        if (projectId <= 0) {
            throw new ControllerException("Illegal projectId (Less or equal to zero)");
        }

        projectMgr.updateProject(projectId, projectName, projectDescription,
                projectDueDate, projectPlannedDate);

        return "redirect:listProjects.do";
    }
}
