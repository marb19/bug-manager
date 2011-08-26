/* ***************************************************************************
 *   Copyright (c) 2011 BM
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
import mx.itesm.gda.bm.biz.DefectTypeManagementBizOp;
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
@RequestMapping("/modifyDefectType")
public class ModifyDefectTypeController extends BaseController {

    @Autowired
    private DefectTypeManagementBizOp defectTypeMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged
    public String getDefectTypeData(@RequestParam("defectTypeID") int defectTypeID,
            ModelMap model) {
        Map<String, ?> dt = defectTypeMgr.getDefectType(defectTypeID);
        model.put("dt", dt);
        return null;
    }

    @RequestMapping(method = RequestMethod.POST)
    @Transactional
    @UserLogged(adminRequired = true)
    public String updateDefectType(
            @RequestParam("defectTypeID") int defectTypeID,
            @RequestParam("defectTypeName") String defectTypeName,
            @RequestParam("defectTypeDescription") String defectTypeDescription,
            ModelMap model) {

        if(defectTypeName.equals("") || defectTypeDescription.equals("")) {
            throw new ControllerException("No se admiten campos vacios");
        }
        if (defectTypeID <= 0) {
            throw new ControllerException("Illegal defectTypeID (Less or equal to zero)");
        }
        
        defectTypeMgr.modifyDefectType(defectTypeID, defectTypeName, defectTypeDescription);

        return "redirect:listDefectTypes.do";
    }

}
