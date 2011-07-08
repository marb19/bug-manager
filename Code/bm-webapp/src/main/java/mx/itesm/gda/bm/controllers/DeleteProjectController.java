/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DeleteProjectController.java 311 2010-11-11 03:02:03Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-10 21:02:03 -0600 (Wed, 10 Nov 2010) $
 * Last Version      : $Revision: 311 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.controllers;

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
 * @version $Revision: 311 $
 */
@Scope("request")
@Controller
@RequestMapping("/deleteProject")
public class DeleteProjectController extends BaseController {

    @Autowired
    private ProjectManagementBizOp projectMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged(adminRequired = true)
    public String deleteProject(
            @RequestParam("project_id") int project_id,
            ModelMap model) {

        if (project_id <= 0) {
            throw new ControllerException("Illegal project_id (Less or equal than zero)");
        }

        projectMgr.deleteProject(project_id);
        return "redirect:listProjects.do";
    }
}
