/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DeleteUserController.java 314 2010-11-12 01:13:22Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-11 19:13:22 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 314 $
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
 * @author $Author: inzunzo $
 * @version $Revision: 314 $
 */
@Scope("request")
@Controller
@RequestMapping("/deleteUser")
public class DeleteUserController extends BaseController {

    @Autowired
    private UserManagementBizOp userMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged(adminRequired = true)
    public String deleteUser(
            @RequestParam("userName") String userName,
            ModelMap model) {
        if(userName.equals("")){
        throw new ControllerException("Empty username");
        }
        userMgr.deleteUser(userName);
        return "redirect:listUsers.do";
    }

}
