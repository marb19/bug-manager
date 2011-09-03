/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewUserControllerTest.java 318 2010-11-12 04:43:33Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-11 22:43:33 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 318 $
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
 * @version $Revision: 318 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"/testControllerContext.xml"})
public class NewUserControllerTest {

    private static final Log LOGGER = LogFactory.getLog(
            NewUserControllerTest.class);
    @Autowired
    private UserManagementBizOp userMgrMock;
    @Autowired
    private NewUserController NewUserController;

    /**
     * Test of displayForm method, of class NewUserController.
     */
    @Test
    public void testDisplayForm() {
        LOGGER.info("checkTestDisplayForm");
        String got = NewUserController.displayForm(new ModelMap());
        Assert.assertNull(got);

    }

    /**
     * Test of newUser method, of class NewUserController.
     */
    @Test
    public void testNewUser() {
        LOGGER.info("checkTestNewUser");
        boolean exceptionThrown = false;

        EasyMock.expect(userMgrMock.getUser("")).andReturn(null);
        EasyMock.expect(userMgrMock.getUserByEmail("")).andReturn(null);
        EasyMock.expect(userMgrMock.createUser("", 30, "", "", "")).andReturn("");
        EasyMock.replay(userMgrMock);
        try {
            NewUserController.newUser("", 30, "fullName", "pass", "mail@mail.com", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        exceptionThrown = false;
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn(null);
        EasyMock.expect(userMgrMock.getUserByEmail("")).andReturn(null);
        EasyMock.expect(userMgrMock.createUser("", 30, "", "", "")).andReturn("");
        EasyMock.replay(userMgrMock);
        try {
            NewUserController.newUser("uName", 30, "", "pass", "mail@mail.com", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        exceptionThrown = false;
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn(null);
        EasyMock.expect(userMgrMock.getUserByEmail("")).andReturn(null);
        EasyMock.expect(userMgrMock.createUser("", 30, "", "", "")).andReturn("");
        EasyMock.replay(userMgrMock);
        try {
            NewUserController.newUser("uName", 30, "fullName", "", "mail@mail.com", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        exceptionThrown = false;
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn(null);
        EasyMock.expect(userMgrMock.getUserByEmail("")).andReturn(null);
        EasyMock.expect(userMgrMock.createUser("", 30, "", "", "")).andReturn("");
        EasyMock.replay(userMgrMock);
        try {
            NewUserController.newUser("uName", 30, "fullName", "pass", "", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        exceptionThrown = false;
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn(null);
        EasyMock.expect(userMgrMock.getUserByEmail("")).andReturn(null);
        EasyMock.expect(userMgrMock.createUser("", 30, "", "", "")).andReturn("");
        EasyMock.replay(userMgrMock);
        try {
            NewUserController.newUser("uName", 30, "fullName", "pass", "phonyMail@", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        exceptionThrown = false;
        Map<String, ?> existingUser = new HashMap<String, Object>();
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn((Map) existingUser);
        EasyMock.expect(userMgrMock.getUserByEmail("")).andReturn(null);
        EasyMock.expect(userMgrMock.createUser("", 30, "", "", "")).andReturn("");
        EasyMock.replay(userMgrMock);
        try {
            NewUserController.newUser("uName", 30, "fullName", "pass", "mail@mail.com", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        exceptionThrown = false;
        EasyMock.expect(userMgrMock.getUser("uName")).andReturn(null);
        EasyMock.expect(userMgrMock.getUserByEmail("mail@mail.com")).andReturn((Map) existingUser);
        EasyMock.expect(userMgrMock.createUser("", 30, "", "", "")).andReturn("");
        EasyMock.replay(userMgrMock);
        try {
            NewUserController.newUser("uName", 30, "fullName",
                    "pass", "mail@mail.com", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }
        Assert.assertTrue(exceptionThrown);



        EasyMock.expect(userMgrMock.getUser("uName")).andReturn(null);
        EasyMock.expect(userMgrMock.getUserByEmail("mail@mail.com")).andReturn(null);
        EasyMock.expect(userMgrMock.createUser("uName", 30, "fullName",
                "pass", "mail@mail.com")).andReturn("uName");
        EasyMock.replay(userMgrMock);

        String got = NewUserController.newUser("uName", 30, "fullName", "pass",
                "mail@mail.com", new ModelMap());
        Assert.assertEquals("redirect:listUsers.do", got);
        EasyMock.reset(userMgrMock);




    }
}
