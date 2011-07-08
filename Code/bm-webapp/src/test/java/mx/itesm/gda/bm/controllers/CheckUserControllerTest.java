/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: CheckUserControllerTest.java 335 2010-11-17 00:41:51Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-16 18:41:51 -0600 (Tue, 16 Nov 2010) $
 * Last Version      : $Revision: 335 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.HashMap;
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
 * @version $Revision: 335 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class CheckUserControllerTest {

    private static final Log LOGGER = LogFactory.getLog(
            CheckUserControllerTest.class);

    @Autowired
    private UserManagementBizOp userMgrMock;

    @Autowired
    private CheckUserController CheckUserController;


    /**
     * Test of newUser method, of class CheckUserController.
     */
    @Test
    public void testNewUser() {
        LOGGER.info("checkTestNewUser");
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn(null);
        EasyMock.replay(userMgrMock);
        String got = CheckUserController.newUser("uName", new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(userMgrMock);


        Map<String, ?> userMap = new HashMap<String,Object>();
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn((Map)userMap);
        EasyMock.replay(userMgrMock);
        got = CheckUserController.newUser("uName", new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(userMgrMock);

    }

    @Test
    public void testNewEmail() {
        LOGGER.info("checkTestNewUser");
        EasyMock.expect(userMgrMock.getUserByEmail("mail@mail.com")).andReturn(null);
        EasyMock.replay(userMgrMock);
        String got = CheckUserController.newEmail("mail@mail.com", new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(userMgrMock);


        Map<String, ?> userMap = new HashMap<String,Object>();
        EasyMock.expect(userMgrMock.getUserByEmail("mail@mail.com")).andReturn((Map) userMap);
        EasyMock.replay(userMgrMock);
        got = CheckUserController.newEmail("mail@mail.com", new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(userMgrMock);

    }

}