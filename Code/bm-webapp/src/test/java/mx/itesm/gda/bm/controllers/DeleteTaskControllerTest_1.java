/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DeleteTaskControllerTest.java 335 2010-11-17 00:41:51Z alex.vc@gmail.com $
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
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.session.UserLoginSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.Capture;
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
public class DeleteTaskControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            DeleteTaskControllerTest.class);

    @Autowired
    private TaskManagementBizOp taskMgrMock;

    @Autowired
    private DeleteTaskController deleteTaskController;

    /**
     * Test of listProjects method, of class ListProjectController.
     */
    @Test
    public void testDeleteTask() {
        LOGGER.info("testDeleteTask");
        boolean exceptionThrown = false;

        //Caso de que el nombre de que el id de la tarea sea inválido
        try {
            String got = deleteTaskController.deleteTask(0, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        }
        Assert.assertTrue(exceptionThrown);

        Capture<Integer> taskIdMock = new Capture<Integer>();
        Map<String, Object> task = new HashMap<String, Object>();
        task.put("projectID", 1);
        task.put("taskID", 1);
        EasyMock.expect(taskMgrMock.getTask(1)).andReturn((Map)task);
        taskMgrMock.deleteTask(EasyMock.capture(taskIdMock));
        EasyMock.expectLastCall();
        EasyMock.replay(taskMgrMock);
        String got = deleteTaskController.deleteTask(1, new ModelMap());
        Assert.assertEquals(got, "redirect:listTasks.do?project_id=1");
        Assert.assertEquals(1, (int)taskIdMock.getValue());
        EasyMock.reset(taskMgrMock);
    }

}
