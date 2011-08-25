/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListUserController.java 312 2010-11-11 06:36:43Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-11 00:36:43 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 312 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.DefectTypeManagementBizOp;
import mx.itesm.gda.bm.utils.UserLogged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author $Author: inzunzo $
 * @version $Revision: 312 $
 */
@Scope("request")
@Controller
@RequestMapping("/listDefectTypes")
public class ListDefectTypeController {

    @Autowired
    private DefectTypeManagementBizOp dtMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged
    public String listUsers(ModelMap model) {
        List<Map<String, ?>> defectTypes = dtMgr.retrieveDefectTypes();
        model.put("defectTypes", defectTypes);
        return null;
    }

}
