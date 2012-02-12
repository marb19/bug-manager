/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListResourcesController.java 312 2010-11-11 06:36:43Z inzunzo $
 * Last Revised By   : $Author: hgrobles $
 * Last Checked In   : $Date: 2010-11-11 00:36:43 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 312 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
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
 * @author $Author: inzunzo $
 * @version $Revision: 312 $
 */
@Scope("request")
@Controller
@RequestMapping("/listResources")
public class ListResourcesController {

    @Autowired
    private UserManagementBizOp userMgr;

    @Autowired
    private ProjectManagementBizOp projectMgr;

    @RequestMapping(method = RequestMethod.GET)
    @Transactional
    @UserLogged(adminRequired = true)
    public String listUsers(@RequestParam("project_id") int projectID,
            ModelMap model) {
        List<Map<String, ?>> projectUsers = projectMgr.getUsers(projectID);
        List<Map<String, ?>> allUsers = userMgr.retrieveUsers();
        List<Map<String, ?>> availableUsers = new ArrayList<Map<String, ?>>();

        for (Map<String, ?> user : allUsers ){
            boolean add=true;
            for (Map<String, ?> projectUser : projectUsers){
                if (user.get("userName").equals(projectUser.get("userName"))){
                    add = false;
                }
            }
            if(add){
                availableUsers.add(user);
            }
        }

        model.put("projectUsers", projectUsers);
        model.put("availableUsers", availableUsers);
        model.put("project_id", projectID);
        return null;
    }

}
