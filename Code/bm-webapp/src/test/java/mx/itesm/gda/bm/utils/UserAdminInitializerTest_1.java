/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserAdminInitializerTest.java 352 2010-11-18 05:44:18Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-17 23:44:18 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 352 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.utils;

import java.util.Collections;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

/**
 *
 * @author $Author: inzunzo $
 * @version $Revision: 352 $
 */
public class UserAdminInitializerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            UserAdminInitializerTest.class);
    private UserAdminInitializer userAdminInitializer;
    private UserDAO userDAOMock;
    
    @Before
    public void setUp() {
        userAdminInitializer = new UserAdminInitializer();
        userDAOMock = EasyMock.createMock(UserDAO.class);
        ReflectionTestUtils.setField(userAdminInitializer, "userDAO",
                userDAOMock);
        userAdminInitializer.setUserName("some_admin");
        userAdminInitializer.setFullName("Full Name");
        userAdminInitializer.setEmail("some@email.com");
        userAdminInitializer.setPassword("some_password");
    }

    /**
     * Test of init method, of class UserAdminInitializer.
     */
    @Test
    public void testInit() {
        LOGGER.info("init");

        EasyMock.expect(userDAOMock.getAllAdministrators()).andReturn(
                Collections.EMPTY_LIST);
        Capture<User> capt = new Capture<User>();
        userDAOMock.save(EasyMock.and(EasyMock.capture(capt),
                EasyMock.isA(User.class)));
        EasyMock.replay(userDAOMock);

        userAdminInitializer.init();

        User u = capt.getValue();

        Assert.assertNotNull(u);
        Assert.assertEquals("some_admin", u.getUserName());
        Assert.assertEquals("Full Name", u.getFullName());
        Assert.assertEquals("some@email.com", u.getEmail());
        Assert.assertEquals("some_password", u.getPassword());
        Assert.assertTrue(u.isAdministrator());
        Assert.assertNull(u.getPasswordRecoveryTicket());
        Assert.assertNull(u.getPasswordRecoveryExpiration());

        EasyMock.resetToStrict(userDAOMock);
        u = new User();
        u.setUserName("some_admin");
        u.setAdministrator(true);
        EasyMock.expect(userDAOMock.getAllAdministrators()).andReturn(
                Collections.singletonList(u));
        EasyMock.replay(userDAOMock);

        userAdminInitializer.init();

        EasyMock.verify(userDAOMock);

        EasyMock.reset(userDAOMock);
    }

    @Test
    public void testGetters() {
        Assert.assertEquals("some_admin", userAdminInitializer.getUserName());
        Assert.assertEquals("Full Name", userAdminInitializer.getFullName());
        Assert.assertEquals("some@email.com", userAdminInitializer.getEmail());
        Assert.assertEquals("some_password", userAdminInitializer.getPassword());
    }
}
