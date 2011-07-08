/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ModifyUserPasswordControllerTest.java 319 2010-11-12 13:30:55Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-12 07:30:55 -0600 (Fri, 12 Nov 2010) $
 * Last Version      : $Revision: 319 $
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
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

/**
 *
 * @author $Author: inzunzo $
 * @version $Revision: 319 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"/testControllerContext.xml"})
public class ModifyUserPasswordControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            ModifyUserPasswordControllerTest.class);
    @Autowired
    private UserManagementBizOp userMgrMock;
    @Autowired
    private ModifyUserPasswordController ModifyUserPasswordController;

    /**
     * Test of getUserData method, of class ModifyUserPasswordController.
     */
    @Test
    public void testGetUserData() {
        LOGGER.info("checkTestGetUserData");
        Map<String, ?> userMap = new HashMap<String, Object>();
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn((Map) userMap);
        EasyMock.replay(userMgrMock);

        String got = ModifyUserPasswordController.getUserData("uName", new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(userMgrMock);
    }

    /**
     * Test of updateUserPassword method, of class ModifyUserPasswordController.
     */
    @Test
    public void testUpdateUserPassword() {
        LOGGER.info("checkTestUpdateUserPassword");
        boolean exceptionThrown = false;

        userMgrMock.modifyUserPassword("uName", "");
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);
        try {
            ModifyUserPasswordController.updateUserPassword("uName", "", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);
        exceptionThrown = false;


        userMgrMock.modifyUserPassword("", "pass");
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);
        try {
            ModifyUserPasswordController.updateUserPassword("", "pass", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);
        exceptionThrown = false;


        Capture<String> userNameMock = new Capture<String>();
        Capture<String> passwordMock = new Capture<String>();
        userMgrMock.modifyUserPassword(
                EasyMock.capture(userNameMock),
                EasyMock.capture(passwordMock));
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);
        ModifyUserPasswordController.updateUserPassword("uName", "pass", new ModelMap());
               
        Assert.assertEquals("uName",userNameMock.getValue());
        Assert.assertEquals("pass",passwordMock.getValue());
        EasyMock.reset(userMgrMock);


    }
}
