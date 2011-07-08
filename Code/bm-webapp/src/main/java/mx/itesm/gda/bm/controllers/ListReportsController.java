/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListReportsController.java 349 2010-11-18 03:50:05Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-17 21:50:05 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 349 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
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
 * @version $Revision: 349 $
 */
@Scope("request")
@Controller
@RequestMapping("/listReports")
public class ListReportsController extends BaseController {

    @Autowired
    private ProjectManagementBizOp projectMgr;

    @Autowired
    private UserManagementBizOp userMgr;

    @Autowired
    private UserLoginSession loginSession;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged
    public String listReports(ModelMap model) {
        List<Map<String, ?>> projects =
                projectMgr.retrieveProjects(loginSession.getLoggedUserName());
        List<Map<String, ?>> users =
                userMgr.retrieveUsers();
        model.put("projects", projects);
        model.put("users", users);
        return null;
    }

}
