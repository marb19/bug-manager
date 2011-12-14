/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListTasksController.java 308 2010-11-10 00:58:54Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-09 18:58:54 -0600 (Tue, 09 Nov 2010) $
 * Last Version      : $Revision: 308 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.DefectManagementBizOp;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
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
@RequestMapping("/listDefects")
public class ListDefectController {

    @Autowired
    private DefectManagementBizOp defectMgr;

    @Autowired
    private ProjectManagementBizOp projMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged()
    public String listDefects(@RequestParam("project_id") int projectID,
                            ModelMap model) {

        if(projectID <= 0){
            throw new ControllerException("ID de proyecto fuera de rango");
        }

        List<Map<String, ?>> defects = defectMgr.retrieveProjectDefects(projectID);
        Map<String, ?> project = projMgr.getProject(projectID);
        model.put("defects", defects);
        model.put("project", project);

        return null;
    }

}