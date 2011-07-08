/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ModifyUserPasswordController.java 319 2010-11-12 13:30:55Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-12 07:30:55 -0600 (Fri, 12 Nov 2010) $
 * Last Version      : $Revision: 319 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.Map;
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
 * @author $Author: inzunzo $
 * @version $Revision: 319 $
 */
@Scope("request")
@Controller
@RequestMapping("/modifyUserPassword")
public class ModifyUserPasswordController extends BaseController {

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
    public String updateUserPassword(
            @RequestParam("userName") String userName,
            @RequestParam("password") String password,
            ModelMap model) {

        if(password.equals("")) {
            throw new ControllerException("No se admite un password vacio");
        }
        if(userName.equals("")) {
            throw new ControllerException("El campo 'nombre de usuario' esta vacío");
        }        
        userMgr.modifyUserPassword(userName, password);

        return "redirect:listUsers.do";
    }

}
