/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: CheckUserController.java 326 2010-11-15 18:19:59Z jpabluz@gmail.com $
 * Last Revised By   : $Author: jpabluz@gmail.com $
 * Last Checked In   : $Date: 2010-11-15 12:19:59 -0600 (Mon, 15 Nov 2010) $
 * Last Version      : $Revision: 326 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import mx.itesm.gda.bm.biz.UserManagementBizOp;
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
 * @author $Author: jpabluz@gmail.com $
 * @version $Revision: 326 $
 */
@Scope("request")
@Controller
@RequestMapping("/checkUser")
public class CheckUserController {

    @Autowired
    private UserManagementBizOp userMgr;

    @RequestMapping(method = RequestMethod.GET, params = { "check=user" })
    @Transactional
    @UserLogged(adminRequired = true)
    public String newUser(
            @RequestParam(value = "userName", defaultValue = "") String userName,
            ModelMap model) {
        if(userMgr.getUser(userName) == null) {
            model.put("output", "true");
        } else {
            model.put("output", "false");
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET, params = { "check=email" })
    @Transactional
    @UserLogged(adminRequired = true)
    public String newEmail(
            @RequestParam(value = "email", defaultValue = "") String email,
            ModelMap model) {
        if(userMgr.getUserByEmail(email) == null) {
            model.put("output", "true");
        } else {
            model.put("output", "false");
        }
        return null;
    }

}
