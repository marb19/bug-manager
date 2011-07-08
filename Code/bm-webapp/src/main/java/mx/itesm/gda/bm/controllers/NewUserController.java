/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
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

import mx.itesm.gda.bm.biz.UserManagementBizOp;
import mx.itesm.gda.bm.utils.UserLogged;
import mx.itesm.gda.bm.utils.WebUtils;
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
@RequestMapping("/newUser")
public class NewUserController {

    @Autowired
    private UserManagementBizOp userMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged(adminRequired = true)
    public String displayForm(ModelMap model) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String newUser(
            @RequestParam("userName") String userName,
            @RequestParam(value = "isAdministrator", defaultValue = "false") boolean isAdministrator,
            @RequestParam("fullName") String fullName,
            @RequestParam("password") String password,
            @RequestParam("email") String email,
            ModelMap model) {
        
        if (userName.equals("") || fullName.equals("") || password.equals("")
                || email.equals("")) {
            throw new ControllerException("No se admiten campos vacios");
        }
        if (!WebUtils.checkEmail(email)) {
            throw new ControllerException("Formato de email incorrecto");
        }
        if (userMgr.getUser(userName) != null) {
            throw new ControllerException("Nombre de usuario duplicado");
        }
        if (userMgr.getUserByEmail(email) != null) {
            throw new ControllerException("Email duplicado");
        }
        String user = userMgr.createUser(userName, isAdministrator, fullName,
                password, email);

        return "redirect:listUsers.do";
    }
}
