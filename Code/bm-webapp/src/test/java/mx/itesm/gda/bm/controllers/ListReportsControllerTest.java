/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListReportsControllerTest.java 355 2010-11-18 23:59:01Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-18 17:59:01 -0600 (Thu, 18 Nov 2010) $
 * Last Version      : $Revision: 355 $
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
 * @version $Revision: 355 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class ListReportsControllerTest {

    private static final Log LOGGER = LogFactory.getLog(
            ListReportsControllerTest.class);

    @Autowired
    private ProjectManagementBizOp projectMgrMock;

    @Autowired
    private UserManagementBizOp userMgrMock;

    @Autowired
    private UserLoginSession loginSession;

    @Autowired
    private ListReportsController listReportsController;

    @Test
    public void testListReports() {
        LOGGER.info("testListReports");
        List<Map<String, ?>> users = new ArrayList<Map<String,?>>();
        List<Map<String, ?>> projects = new ArrayList<Map<String,?>>();

        loginSession.setLoggedUserName("u");
        EasyMock.expect(projectMgrMock.retrieveProjects("u")).andReturn(projects);
        EasyMock.expect(userMgrMock.retrieveUsers()).andReturn(users);
        EasyMock.replay(projectMgrMock, userMgrMock);
        String got = listReportsController.listReports(new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(projectMgrMock, userMgrMock);
    }
}
