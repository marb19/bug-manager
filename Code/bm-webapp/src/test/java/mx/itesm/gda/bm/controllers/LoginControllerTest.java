/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: LoginControllerTest.java 269 2010-10-23 19:59:04Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-23 14:59:04 -0500 (Sat, 23 Oct 2010) $
 * Last Version      : $Revision: 269 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import mx.itesm.gda.bm.biz.UserAccessBizOp;
import mx.itesm.gda.bm.session.UserLoginSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 269 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class LoginControllerTest {

    private static final Log LOGGER = LogFactory.getLog(
            LoginControllerTest.class);

    @Autowired
    private UserAccessBizOp userAccessBizOpMock;

    @Autowired
    private UserLoginSession loginSession;

    @Autowired
    private LoginController loginController;

    /**
     * Test of displayLoginForm method, of class LoginController.
     */
    @Test
    public void testDisplayLoginForm() {
        LOGGER.info("displayLoginForm");
        loginSession.setLoggedUserName(null);
        loginSession.setLoginMessage(null);
        ModelMap model = new ModelMap();

        String ret = loginController.displayLoginForm(model);

        Assert.assertNull(ret);
        Assert.assertNull(model.get("loginMessage"));

        loginSession.setLoggedUserName(null);
        loginSession.setLoginMessage("My Message");
        model = new ModelMap();

        ret = loginController.displayLoginForm(model);

        Assert.assertNull(ret);
        Assert.assertEquals("My Message", model.get("loginMessage"));

        loginSession.setLoggedUserName("some_user");
        loginSession.setLoginMessage(null);
        model = new ModelMap();

        ret = loginController.displayLoginForm(model);

        Assert.assertEquals("redirect:listProjects.do", ret);

    }

    /**
     * Test of logout method, of class LoginController.
     */
    @Test
    public void testLogout() {
        LOGGER.info("logout");

        loginSession.setLoggedUserName("some_user");
        loginSession.setLoginMessage(null);
        ModelMap model = new ModelMap();

        String ret = loginController.logout(model);

        Assert.assertEquals("redirect:userLogin.do", ret);
        Assert.assertNull(loginSession.getLoggedUserName());
    }

    /**
     * Test of validateLoginForm method, of class LoginController.
     */
    @Test
    public void testValidateLoginForm() {
        LOGGER.info("validateLoginForm");

        EasyMock.expect(userAccessBizOpMock.validateUserLogin("some_user",
                "some_passwd")).andReturn(Boolean.TRUE);
        EasyMock.replay(userAccessBizOpMock);

        loginSession.setLoggedUserName(null);
        loginSession.setLoginMessage(null);
        ModelMap model = new ModelMap();

        String ret = loginController.validateLoginForm("some_user", "some_passwd", model);

        Assert.assertEquals("redirect:listProjects.do", ret);
        Assert.assertEquals("some_user", loginSession.getLoggedUserName());

        EasyMock.reset(userAccessBizOpMock);
        EasyMock.expect(userAccessBizOpMock.validateUserLogin("some_user",
                "some_passwd")).andReturn(Boolean.FALSE);
        EasyMock.replay(userAccessBizOpMock);

        loginSession.setLoggedUserName(null);
        loginSession.setLoginMessage(null);
        model = new ModelMap();

        ret = loginController.validateLoginForm("some_user", "some_passwd", model);

        Assert.assertNull(ret);
        Assert.assertNull(loginSession.getLoggedUserName());

        EasyMock.reset(userAccessBizOpMock);
    }

}
