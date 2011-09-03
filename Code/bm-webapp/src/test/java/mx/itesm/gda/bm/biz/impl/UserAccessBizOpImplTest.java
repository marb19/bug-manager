/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserAccessBizOpImplTest.java 269 2010-10-23 19:59:04Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-23 14:59:04 -0500 (Sat, 23 Oct 2010) $
 * Last Version      : $Revision: 269 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import com.dumbster.smtp.SimpleSmtpServer;
import com.dumbster.smtp.SmtpMessage;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Random;
import mx.itesm.gda.bm.biz.UserAccessBizOp;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 269 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testBizOpContext.xml" })
public class UserAccessBizOpImplTest {

    private static final Log LOGGER = LogFactory.getLog(
            UserAccessBizOpImplTest.class);

    @Autowired
    private UserAccessBizOp userAccessBizOp;

    @Autowired
    private UserDAO userDAOMock;

    private static User createUser(String userName, String fullName,
            String email, String password, int permissions,
            String recoveryTicket, Date recoveryExpiration) {
        User u = new User();
        u.setUserName(userName);
        u.setFullName(fullName);
        u.setEmail(email);
        u.setPassword(password);
        u.setPermissions(permissions);
        u.setPasswordRecoveryTicket(recoveryTicket);
        u.setPasswordRecoveryExpiration(recoveryExpiration);
        u.setAssignedTasks(Collections.EMPTY_LIST);
        u.setAuthoredComments(Collections.EMPTY_LIST);
        u.setAuthoredCompletionReports(Collections.EMPTY_LIST);
        return u;
    }

    @Test
    @Transactional
    public void testCheckUserAdministrator() {
        LOGGER.info("checkUserAdministrator");

        EasyMock.expect(userDAOMock.findByUserName("sure_admin")).
                andReturn(createUser("sure_admin", "Administrator",
                "admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        boolean ret = userAccessBizOp.checkUserAdministrator("sure_admin");

        Assert.assertTrue(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("no_admin")).
                andReturn(createUser("no_admin", "No Administrator",
                "no-admin@bipolar.mx", "Password", 10, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.checkUserAdministrator("no_admin");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("none_existent")).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.checkUserAdministrator("none_existent");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("")).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.checkUserAdministrator("");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName(null)).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.checkUserAdministrator(null);

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testCheckValidUser() {
        LOGGER.info("checkValidUser");

        EasyMock.expect(userDAOMock.findByUserName("sure_admin")).
                andReturn(createUser("sure_admin", "Administrator",
                "admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        boolean ret = userAccessBizOp.checkValidUser("sure_admin");

        Assert.assertTrue(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("no_admin")).
                andReturn(createUser("no_admin", "No Administrator",
                "no-admin@bipolar.mx", "Password", 10, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.checkValidUser("no_admin");

        Assert.assertTrue(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("none_existent")).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.checkValidUser("none_existent");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("")).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.checkValidUser("");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName(null)).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.checkValidUser(null);

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testGetUserModel() {
        LOGGER.info("getUserModel");

        EasyMock.expect(userDAOMock.findByUserName("some_user")).andReturn(
                createUser("some_user", "Full Name", "some@email.com",
                "somePasSwoRd", 30, null, null));
        EasyMock.replay(userDAOMock);

        Map<String, ?> umodel = userAccessBizOp.getUserModel("some_user");

        Assert.assertNotNull(umodel);
        Assert.assertEquals("some_user", umodel.get("userName"));
        Assert.assertEquals("Full Name", umodel.get("fullName"));
        Assert.assertEquals("some@email.com", umodel.get("email"));
        Assert.assertEquals(Boolean.TRUE, umodel.get("administrator"));
        Assert.assertNull(umodel.get("password"));

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("some_other")).andReturn(
                createUser("some_other", "Full Name", "some@email.com",
                "somePasSwoRd", 10, null, null));
        EasyMock.replay(userDAOMock);

        umodel = userAccessBizOp.getUserModel("some_other");

        Assert.assertNotNull(umodel);
        Assert.assertEquals("some_other", umodel.get("userName"));
        Assert.assertEquals("Full Name", umodel.get("fullName"));
        Assert.assertEquals("some@email.com", umodel.get("email"));
        Assert.assertEquals(Boolean.FALSE, umodel.get("administrator"));
        Assert.assertNull(umodel.get("password"));

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("non_existent")).andReturn(
                null);
        EasyMock.replay(userDAOMock);

        umodel = userAccessBizOp.getUserModel("non_existent");

        Assert.assertNull(umodel);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("")).andReturn(null);
        EasyMock.replay(userDAOMock);

        umodel = userAccessBizOp.getUserModel("");

        Assert.assertNull(umodel);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName(null)).andReturn(null);
        EasyMock.replay(userDAOMock);

        umodel = userAccessBizOp.getUserModel(null);

        Assert.assertNull(umodel);

        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testInitiatePasswordRecovery() {
        LOGGER.info("initiatePasswordRecovery");

        EasyMock.expect(userDAOMock.findByEmail("some@email.com")).andReturn(
                createUser("some_user", "Full Name", "some@email.com",
                "somePasSwoRd", 30, null, null));
        Capture<User> updatedUser = new Capture<User>();
        userDAOMock.update(EasyMock.and(EasyMock.isA(User.class),
                EasyMock.capture(updatedUser)));
        EasyMock.replay(userDAOMock);

        SimpleSmtpServer server = SimpleSmtpServer.start(25025);
        Calendar before = Calendar.getInstance();
        boolean result = userAccessBizOp.initiatePasswordRecovery(
                "some@email.com");
        Calendar after = Calendar.getInstance();
        server.stop();

        Assert.assertTrue(result);

        after.add(Calendar.DAY_OF_MONTH, 1);
        before.add(Calendar.DAY_OF_MONTH, 1);

        SmtpMessage m = (SmtpMessage)server.getReceivedEmail().next();
        User u = updatedUser.getValue();

        Assert.assertEquals(u.getPasswordRecoveryTicket(), m.getBody());
        Assert.assertTrue(!before.after(u.getPasswordRecoveryExpiration()));
        Assert.assertTrue(!after.before(u.getPasswordRecoveryExpiration()));
        Assert.assertTrue(m.getHeaderValue("To").contains(u.getEmail()));

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByEmail("phony@mc-phony.com")).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        result = userAccessBizOp.initiatePasswordRecovery("phony@mc-phony.com");
        Assert.assertFalse(result);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByEmail("")).andReturn(null);
        EasyMock.replay(userDAOMock);

        result = userAccessBizOp.initiatePasswordRecovery("");

        Assert.assertFalse(result);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByEmail(null)).andReturn(null);
        EasyMock.replay(userDAOMock);

