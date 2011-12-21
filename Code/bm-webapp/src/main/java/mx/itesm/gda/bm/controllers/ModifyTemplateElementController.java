/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ModifyTemplateElementController.java 398 2011-11-21 22:10:10Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2011-11-21 16:10:10 -0600 (Mon, 21 Nov 2011) $
 * Last Version      : $Revision: 398 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.Map;
import mx.itesm.gda.bm.biz.DefectTypeManagementBizOp;
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
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 398 $
 */
@Scope("request")
@Controller
@RequestMapping("/modifyTemplateElement")
public class ModifyTemplateElementController extends BaseController {

    @Autowired
    private TemplateManagementBizOp templateMgr;

    @Autowired
    private DefectTypeManagementBizOp defectTypeMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged(adminRequired = true)
    public String getTemplateData(
            @RequestParam("templateElementId") int templateElementId,
            @RequestParam("templateId") int templateId,
            ModelMap model) {
        Map<String, ?> element = templateMgr.getTemplateElement(templateElementId);
        model.put("element", element);
        model.put("defectTypes", defectTypeMgr.retrieveDefectTypes());
        model.put("templateId", templateId);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String modifyTemplate(
            @RequestParam("templateElementId") int templateElementId,
            @RequestParam("templateId") int templateId,
            @RequestParam("defectTypeId") int defectTypeId,
            @RequestParam("elementDescription") String elementDescription,
            ModelMap model) {

        if(defectTypeId < 0){
            throw new ControllerException("ID de tipo de defecto invalido");
        }

        if(elementDescription.equals("")){
            throw new ControllerException("No se admiten campos vacios");
        }

        int templateElement = templateMgr.modifyTemplateElement(templateElementId, defectTypeId, elementDescription);

        return "redirect:modifyTemplate.do?templateId="+templateId;
    }

}