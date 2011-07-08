/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserManagementBizOpImplTest.java 347 2010-11-18 03:01:01Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-17 21:01:01 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 347 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.biz.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskComment;
import mx.itesm.gda.bm.model.TaskCompletionReport;
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
 * @author hgrobles
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"/testBizOpContext.xml"})
public class UserManagementBizOpImplTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            UserManagementBizOpImplTest.class);
    @Autowired
    private UserManagementBizOp userManagementBizOp;
    @Autowired
    private UserDAO userDAOMock;

    private static List<User> createUserList() {
        List<User> userList = new ArrayList<User>();

        User u = new User();
        u.setUserName("user1");
        u.setFullName("user1");
        u.setEmail("user1@email.com");
        u.setPassword("123");
        u.setAdministrator(Boolean.TRUE);
        u.setAssignedTasks(Collections.EMPTY_LIST);
        u.setAuthoredComments(Collections.EMPTY_LIST);
        u.setAuthoredCompletionReports(Collections.EMPTY_LIST);
        userList.add(u);

        Task task1 = new Task();
        List<Task> taskList = new ArrayList<Task>();
        taskList.add(task1);

        TaskComment comment1 = new TaskComment();
        List<TaskComment> commentList = new ArrayList<TaskComment>();
        commentList.add(comment1);

        TaskCompletionReport report1 = new TaskCompletionReport();
        List<TaskCompletionReport> reportList = new ArrayList<TaskCompletionReport>();
        reportList.add(report1);

        User u2 = new User();
        u2.setUserName("user2");
        u2.setFullName("user2");
        u2.setEmail("user2@email.com");
        u2.setPassword("123");
        u2.setAdministrator(Boolean.FALSE);
        u2.setAssignedTasks(taskList);
        u2.setAuthoredComments(commentList);
        u2.setAuthoredCompletionReports(reportList);
        userList.add(u2);

        User u3 = new User();
        u3.setUserName("user3");
        u3.setFullName("user3");
        u3.setEmail("user3@email.com");
        u3.setPassword("123");
        u3.setAdministrator(Boolean.FALSE);
        u3.setAssignedTasks(taskList);
        u3.setAuthoredComments(commentList);
        u3.setAuthoredCompletionReports(Collections.EMPTY_LIST);
        userList.add(u3);

        User u4 = new User();
        u4.setUserName("user4");
        u4.setFullName("user4");
        u4.setEmail("user4@email.com");
        u4.setPassword("123");
        u4.setAdministrator(Boolean.FALSE);
        u4.setAssignedTasks(Collections.EMPTY_LIST);
        u4.setAuthoredComments(commentList);
        u4.setAuthoredCompletionReports(reportList);
        userList.add(u4);

        User u5 = new User();
        u5.setUserName("user5");
        u5.setFullName("user5");
        u5.setEmail("user5@email.com");
        u5.setPassword("123");
        u5.setAdministrator(Boolean.FALSE);
        u5.setAssignedTasks(taskList);
        u5.setAuthoredComments(Collections.EMPTY_LIST);
        u5.setAuthoredCompletionReports(reportList);
        userList.add(u5);

        User u6 = new User();
        u6.setUserName("user6");
        u6.setFullName("user6");
        u6.setEmail("user6@email.com");
        u6.setPassword("123");
        u6.setAdministrator(Boolean.FALSE);
        u6.setAssignedTasks(taskList);
        u6.setAuthoredComments(Collections.EMPTY_LIST);
        u6.setAuthoredCompletionReports(Collections.EMPTY_LIST);
        userList.add(u6);

        User u7 = new User();
        u7.setUserName("user7");
        u7.setFullName("user7");
        u7.setEmail("user7@email.com");
        u7.setPassword("123");
        u7.setAdministrator(Boolean.FALSE);
        u7.setAssignedTasks(Collections.EMPTY_LIST);
        u7.setAuthoredComments(commentList);
        u7.setAuthoredCompletionReports(Collections.EMPTY_LIST);
        userList.add(u7);

        User u8 = new User();
        u8.setUserName("user8");
        u8.setFullName("user8");
        u8.setEmail("user8@email.com");
        u8.setPassword("123");
        u8.setAdministrator(Boolean.FALSE);
        u8.setAssignedTasks(Collections.EMPTY_LIST);
        u8.setAuthoredComments(Collections.EMPTY_LIST);
        u8.setAuthoredCompletionReports(reportList);
        userList.add(u8);

        return userList;
    }

    private static User createUser(String userName, String fullName,
            String email, String password, boolean administrator) {
        User u = new User();
        u.setUserName(userName);
        u.setFullName(fullName);
        u.setEmail(email);
        u.setPassword(password);
        u.setAdministrator(administrator);
        return u;
    }

    @Test
    @Transactional
    public void testRetrieveUsers() {
        LOGGER.info("retrieveUsers");
        EasyMock.expect(userDAOMock.getAll()).
                andReturn(createUserList());
        EasyMock.replay(userDAOMock);

        List<Map<String, ?>> users = userManagementBizOp.retrieveUsers();

        Assert.assertEquals(users.size(), 8);
        Assert.assertEquals(users.get(0).get("userName"), "user1");
        Assert.assertEquals(users.get(0).get("fullName"), "user1");
        Assert.assertEquals(users.get(0).get("email"), "user1@email.com");
        Assert.assertEquals(users.get(0).get("isAdministrator"), true);
        Assert.assertEquals(users.get(0).get("isEmpty"), true);

        Assert.assertEquals(users.get(1).get("userName"), "user2");
        Assert.assertEquals(users.get(1).get("fullName"), "user2");
        Assert.assertEquals(users.get(1).get("email"), "user2@email.com");
        Assert.assertEquals(users.get(1).get("isAdministrator"), false);
        Assert.assertEquals(users.get(1).get("isEmpty"), false);

        Assert.assertEquals(users.get(2).get("userName"), "user3");
        Assert.assertEquals(users.get(2).get("fullName"), "user3");
        Assert.assertEquals(users.get(2).get("email"), "user3@email.com");
        Assert.assertEquals(users.get(2).get("isAdministrator"), false);
        Assert.assertEquals(users.get(2).get("isEmpty"), false);

        Assert.assertEquals(users.get(3).get("userName"), "user4");
        Assert.assertEquals(users.get(3).get("fullName"), "user4");
        Assert.assertEquals(users.get(3).get("email"), "user4@email.com");
        Assert.assertEquals(users.get(3).get("isAdministrator"), false);
        Assert.assertEquals(users.get(3).get("isEmpty"), false);

        Assert.assertEquals(users.get(4).get("userName"), "user5");
        Assert.assertEquals(users.get(4).get("fullName"), "user5");
        Assert.assertEquals(users.get(4).get("email"), "user5@email.com");
        Assert.assertEquals(users.get(4).get("isAdministrator"), false);
        Assert.assertEquals(users.get(4).get("isEmpty"), false);

        Assert.assertEquals(users.get(5).get("userName"), "user6");
        Assert.assertEquals(users.get(5).get("fullName"), "user6");
        Assert.assertEquals(users.get(5).get("email"), "user6@email.com");
        Assert.assertEquals(users.get(5).get("isAdministrator"), false);
        Assert.assertEquals(users.get(5).get("isEmpty"), false);

        Assert.assertEquals(users.get(6).get("userName"), "user7");
        Assert.assertEquals(users.get(6).get("fullName"), "user7");
        Assert.assertEquals(users.get(6).get("email"), "user7@email.com");
        Assert.assertEquals(users.get(6).get("isAdministrator"), false);
        Assert.assertEquals(users.get(6).get("isEmpty"), false);

        Assert.assertEquals(users.get(7).get("userName"), "user8");
        Assert.assertEquals(users.get(7).get("fullName"), "user8");
        Assert.assertEquals(users.get(7).get("email"), "user8@email.com");
        Assert.assertEquals(users.get(7).get("isAdministrator"), false);
        Assert.assertEquals(users.get(7).get("isEmpty"), false);

        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testCreateUser() {
        LOGGER.info("createUser");
        Capture<User> createdUser = new Capture<User>();
        userDAOMock.save(EasyMock.and(EasyMock.isA(User.class),
                EasyMock.capture(createdUser)));
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        String name = userManagementBizOp.createUser("test1", true, "test1", "123", "test1@email.com");

        User the_user = createdUser.getValue();
        Assert.assertEquals(name, the_user.getUserName());
        Assert.assertEquals(true, the_user.isAdministrator());
        Assert.assertEquals("test1", the_user.getFullName());
        Assert.assertEquals("123", the_user.getPassword());
        Assert.assertEquals("test1@email.com", the_user.getEmail());
        EasyMock.reset(userDAOMock);



        createdUser = new Capture<User>();
        userDAOMock.save(EasyMock.and(EasyMock.isA(User.class),
                EasyMock.capture(createdUser)));
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        name = userManagementBizOp.createUser("", false, "", "", "");

        the_user = createdUser.getValue();
        Assert.assertEquals(name, the_user.getUserName());
        Assert.assertEquals(false, the_user.isAdministrator());
        Assert.assertEquals("", the_user.getFullName());
        Assert.assertEquals("", the_user.getPassword());
        Assert.assertEquals("", the_user.getEmail());
        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testDeleteUser() {
        LOGGER.info("deleteUser");
        boolean exceptionThrown = false;

        //Caso de que trate de borrar un usuario nulo
        EasyMock.expect(userDAOMock.findByUserName("username1")).andReturn(null);
        userDAOMock.delete(new User());
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        try {
            userManagementBizOp.deleteUser("username1");
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userDAOMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que trate de borrar un usuario existente pero vacío
        exceptionThrown = false;
        User u = createUser("uName", "fullName", "mail@mail.com", "pass", false);
        u.setAssignedTasks(new ArrayList<Task>());
        u.setAuthoredComments(new ArrayList<TaskComment>());
        u.setAuthoredCompletionReports(new ArrayList<TaskCompletionReport>());
        EasyMock.expect(userDAOMock.findByUserName("username1")).andReturn(u);
        Capture<User> capturedUserMock = new Capture<User>();
        userDAOMock.delete(EasyMock.and(EasyMock.isA(User.class),
                EasyMock.capture(capturedUserMock)));
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        try {
            userManagementBizOp.deleteUser("username1");
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userDAOMock);
        }
        Assert.assertFalse(exceptionThrown);
        User capturedUser = capturedUserMock.getValue();
        Assert.assertEquals("uName", capturedUser.getUserName());
        Assert.assertEquals("fullName", capturedUser.getFullName());
        Assert.assertEquals("mail@mail.com", capturedUser.getEmail());
        Assert.assertEquals("pass", capturedUser.getPassword());
        Assert.assertEquals(false, capturedUser.isAdministrator());

        //Caso de que intente borrar un usuario existente con tasks
        List<Task> t = new ArrayList<Task>();
        t.add(new Task());
        u.setAssignedTasks(t);
        EasyMock.expect(userDAOMock.findByUserName("username1")).andReturn(u);
        userDAOMock.delete(u);
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        try {
            userManagementBizOp.deleteUser("username1");
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userDAOMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que intente borrar un usuario existente con taskscomments
        u.setAssignedTasks(new ArrayList<Task>());
        List<TaskComment> tc = new ArrayList<TaskComment>();
        tc.add(new TaskComment());
        u.setAuthoredComments(tc);
        EasyMock.expect(userDAOMock.findByUserName("username1")).andReturn(u);
        userDAOMock.delete(u);
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        try {
            userManagementBizOp.deleteUser("username1");
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userDAOMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que intente borrar un usuario existente con tasks
        u.setAuthoredComments(new ArrayList<TaskComment>());
        List<TaskCompletionReport> tcr = new ArrayList<TaskCompletionReport>();
        tcr.add(new TaskCompletionReport());
        u.setAuthoredCompletionReports(tcr);
        EasyMock.expect(userDAOMock.findByUserName("username1")).andReturn(u);
        userDAOMock.delete(u);
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        try {
            userManagementBizOp.deleteUser("username1");
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userDAOMock);
        }
        Assert.assertTrue(exceptionThrown);
    }

    @Test
    @Transactional
    public void testModifyUser() {
        LOGGER.info("modifyUser");
        boolean exceptionThrown = false;

        EasyMock.expect(userDAOMock.findByUserName("uName")).andReturn(null);
        userDAOMock.update(new User());
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        try {
            userManagementBizOp.modifyUser("uName", true, "fullName", "mail@mail.com");
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userDAOMock);
        }
        Assert.assertTrue(exceptionThrown);


        User u = createUser("uName", "fullName", "mail@mail.com", "pass", true);
        EasyMock.expect(userDAOMock.findByUserName("uName")).andReturn(u);
        Capture<User> capturedUserMock = new Capture<User>();
        userDAOMock.update(EasyMock.and(EasyMock.isA(User.class), EasyMock.capture(capturedUserMock)));
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);

        userManagementBizOp.modifyUser("uName", false, "newFullName", "newMail@mail.com");

        User capturedUser = capturedUserMock.getValue();
        Assert.assertEquals("uName", capturedUser.getUserName());
        Assert.assertEquals(false, capturedUser.isAdministrator());
        Assert.assertEquals("newFullName", capturedUser.getFullName());
        Assert.assertEquals("newMail@mail.com", capturedUser.getEmail());
        Assert.assertEquals("pass", capturedUser.getPassword());
        EasyMock.reset(userDAOMock);



        u = createUser("uName", "fullName", "mail@mail.com", "pass", true);
        EasyMock.expect(userDAOMock.findByUserName("uName")).andReturn(u);
        capturedUserMock = new Capture<User>();
        userDAOMock.update(EasyMock.and(EasyMock.isA(User.class), EasyMock.capture(capturedUserMock)));
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);

        userManagementBizOp.modifyUser("uName", false, "", "");

        capturedUser = capturedUserMock.getValue();
        Assert.assertEquals("uName", capturedUser.getUserName());
        Assert.assertEquals(false, capturedUser.isAdministrator());
        Assert.assertEquals("", capturedUser.getFullName());
        Assert.assertEquals("", capturedUser.getEmail());
        Assert.assertEquals("pass", capturedUser.getPassword());
        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testModifyUserPassword() {
        LOGGER.info("modifyUserPassword");
        boolean exceptionThrown = false;

        EasyMock.expect(userDAOMock.findByUserName("uName")).andReturn(null);
        userDAOMock.update(new User());
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);
        try {
            userManagementBizOp.modifyUserPassword("uName", "newPass");

        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userDAOMock);
        }
        Assert.assertTrue(exceptionThrown);


        User u = createUser("uName", "fullName", "mail@mail.com", "pass", true);
        EasyMock.expect(userDAOMock.findByUserName("uName")).andReturn(u);
        Capture<User> capturedUserMock = new Capture<User>();
        userDAOMock.update(EasyMock.and(EasyMock.isA(User.class), EasyMock.capture(capturedUserMock)));
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);

        userManagementBizOp.modifyUserPassword("uName", "newPass");

        User capturedUser = capturedUserMock.getValue();
        Assert.assertEquals("uName", capturedUser.getUserName());
        Assert.assertEquals(true, capturedUser.isAdministrator());
        Assert.assertEquals("fullName", capturedUser.getFullName());
        Assert.assertEquals("mail@mail.com", capturedUser.getEmail());
        Assert.assertEquals("newPass", capturedUser.getPassword());
        EasyMock.reset(userDAOMock);


        u = createUser("uName", "fullName", "mail@mail.com", "pass", true);
        EasyMock.expect(userDAOMock.findByUserName("uName")).andReturn(u);
        capturedUserMock = new Capture<User>();
        userDAOMock.update(EasyMock.and(EasyMock.isA(User.class), EasyMock.capture(capturedUserMock)));
        EasyMock.expectLastCall();
        EasyMock.replay(userDAOMock);

        userManagementBizOp.modifyUserPassword("uName", "");

        capturedUser = capturedUserMock.getValue();
        Assert.assertEquals("uName", capturedUser.getUserName());
        Assert.assertEquals(true, capturedUser.isAdministrator());
        Assert.assertEquals("fullName", capturedUser.getFullName());
        Assert.assertEquals("mail@mail.com", capturedUser.getEmail());
        Assert.assertEquals("", capturedUser.getPassword());
        EasyMock.reset(userDAOMock);
    }

    @Test
    @Transactional
    public void testGetUser() {
        LOGGER.info("getUser");

        EasyMock.expect(userDAOMock.findByUserName("uName")).andReturn(null);
        EasyMock.replay(userDAOMock);

        Map<String, ?> gotMap = userManagementBizOp.getUser("uName");

        Assert.assertNull(gotMap);
        EasyMock.reset(userDAOMock);


        User u = createUser("uName", "phonyFullName", "mail@mail.com", "pass", true);
        EasyMock.expect(userDAOMock.findByUserName("uName")).andReturn(u);
        EasyMock.replay(userDAOMock);

        gotMap = userManagementBizOp.getUser("uName");

        Assert.assertEquals("uName",gotMap.get("userName"));
        Assert.assertEquals("phonyFullName",gotMap.get("fullName"));
        Assert.assertEquals(true,gotMap.get("isAdministrator"));
        Assert.assertEquals("mail@mail.com",gotMap.get("email"));
        EasyMock.reset(userDAOMock);


        u = createUser("", "", "", "", false);
        EasyMock.expect(userDAOMock.findByUserName("")).andReturn(u);
        EasyMock.replay(userDAOMock);

        gotMap = userManagementBizOp.getUser("");

        Assert.assertEquals("",gotMap.get("userName"));
        Assert.assertEquals("",gotMap.get("fullName"));
        Assert.assertEquals(false,gotMap.get("isAdministrator"));
        Assert.assertEquals("",gotMap.get("email"));
        EasyMock.reset(userDAOMock);
    }


    @Test
    @Transactional
    public void testGetUserByEmail() {
        LOGGER.info("getUserByEmail");

        EasyMock.expect(userDAOMock.findByEmail("mail@mail.com")).andReturn(null);
        EasyMock.replay(userDAOMock);

        Map<String, ?> gotMap = userManagementBizOp.getUserByEmail("mail@mail.com");

        Assert.assertNull(gotMap);
        EasyMock.reset(userDAOMock);


        User u = createUser("uName", "phonyFullName", "mail@mail.com", "pass", true);
        EasyMock.expect(userDAOMock.findByEmail("mail@mail.com")).andReturn(u);
        EasyMock.replay(userDAOMock);

        gotMap = userManagementBizOp.getUserByEmail("mail@mail.com");

        Assert.assertEquals("uName",gotMap.get("userName"));
        Assert.assertEquals("phonyFullName",gotMap.get("fullName"));
        Assert.assertEquals(true,gotMap.get("isAdministrator"));
        Assert.assertEquals("mail@mail.com",gotMap.get("email"));
        EasyMock.reset(userDAOMock);


        u = createUser("", "", "", "", false);
        EasyMock.expect(userDAOMock.findByEmail("")).andReturn(u);
        EasyMock.replay(userDAOMock);

        gotMap = userManagementBizOp.getUserByEmail("");

        Assert.assertEquals("",gotMap.get("userName"));
        Assert.assertEquals("",gotMap.get("fullName"));
        Assert.assertEquals(false,gotMap.get("isAdministrator"));
        Assert.assertEquals("",gotMap.get("email"));
        EasyMock.reset(userDAOMock);
    }
}
