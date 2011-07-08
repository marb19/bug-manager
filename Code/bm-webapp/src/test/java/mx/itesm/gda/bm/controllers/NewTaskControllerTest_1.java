/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewTaskControllerTest.java 335 2010-11-17 00:41:51Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-16 18:41:51 -0600 (Tue, 16 Nov 2010) $
 * Last Version      : $Revision: 335 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
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
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 335 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class NewTaskControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            NewTaskControllerTest.class);

    @Autowired
    private TaskManagementBizOp taskMgrMock;

    @Autowired
    private UserManagementBizOp userMgrMock;

    @Autowired
    private ProjectManagementBizOp projMgrMock;

    @Autowired
    private NewTaskController newTaskController;

    /**
     * Test of listProjects method, of class ListProjectController.
     */
    @Test
    public void testDisplayForm() {
        LOGGER.info("testDisplayForm");
        List<Map<String, ?>> users = new ArrayList<Map<String,?>>();
        boolean exceptionThrown = false;

        //Caso de que el id del proyecto esté fuera de rango
        EasyMock.expect(userMgrMock.retrieveUsers()).andReturn(null);
        EasyMock.replay(userMgrMock);
        try {
            String got = newTaskController.displayForm(0, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el id del proyecto esté fuera de rango
        EasyMock.expect(userMgrMock.retrieveUsers()).andReturn(users);
        EasyMock.replay(userMgrMock);
        String got = newTaskController.displayForm(1, new ModelMap());
        Assert.assertTrue(exceptionThrown);
        EasyMock.reset(userMgrMock);
    }

    /**
     * Test of listProjects method, of class ListProjectController.
     */
    @Test
    public void testNewTask() {
        LOGGER.info("testNewTask");
        boolean exceptionThrown = false;
        Map<String, Object> user = new HashMap<String, Object>();
        
        //Caso de que el nombre de la tarea sea ""
        try {
            String got = newTaskController.newTask("", 0, null, null, 
                    0, null, null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el project_id sea <= 0
        exceptionThrown = false;
        try {
            String got = newTaskController.newTask("task", 0, null, null,
                    0, null, null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que la descripcion sea ""
        exceptionThrown = false;
        try {
            String got = newTaskController.newTask("task", 1, "", null,
                    0, null, null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el usuario sea ""
        exceptionThrown = false;
        try {
            String got = newTaskController.newTask("task", 1, "task desc", "",
                    0, null, null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que el usuario sea ""
        EasyMock.expect(userMgrMock.getUser("u")).andReturn(null);
        EasyMock.replay(userMgrMock);
        exceptionThrown = false;
        try {
            String got = newTaskController.newTask("task", 1, "task desc", "u",
                    0, null, null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally{
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que estimatedHours sea <= 0
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.replay(userMgrMock);
        exceptionThrown = false;
        try {
            String got = newTaskController.newTask("task", 1, "task desc", "u",
                    0, null, null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally{
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que estimatedHours sea <= 0
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.replay(userMgrMock);
        exceptionThrown = false;
        try {
            String got = newTaskController.newTask("task", 1, "task desc", "u",
                    0, null, null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally{
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que startDate sea nula
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.replay(userMgrMock);
        exceptionThrown = false;
        try {
            String got = newTaskController.newTask("task", 1, "task desc", "u",
                    20, null, null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally{
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que endDate sea nula
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.replay(userMgrMock);
        exceptionThrown = false;
        try {
            String got = newTaskController.newTask("task", 1, "task desc", "u",
                    20, new Date(), null, null);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally{
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso sin excepciones
        Date startDate = new Date();
        Date endDate = new Date();
        EasyMock.expect(userMgrMock.getUser("u")).andReturn((Map)user);
        EasyMock.expect(taskMgrMock.createTask("task", 1, "task desc", "u", 20,
                startDate, endDate)).andReturn(1);
        EasyMock.replay(userMgrMock, taskMgrMock);
        String got = newTaskController.newTask("task", 1, "task desc", "u",
                    20, startDate, endDate, new ModelMap());
        Assert.assertEquals(got, "redirect:listTasks.do?project_id=1");
        EasyMock.reset(userMgrMock, taskMgrMock);

    }

}
