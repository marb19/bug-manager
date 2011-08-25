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

import mx.itesm.gda.bm.biz.DefectTypeManagementBizOp;
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
 * @version $Revision: 312 $
 */
@Scope("request")
@Controller
@RequestMapping("/newDefectType")
public class NewDefectTypeController extends BaseController{

    @Autowired
    private DefectTypeManagementBizOp defectTypeMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional(readOnly = true)
    @UserLogged(adminRequired = true)
    public String displayForm(ModelMap model) {
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String newDefectType(
            @RequestParam("defectTypeName") String defectTypeName,
            @RequestParam("defectTypeDescription") String defectTypeDescription,
            ModelMap model) {
        
        if (defectTypeName.equals("") || defectTypeDescription.equals("")) {
            throw new ControllerException("No se admiten campos vacios");
        }
        int dt = defectTypeMgr.createDefectType(defectTypeName, defectTypeDescription);

        return "redirect:listDefectTypes.do";
    }
}
