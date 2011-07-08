/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ListUserControllerTest.java 318 2010-11-12 04:43:33Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-11 22:43:33 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 318 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.Assert;
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
 * @version $Revision: 318 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class ListUserControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            ListUserControllerTest.class);

    @Autowired
    private UserManagementBizOp userMgrMock;

    @Autowired
    private ListUserController ListUserController;


    /**
     * Test of listProjects method, of class ListUserController.
     */
    @Test
    public void testListUsers() {
        LOGGER.info("checkListUsers");
        List<Map<String, ?>> users = new ArrayList<Map<String,?>>();
        EasyMock.expect(userMgrMock.retrieveUsers()).andReturn(users);
        EasyMock.replay(userMgrMock);

        String got = ListUserController.listUsers(new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(userMgrMock);
    }

}