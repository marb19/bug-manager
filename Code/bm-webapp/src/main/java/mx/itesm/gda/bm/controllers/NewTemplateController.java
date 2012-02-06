/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewTaskController.java 398 2011-11-21 22:10:10Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2011-11-21 16:10:10 -0600 (Mon, 21 Nov 2011) $
 * Last Version      : $Revision: 398 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.List;
import mx.itesm.gda.bm.biz.TemplateManagementBizOp;
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
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 398 $
 */
@Scope("request")
@Controller
@RequestMapping("/newTemplate")
public class NewTemplateController extends BaseController {

    @Autowired
    private TemplateManagementBizOp templateMgr;

    @Autowired
    private UserManagementBizOp userMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged(adminRequired = true)
    public String displayForm(ModelMap model) {
        List<String> templateReviewTypes = templateMgr.getTypes();

        model.put("templateReviewTypes", templateReviewTypes);

        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String newTemplate(
            @RequestParam("templateName") String templateName,
            @RequestParam("templateDescription") String templateDescription,
            @RequestParam("templateReviewType") String templateReviewType,
            @RequestParam(value = "templatePublic", defaultValue = "false") boolean templatePublic,
            @RequestParam("assignedUser") String assignedUser,
            ModelMap model) {

        if(templateName.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }


        if(templateDescription.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(assignedUser.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        if(userMgr.getUser(assignedUser) == null) {
            throw new ControllerException("Usuario inexistente");
        }
        if(templateReviewType.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        boolean wrongType = true;
        for (String type : templateMgr.getTypes()){
            if (templateReviewType.equals(type)){
                wrongType=false;
            }
        }
        if (wrongType){
            throw new ControllerException("Tipo de template inexistente");
        }

        int template = templateMgr.createTemplate(templateName, templateDescription,
                templateReviewType, templatePublic, assignedUser);

        return "redirect:listTemplates.do";
    }

}
