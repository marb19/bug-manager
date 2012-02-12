/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DeleteTemplateElementController.java 314 2010-11-12 01:13:22Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-11 19:13:22 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 314 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import mx.itesm.gda.bm.biz.TemplateManagementBizOp;
import mx.itesm.gda.bm.session.UserLoginSession;
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
@RequestMapping("/deleteTemplateElement")
public class DeleteTemplateElementController extends BaseController {

    @Autowired
    private TemplateManagementBizOp templateMgr;

    @Autowired
    private UserLoginSession loginSession;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged
    public String deleteUser(
            @RequestParam("templateElementId") int templateElementId,
            @RequestParam("templateId") int templateId,
            ModelMap model) {
        if(templateElementId<0){
        throw new ControllerException("Invalid template ID");
        }

        templateMgr.deleteTemplateElement(templateElementId);
        return "redirect:modifyTemplate.do?templateId="+templateId;
    }

}
