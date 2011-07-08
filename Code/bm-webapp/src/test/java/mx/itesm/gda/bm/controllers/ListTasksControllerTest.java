/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListTasksControllerTest.java 335 2010-11-17 00:41:51Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-16 18:41:51 -0600 (Tue, 16 Nov 2010) $
 * Last Version      : $Revision: 335 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.model.Project;
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
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 335 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class ListTasksControllerTest {

    private static final Log LOGGER = LogFactory.getLog(
            ListTasksControllerTest.class);

    @Autowired
    private TaskManagementBizOp taskMgrMock;

    @Autowired
    private ProjectManagementBizOp projMgrMock;

    @Autowired
    private ListTasksController ListTasksController;

    /**
     * Test of listProjects method, of class ListProjectController.
     */
    @Test
    public void testListTasks() {
        LOGGER.info("checkListProjects");
        List<Map<String, ?>> tasks = new ArrayList<Map<String,?>>();
        Map<String, Object> project = new HashMap<String, Object>();
        boolean exceptionThrown = false;

        //Caso de que el id del proyecto esté fuera de rango
        EasyMock.expect(taskMgrMock.retrieveTasks(0)).andReturn(null);
        EasyMock.replay(taskMgrMock);
        try {
            String got = ListTasksController.listTasks(0, 0, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(taskMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso que el id del filtro no sea ni 0 ni 1
        EasyMock.expect(taskMgrMock.retrieveTasks(1)).andReturn(tasks);
        EasyMock.replay(taskMgrMock);
        try {
            String got = ListTasksController.listTasks(1, 2, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(taskMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso que el id del filtro sea 0
        EasyMock.expect(taskMgrMock.retrieveTasks(1)).andReturn(tasks);
        EasyMock.expect(projMgrMock.getProject(1)).andReturn((Map)project);
        EasyMock.replay(taskMgrMock, projMgrMock);
        String got = ListTasksController.listTasks(1, 0, new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(taskMgrMock, projMgrMock);

        //Caso que el id del filtro sea 1
        EasyMock.expect(taskMgrMock.retrieveTasks(1)).andReturn(tasks);
        EasyMock.expect(projMgrMock.getProject(1)).andReturn((Map)project);
        EasyMock.replay(taskMgrMock, projMgrMock);
        got = ListTasksController.listTasks(1, 1, new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(taskMgrMock, projMgrMock);
    }

}
