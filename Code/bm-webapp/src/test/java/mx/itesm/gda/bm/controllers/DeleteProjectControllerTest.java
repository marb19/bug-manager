/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DeleteProjectControllerTest.java 318 2010-11-12 04:43:33Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-11 22:43:33 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 318 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.controllers;

import junit.framework.Assert;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
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
 * @version $Revision: 318 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"/testControllerContext.xml"})
public class DeleteProjectControllerTest {

    private static final Log LOGGER = LogFactory.getLog(
            DeleteProjectControllerTest.class);
    @Autowired
    private ProjectManagementBizOp projectMgrMock;
    @Autowired
    private DeleteProjectController DeleteProjectController;

    /**
     * Test of deleteProject method, of class DeleteProjectController.
     */
    @Test
    public void testDeleteProject() {
        LOGGER.info("checkTestDeleteProject");
        boolean exceptionThrown = false;
        Capture<Integer> idMock = new Capture<Integer>();
        projectMgrMock.deleteProject(EasyMock.capture(idMock));
        EasyMock.expectLastCall();
        EasyMock.replay(projectMgrMock);

        String got = DeleteProjectController.deleteProject(30, new ModelMap());
        Assert.assertEquals("redirect:listProjects.do", got);
        Assert.assertTrue(30 == idMock.getValue());
        EasyMock.reset(projectMgrMock);

        projectMgrMock.deleteProject(-5);
        EasyMock.expectLastCall();
        EasyMock.replay(projectMgrMock);
        try {
            got = DeleteProjectController.deleteProject(-5, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);



    }
}
