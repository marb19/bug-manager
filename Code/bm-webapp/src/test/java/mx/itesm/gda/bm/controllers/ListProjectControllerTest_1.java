/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListProjectControllerTest.java 312 2010-11-11 06:36:43Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-11 00:36:43 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 312 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
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
 * @author $Author: inzunzo $
 * @version $Revision: 312 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class ListProjectControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            ListProjectControllerTest.class);

    @Autowired
    private ProjectManagementBizOp projectMgrMock;

    @Autowired
    private ListProjectController ListProjectController;

    @Autowired
    private UserLoginSession loginSession;
    /**
     * Test of listProjects method, of class ListProjectController.
     */
    @Test
    public void testListProjects() {
        LOGGER.info("checkListProjects");
        boolean exceptionThrown = false;
        loginSession.setLoggedUserName("admin");
        List<Map<String, ?>> map = new ArrayList<Map<String, ?>>();
        EasyMock.expect(projectMgrMock.retrieveProjects("admin")).andReturn(map);
        EasyMock.replay(projectMgrMock);
        try {
            ListProjectController.listProjects(new ModelMap());
        } catch(Exception e) {
            exceptionThrown = true;
        }

        Assert.assertFalse(exceptionThrown);
        EasyMock.reset(projectMgrMock);
    }

}
