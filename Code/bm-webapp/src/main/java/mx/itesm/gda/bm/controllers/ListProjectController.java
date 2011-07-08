/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListProjectController.java 282 2010-10-31 03:07:22Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 22:07:22 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 282 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.List;
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
import mx.itesm.gda.bm.session.UserLoginSession;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 282 $
 */
@Scope("request")
@Controller
@RequestMapping("/listProjects")
public class ListProjectController extends BaseController {

    @Autowired
    private ProjectManagementBizOp projectMgr;

    @Autowired
    private UserLoginSession loginSession;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged
    public String listProjects(ModelMap model) {
        List<Map<String, ?>> projects =
                projectMgr.retrieveProjects(loginSession.getLoggedUserName());
        model.put("projects", projects);
        return null;
    }

}
