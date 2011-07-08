/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewPasswordRequestController.java 282 2010-10-31 03:07:22Z alex.vc@gmail.com $
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
import mx.itesm.gda.bm.utils.UserLogged;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 282 $
 */
@Controller
@Scope("request")
public class NewPasswordRequestController extends BaseController {

    private static final Log LOGGER = LogFactory.getLog(
            NewPasswordRequestController.class);

    @Autowired
    private UserAccessBizOp userAccessBizOp;

    @Autowired
    private UserLoginSession loginSession;

    @RequestMapping(value = "/requestNewPassword", method = RequestMethod.GET)
    @UserLogged(autoLogout = true)
    public void requestNewPassword() {
    }

    @RequestMapping(value = "/requestNewPassword", method = RequestMethod.POST)
    @Transactional
    @UserLogged(autoLogout = true)
    public String doRequestNewPassword(@RequestParam("email") String email,
            ModelMap model) {
        userAccessBizOp.initiatePasswordRecovery(email);
        return "redirect:newPasswordRequested.do";
    }

    @RequestMapping(value = "/newPasswordRequested", method = RequestMethod.GET)
    @UserLogged(autoLogout = true)
    public void newPasswordRequested() {
    }

    @RequestMapping(value = "/newPassword", method = RequestMethod.GET)
    @Transactional
    @UserLogged(autoLogout = true)
    public String newPassword(@RequestParam("ticket") String ticket,
            ModelMap model) {
        if(!userAccessBizOp.validatePasswordRecoveryTicket(ticket)) {
            return "redirect:ticketNotFound.do";
        }
        return null;
    }

    @RequestMapping(value = "/ticketNotFound", method = RequestMethod.GET)
    @UserLogged(autoLogout = true)
    public void ticketNotFound() {
    }

}
