/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ViewTaskControllerTest.java 357 2010-11-20 15:14:45Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-20 09:14:45 -0600 (Sat, 20 Nov 2010) $
 * Last Version      : $Revision: 357 $
 *
 * Original Author   : Marco Rangel
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
import mx.itesm.gda.bm.session.UserLoginSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.EasyMock;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

/**
 *
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 357 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class ViewTaskControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            ViewTaskControllerTest.class);

    @Autowired
    private UserLoginSession sessionMock;

    @Autowired
    private TaskManagementBizOp taskMgrMock;

    @Autowired
    private UserManagementBizOp userMgrMock;

    @Autowired
    private ViewTaskController viewTaskController;

    /**
     * Test of listProjects method, of class ListProjectController.
     */
    @Test
    public void testDisplayForm() {
        LOGGER.info("testDisplayForm");
        boolean exceptionThrown = false;

        //Caso de que el id del proyecto sea <= 0
        try {
            String got = viewTaskController.displayForm(0, 0, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
        exceptionThrown = false;

        //Caso de que el id de la tarea sea <= 0
        try {
            String got = viewTaskController.displayForm(1, 0, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);
        exceptionThrown = false;

        //Caso de que el id del proyecto y el id de la tarea sean > 0
        List<Map<String, ?>> users = new ArrayList<Map<String,?>>();
        List<Map<String, ?>> comments = new ArrayList<Map<String,?>>();
        Map<String, Object> task = new HashMap<String, Object>();
        ModelMap map = new ModelMap();
        EasyMock.expect(taskMgrMock.getTask(1)).andReturn((Map)task);
        EasyMock.expect(userMgrMock.retrieveUsers()).andReturn(users);
        EasyMock.expect(taskMgrMock.retrieveComments(1)).andReturn(comments);
        EasyMock.replay(taskMgrMock, userMgrMock);
        String got = viewTaskController.displayForm(1, 1, map);
        Assert.assertEquals(null, got);
        Assert.assertEquals(map.get("users"), users);
        Assert.assertEquals(map.get("task"), task);
        Assert.assertEquals(map.get("comments"), comments);
        Assert.assertEquals(map.get("project_id"), 1);
        Assert.assertEquals(map.get("task_id"), 1);
        EasyMock.reset(taskMgrMock, userMgrMock);
    }

    /**
     * Test of listProjects method, of class ListProjectController.
     */
    @Test
    public void testModifyTaskAdmin() {
        LOGGER.info("testModifyTaskAdmin");
        boolean exceptionThrown = false;
        Map<String, Object> user = new HashMap<String, Object>();
        Map<String, Object> task = new HashMap<String, Object>();
        task.put("investedHours", 0);
        Date startDate = new Date();
        Date endDate = new Date();

        //Caso de que el projectId esté fuera de rango
        try{
            viewTaskController.modifyTaskAdmin(0, 0, null, null, null, null, 0,
                    0, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el taskId esté fuera de rango
        try{
            viewTaskController.modifyTaskAdmin(1, 0, null, null, null, null, 0,
                    0, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el nombre de la tarea esté vacío
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "", null, null, null, 0,
                    0, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que la descripcion de la tarea esté vacia
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "", null, null, 0,
                    0, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el campo de usuario esté vacío
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "", null, 0, 0, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el TaskState sea ""
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "", 0, 0, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el TaskState sea inválido
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "BAD_STATE", 0, 0, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        } 
        Assert.assertTrue(exceptionThrown);

        //Caso de que el TaskState sea CANCELED y estimatedHours sea <= 0
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "CANCELED", 0, 0, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        } 
        Assert.assertTrue(exceptionThrown);

        //Caso de que el TaskState sea NOT_STARTED e investedHours sea < 0
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "NOT_STARTED", 20, -1, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        } 
        Assert.assertTrue(exceptionThrown);

        //Caso de que el TaskState sea STARTED y remainingHours sea < 0
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "STARTED", 20, 5, -1, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        } 
        Assert.assertTrue(exceptionThrown);

        //Caso de que el TaskState sea COMPLETED y remainingHours sea > 0
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "COMPLETED", 20, 5, 15, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        } 
        Assert.assertTrue(exceptionThrown);

        //Caso de que stardDate sea null
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "COMPLETED", 20, 20, 0, null, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        } 
        Assert.assertTrue(exceptionThrown);

        //Caso de que endDate sea null
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "COMPLETED", 20, 20, 0, startDate, null, null, null);
        } catch(Exception e){
            exceptionThrown = true;
        } 
        Assert.assertTrue(exceptionThrown);

        //Caso de que el usuario no sea encontrado
        EasyMock.expect(userMgrMock.getUser("u")).andReturn(null);
        EasyMock.replay(userMgrMock);
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
            "u", "COMPLETED", 20, 20, 0, startDate, endDate, "comment", new ModelMap());
        } catch(Exception e){
            exceptionThrown = true;
        } finally{
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que remainingHours sea <= 0 e investedHours sea <= 0
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.expect(taskMgrMock.getTask(1)).andReturn((Map)task);
        EasyMock.replay(userMgrMock, taskMgrMock);
        try{
            viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
                    "u", "COMPLETED", 20, 0, 0, startDate, endDate, "", new ModelMap());
        } catch(Exception e){
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock, taskMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que los comentarios sean ""
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.expect(taskMgrMock.getTask(1)).andReturn((Map)task);
        EasyMock.expect(taskMgrMock.modifyTask(1, "task", "task desc", "u",
                "COMPLETED", 20, 20, 0, startDate, endDate)).andReturn(1);
        EasyMock.replay(userMgrMock, taskMgrMock);
        String got = viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
            "u", "COMPLETED", 20, 20, 0, startDate, endDate, "", new ModelMap());
        Assert.assertEquals("redirect:listTasks.do?project_id=1", got);
        EasyMock.reset(userMgrMock, taskMgrMock);

        //Caso de que los comentarios sean ""
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.expect(taskMgrMock.getTask(1)).andReturn((Map)task);
        EasyMock.expect(taskMgrMock.modifyTask(1, "task", "task desc", "u",
                "STARTED", 20, 20, 5, startDate, endDate)).andReturn(1);
        EasyMock.replay(userMgrMock, taskMgrMock);
        got = viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
            "u", "STARTED", 20, 20, 5, startDate, endDate, "", new ModelMap());
        Assert.assertEquals("redirect:listTasks.do?project_id=1", got);
        EasyMock.reset(userMgrMock, taskMgrMock);

        //Caso de que los comentarios no sean ""
        got = "";
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.expect(taskMgrMock.getTask(1)).andReturn((Map)task);
        EasyMock.expect(taskMgrMock.modifyTask(1, "task", "task desc", "u",
                "NOT_STARTED", 20, 0, 20, startDate, endDate)).andReturn(1);
        EasyMock.expect(taskMgrMock.addComment(1, null, "comment")).andReturn(1);
        EasyMock.expect(sessionMock.getLoggedUserName());
        EasyMock.replay(userMgrMock, taskMgrMock);
        got = viewTaskController.modifyTaskAdmin(1, 1, "task", "task desc",
            "u", "NOT_STARTED", 20, 0, 20, startDate, endDate, "comment", new ModelMap());
        Assert.assertEquals("redirect:listTasks.do?project_id=1", got);
        EasyMock.reset(userMgrMock, taskMgrMock);
    }

}
