/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ModifyUserControllerTest.java 319 2010-11-12 13:30:55Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-12 07:30:55 -0600 (Fri, 12 Nov 2010) $
 * Last Version      : $Revision: 319 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.controllers;

import java.lang.String;
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
 * @author $Author: inzunzo $
 * @version $Revision: 319 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"/testControllerContext.xml"})
public class ModifyUserControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            ModifyUserControllerTest.class);
    @Autowired
    private UserManagementBizOp userMgrMock;
    @Autowired
    private ModifyUserController ModifyUserController;

    /**
     * Test of getUserData method, of class ModifyUserController.
     */
    @Test
    public void testGetUserData() {
        LOGGER.info("checkTestGetUserData");
        Map<String, ?> userMap = new HashMap<String, Object>();
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn((Map) userMap);
        EasyMock.replay(userMgrMock);

        String got = ModifyUserController.getUserData("uName", new ModelMap());
        Assert.assertNull(got);

        EasyMock.reset(userMgrMock);
    }

    /**
     * Test of updateUser method, of class ModifyUserController.
     */
    @Test
    public void testUpdateUser() {
        LOGGER.info("checkTestUpdateUser");
        boolean exceptionThrown = false;

        userMgrMock.modifyUser("", true, "fullName", "mail@mail.com");
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);

        try {
            ModifyUserController.updateUser("", true, "fullName",
                    "mail@mail.com", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);
        exceptionThrown = false;



        userMgrMock.modifyUser("uName", true, "", "mail@mail.com");
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);

        try {
            ModifyUserController.updateUser("uName", true, "",
                    "mail@mail.com", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);
        exceptionThrown = false;


        userMgrMock.modifyUser("uName", true, "fullName", "");
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);

        try {
            ModifyUserController.updateUser("uName", true, "fullName", "", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);
        exceptionThrown = false;

        
        userMgrMock.modifyUser("uName", true, "fullName", "phonymail@");
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);

        try {
            ModifyUserController.updateUser("uName", true, "fullName",
                    "phonymail@", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);
        exceptionThrown = false;


        userMgrMock.modifyUser("uName", true, "fullName", "mail@mail.com");
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);

        String got =  ModifyUserController.updateUser("uName", true, "fullName",
                    "mail@mail.com", new ModelMap());

        EasyMock.reset(userMgrMock);
        Assert.assertEquals("redirect:listUsers.do",got);
        
        

    }
}
