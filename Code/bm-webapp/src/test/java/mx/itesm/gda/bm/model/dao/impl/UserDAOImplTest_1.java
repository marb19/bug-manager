/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserDAOImplTest.java 352 2010-11-18 05:44:18Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-17 23:44:18 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 352 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.BitSet;
import java.util.Date;
import java.util.List;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.test.data.DAODataTester;
import mx.itesm.gda.bm.test.utils.TestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: inzunzo $
 * @version $Revision: 352 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testDAOContext.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserDAOImplTest_1 extends DAODataTester {

    private static final Log LOGGER = LogFactory.getLog(UserDAOImplTest.class);

    @Autowired
    private UserDAO userDAO;

    /**
     * Test of getAll method, of class UserDAOImpl.
     */
    @Test
    @Transactional
    public void testGetAll() {
        LOGGER.info("getAll");
        BitSet bitset = new BitSet();
        bitset.set(0, user_usernames.length);
        List<User> users = userDAO.getAll();
        OUTER:
        for(User u : users) {
            for(int i = bitset.nextSetBit(0); i >= 0;
                    i = bitset.nextSetBit(i + 1)) {

                if(user_usernames[i].equals(u.getUserName())) {
                    bitset.clear(i);

                    Assert.assertEquals(u.getUserName(), user_usernames[i]);
                    Assert.assertEquals(u.getPassword(), user_passwords[i]);
                    Assert.assertEquals(u.getFullName(), user_fullnames[i]);
                    Assert.assertEquals(u.isAdministrator(),
                            user_administrators[i]);
                    Assert.assertEquals(u.getEmail(), user_emails[i]);
                    Assert.assertEquals(u.getPasswordRecoveryTicket(),
                            user_prtickets[i]);
                    Assert.assertEquals(u.getPasswordRecoveryExpiration(),
                            user_prtexpirations[i]);

                    continue OUTER;
                }
            }

            Assert.fail();
        }

        Assert.assertTrue(bitset.isEmpty());
    }

    /**
     * Test of getAllAdministrators method, of class UserDAOImpl.
     */
    @Test
    @Transactional
    public void testGetAllAdministrators() {
        LOGGER.info("getAllAdministrators");
        BitSet bitset = new BitSet();
        bitset.set(0, user_usernames.length);
        List<User> users = userDAO.getAllAdministrators();
        OUTER:
        for(User u : users) {
            Assert.assertTrue(u.isAdministrator());
            for(int i = bitset.nextSetBit(0); i >= 0;
                    i = bitset.nextSetBit(i + 1)) {
                if(user_usernames[i].equals(u.getUserName())) {
                    bitset.clear(i);

                    Assert.assertEquals(u.getUserName(), user_usernames[i]);
                    Assert.assertEquals(u.getPassword(), user_passwords[i]);
                    Assert.assertEquals(u.getFullName(), user_fullnames[i]);
                    Assert.assertEquals(u.isAdministrator(),
                            user_administrators[i]);
                    Assert.assertEquals(u.getEmail(), user_emails[i]);
                    Assert.assertEquals(u.getPasswordRecoveryTicket(),
                            user_prtickets[i]);
                    Assert.assertEquals(u.getPasswordRecoveryExpiration(),
                            user_prtexpirations[i]);

                    continue OUTER;
                }
            }

            Assert.fail();
        }

        for(int i = bitset.nextSetBit(0); i >= 0;
                i = bitset.nextSetBit(i + 1)) {
            Assert.assertFalse(user_administrators[i]);
            bitset.clear(i);
        }

        Assert.assertTrue(bitset.isEmpty());
    }

    /**
     * Test of findByUserName method, of class UserDAOImpl.
     */
    @Test
    @Transactional
    public void testFindByUserName() {
        LOGGER.info("findByUserName");

        for(int i = 0; i < user_usernames.length; i++) {
            User u = userDAO.findByUserName(user_usernames[i]);
            Assert.assertNotNull(u);
            Assert.assertEquals(u.getUserName(), user_usernames[i]);
            Assert.assertEquals(u.getPassword(), user_passwords[i]);
            Assert.assertEquals(u.getFullName(), user_fullnames[i]);
            Assert.assertEquals(u.isAdministrator(), user_administrators[i]);
            Assert.assertEquals(u.getEmail(), user_emails[i]);
            Assert.assertEquals(u.getPasswordRecoveryTicket(), user_prtickets[i]);
            Assert.assertEquals(u.getPasswordRecoveryExpiration(),
                    user_prtexpirations[i]);
        }

        for(int i = 0; i < user_usernames.length; i++) {
            User u =
                    userDAO.findByUserName(TestUtils.reverse(user_usernames[i]));
            Assert.assertNull(u);
        }

        User u = userDAO.findByUserName("");
        Assert.assertNull(u);

        u = userDAO.findByUserName(null);
        Assert.assertNull(u);

    }

    /**
     * Test of findByRecoveryTicket method, of class UserDAOImpl.
     */
    @Test
    @Transactional
    public void testFindByRecoveryTicket() {
        LOGGER.info("findByRecoveryTicket");
        for(int i = 0; i < user_prtickets.length; i++) {
            if(user_prtickets[i] != null) {
                User u = userDAO.findByRecoveryTicket(user_prtickets[i]);
                if(user_prtexpirations[i].after(new Date())) {
                    Assert.assertNotNull(u);
                    Assert.assertEquals(u.getUserName(), user_usernames[i]);
                    Assert.assertEquals(u.getPassword(), user_passwords[i]);
                    Assert.assertEquals(u.getFullName(), user_fullnames[i]);
                    Assert.assertEquals(u.isAdministrator(),
                            user_administrators[i]);
                    Assert.assertEquals(u.getEmail(), user_emails[i]);
                    Assert.assertEquals(u.getPasswordRecoveryTicket(),
                            user_prtickets[i]);
                    Assert.assertEquals(u.getPasswordRecoveryExpiration(),
                            user_prtexpirations[i]);
                } else {
                    Assert.assertNull(u);
                }
            }
        }

        for(int i = 0; i < user_prtickets.length; i++) {
            if(user_prtickets[i] != null) {
                User u = userDAO.findByRecoveryTicket(TestUtils.reverse(
                        user_prtickets[i]));
                Assert.assertNull(u);
            }
        }

        User u = userDAO.findByRecoveryTicket("");
        Assert.assertNull(u);

        u = userDAO.findByRecoveryTicket(null);
        Assert.assertNull(u);
    }

    /**
     * Test of findByEmail method, of class UserDAOImpl.
     */
    @Test
    @Transactional
    public void testFindByEmail() {
        LOGGER.info("findByEmail");

        for(int i = 0; i < user_emails.length; i++) {
            User u = userDAO.findByEmail(user_emails[i]);
            Assert.assertNotNull(u);
            Assert.assertEquals(u.getUserName(), user_usernames[i]);
            Assert.assertEquals(u.getPassword(), user_passwords[i]);
            Assert.assertEquals(u.getFullName(), user_fullnames[i]);
            Assert.assertEquals(u.isAdministrator(), user_administrators[i]);
            Assert.assertEquals(u.getEmail(), user_emails[i]);
            Assert.assertEquals(u.getPasswordRecoveryTicket(), user_prtickets[i]);
            Assert.assertEquals(u.getPasswordRecoveryExpiration(),
                    user_prtexpirations[i]);
        }

        for(int i = 0; i < user_emails.length; i++) {
            User u = userDAO.findByEmail(TestUtils.reverse(user_emails[i]));
            Assert.assertNull(u);
        }

        User u = userDAO.findByEmail("");
        Assert.assertNull(u);

        u = userDAO.findByEmail(null);
        Assert.assertNull(u);
    }

}
