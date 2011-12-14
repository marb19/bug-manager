/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DeleteTaskController.java 308 2010-11-10 00:58:54Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-09 18:58:54 -0600 (Tue, 09 Nov 2010) $
 * Last Version      : $Revision: 308 $
 *
 * Original Author   : Fernando Manzano
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.Map;
import mx.itesm.gda.bm.biz.DefectManagementBizOp;
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
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 308 $
 */
@Scope("request")
@Controller
@RequestMapping("/deleteDefect")
public class DeleteDefectController extends BaseController {

    @Autowired
    private DefectManagementBizOp defectMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged(adminRequired = true)
    public String deleteDefect(
            @RequestParam("defectID") int defectID,
            ModelMap model) {

        if(defectID <= 0){
            throw new ControllerException("ID de defecto fuera de rango");
        }
        
        Map<String, ?> defect = defectMgr.getDefect(defectID);
        Map<String, ?> project = (Map<String, ?>) defect.get("project");
        defectMgr.deleteDefect(defectID);
        
        return "redirect:listDefects.do?project_id=" + project.get("projectId");
    }

}
