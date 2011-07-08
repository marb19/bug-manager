/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: LoginController.java 282 2010-10-31 03:07:22Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 22:07:22 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 282 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import mx.itesm.gda.bm.biz.UserAccessBizOp;
import mx.itesm.gda.bm.session.UserLoginSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 282 $
 */
@Controller
@RequestMapping("/userLogin")
@Scope("request")
public class LoginController extends BaseController {

    private static final Log LOGGER = LogFactory.getLog(
            LoginController.class);

    @Autowired
    private UserAccessBizOp userAccessBizOp;

    @Autowired
    private UserLoginSession loginSession;

    @Autowired
    private MessageSourceAccessor msgSource;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    public String displayLoginForm(ModelMap model) {
        if(loginSession.getLoggedUserName() != null) {
            return "redirect:listProjects.do";
        }
        model.put("loginMessage", loginSession.getLoginMessage());
        loginSession.setLoginMessage(null);
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, params = { "op=logout" })
    @Transactional(readOnly = true)
    public String logout(ModelMap model) {
        loginSession.setLoggedUserName(null);
        return "redirect:userLogin.do";
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    public String validateLoginForm(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            ModelMap model) {

        loginSession.setLoggedUserName(null);

        if(!userAccessBizOp.validateUserLogin(username, password)) {
            model.put("loginMessage", msgSource.getMessage(
                    "LoginController.invalidUser"));
            return null;
        }

        loginSession.setLoggedUserName(username);

        return "redirect:listProjects.do";
    }

}
