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

import mx.itesm.gda.bm.biz.TemplateManagementBizOp;
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
@RequestMapping("/deleteTemplate")
public class DeleteTemplateController extends BaseController {

    @Autowired
    private TemplateManagementBizOp templateMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged(adminRequired = true)
    public String deleteUser(
            @RequestParam("userName") String userName,
            @RequestParam("templateId") int templateId,
            ModelMap model) {
        if(userName.equals("")){
        throw new ControllerException("Empty username");
        }
        if(templateId<0){
        throw new ControllerException("Invalid template ID");
        }

        templateMgr.deleteTemplate(templateId);
        return "redirect:listTemplates.do?userName="+userName;
    }

}
