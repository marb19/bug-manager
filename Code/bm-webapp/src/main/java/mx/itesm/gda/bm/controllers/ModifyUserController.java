/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ModifyUserController.java 314 2010-11-12 01:13:22Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-11 19:13:22 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 314 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.Map;
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
 * @version $Revision: 314 $
 */
@Scope("request")
@Controller
@RequestMapping("/modifyUser")
public class ModifyUserController extends BaseController {

    @Autowired
    private UserManagementBizOp userMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged
    public String getUserData(@RequestParam("userName") String userName,
            ModelMap model) {
        Map<String, ?> u = userMgr.getUser(userName);
        model.put("u", u);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String updateUser(
            @RequestParam("userName") String userName,
            @RequestParam(value = "permissions", defaultValue = "10") int permissions,
            @RequestParam("fullName") String fullName,
            @RequestParam("email") String email,
            ModelMap model) {

        if(userName.equals("") || fullName.equals("") || email.equals("")) {
            throw new ControllerException("No se admiten campos vacios");
        }
        if(!WebUtils.checkEmail(email)) {
            throw new ControllerException("Formato de email incorrecto");
        }
        
        userMgr.modifyUser(userName, permissions, fullName, email);

        return "redirect:listUsers.do";
    }

}