        result = userAccessBizOp.initiatePasswordRecovery(null);

        Assert.assertFalse(result);

        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testValidatePasswordRecoveryTicket() {
        LOGGER.info("validatePasswordRecoveryTicket");
        Random random = new SecureRandom();

        Calendar expiration = Calendar.getInstance();
        expiration.add(Calendar.DAY_OF_MONTH, 1);

        Capture<User> updatedUser = new Capture<User>();
        EasyMock.expect(userDAOMock.findByRecoveryTicket("qwertyuiop")).
                andReturn(createUser("some_user", "Full Name", "some@email.com",
                "12345", 10, "qwertyuiop", expiration.getTime()));
        userDAOMock.update(EasyMock.and(EasyMock.capture(updatedUser),
                EasyMock.isA(User.class)));
        EasyMock.replay(userDAOMock);

        SimpleSmtpServer server = SimpleSmtpServer.start(25025);
        boolean ret = userAccessBizOp.validatePasswordRecoveryTicket(
                "qwertyuiop");
        server.stop();

        Assert.assertTrue(ret);

        SmtpMessage m = (SmtpMessage)server.getReceivedEmail().next();
        User u = updatedUser.getValue();

        Assert.assertEquals(u.getPassword(), m.getBody());
        Assert.assertTrue(m.getHeaderValue("To").contains(u.getEmail()));
        Assert.assertNull(u.getPasswordRecoveryTicket());
        Assert.assertNull(u.getPasswordRecoveryExpiration());

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByRecoveryTicket("phony-ticket")).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validatePasswordRecoveryTicket("phony-ticket");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByRecoveryTicket("")).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validatePasswordRecoveryTicket("");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByRecoveryTicket(null)).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validatePasswordRecoveryTicket(null);

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testValidateUserLogin() {
        LOGGER.info("validateUserLogin");

        EasyMock.expect(userDAOMock.findByUserName("sure_admin")).
                andReturn(createUser("sure_admin", "Administrator",
                "admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        boolean ret = userAccessBizOp.validateUserLogin("sure_admin",
                "Password");

        Assert.assertTrue(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("sure_admin")).
                andReturn(createUser("sure_admin", "Administrator",
                "admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("sure_admin", "bad_password");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("sure_admin")).
                andReturn(createUser("sure_admin", "Administrator",
                "admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("sure_admin", "");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("sure_admin")).
                andReturn(createUser("sure_admin", "Administrator",
                "admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("sure_admin", null);

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("no_admin")).
                andReturn(createUser("no_admin", "No Administrator",
                "no-admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("no_admin", "Password");

        Assert.assertTrue(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("no_admin")).
                andReturn(createUser("no_admin", "No Administrator",
                "no-admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("no_admin", "bad_password");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("no_admin")).
                andReturn(createUser("no_admin", "No Administrator",
                "no-admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("no_admin", "");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("no_admin")).
                andReturn(createUser("no_admin", "No Administrator",
                "no-admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("no_admin", null);

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("none_existent")).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("none_existent", "Password");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("none_existent")).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("none_existent", "");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("none_existent")).
                andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("none_existent", null);

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("")).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("", "Password");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("")).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("", "");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("")).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin("", null);

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName(null)).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin(null, "Password");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName(null)).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin(null, "");

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName(null)).andReturn(null);
        EasyMock.replay(userDAOMock);

        ret = userAccessBizOp.validateUserLogin(null, null);

        Assert.assertFalse(ret);

        EasyMock.reset(userDAOMock);
    }

}
