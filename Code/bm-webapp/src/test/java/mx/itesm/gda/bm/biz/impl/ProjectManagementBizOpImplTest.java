/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ProjectManagementBizOpImplTest.java 311 2010-11-11 03:02:03Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-10 21:02:03 -0600 (Wed, 10 Nov 2010) $
 * Last Version      : $Revision: 311 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.biz.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.test.utils.TestUtils;
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
 * @author $Author: inzunzo $
 * @version $Revision: 311 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"/testBizOpContext.xml"})
public class ProjectManagementBizOpImplTest {

    private static final Log LOGGER = LogFactory.getLog(
            ProjectManagementBizOpImplTest.class);
    @Autowired
    private ProjectManagementBizOp ProjectManagementBizOp;
    @Autowired
    private ProjectDAO projectDAOMock;
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

    private static Project createProject(Integer projectId, String projectName,
            List<Task> tasks, String projectDescription, Date projectDueDate,
            Date projectPlannedDate) {
        Project p = new Project();
        p.setProjectId(projectId);
        p.setProjectName(projectName);
        p.setProjectDescription(projectDescription);
        p.setProjectDueDate(projectDueDate);
        p.setProjectPlannedDate(projectPlannedDate);
        p.setTasks(tasks);
        return p;
    }

    private static Date createDate(int day, int month, int year) {
        Calendar that_day = new GregorianCalendar(year, month - 1, day);
        return that_day.getTime();
    }

    @Test
    @Transactional
    public void testRetrieveProjects() {
        LOGGER.info("checkRetrieveProjects");

        List<Task> t = new ArrayList<Task>();
        List<Project> ps = new ArrayList<Project>();
        ps.add(createProject(11, "Proyecto1", t, "Desc1", createDate(11, 2, 2010), createDate(13, 2, 2010)));
        ps.add(createProject(12, "Proyecto2", t, "Desc2", createDate(12, 2, 2010), createDate(14, 2, 2010)));

        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        for (Project project : ps) {
            Map<String, Object> p = new HashMap<String, Object>();
            p.put("projectId", project.getProjectId());
            p.put("projectName", project.getProjectName());
            p.put("projectDescription", project.getProjectDescription());
            p.put("projectDueDate", project.getProjectDueDate());
            p.put("projectPlannedDate", project.getProjectPlannedDate());
            p.put("empty", project.getTasks().isEmpty());
            ret.add(p);
        }

        EasyMock.expect(userDAOMock.findByUserName("user1")).
                andReturn(createUser("user1", "Administrator",
                "admin@bipolar.mx", "Password", 30, null, null));
        EasyMock.expect(projectDAOMock.getAll()).andReturn(ps);
        EasyMock.replay(userDAOMock, projectDAOMock);

        List<Map<String, ?>> gotPs = ProjectManagementBizOp.retrieveProjects("user1");
        Assert.assertEquals(gotPs, ret);

        EasyMock.reset(userDAOMock, projectDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("user1")).
                andReturn(createUser("user1", "Administrator",
                "admin@bipolar.mx", "Password", 10, null, null));
        EasyMock.expect(projectDAOMock.searchByUserNameEnrolled("user1")).andReturn(ps);
        EasyMock.replay(userDAOMock, projectDAOMock);

        List<Map<String, ?>> gotPs2 = ProjectManagementBizOp.retrieveProjects("user1");
        Assert.assertEquals(gotPs2, ret);

        EasyMock.reset(userDAOMock, projectDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("nonExistantUser")).andReturn(null);
        EasyMock.replay(userDAOMock);

        List<Map<String, ?>> gotPs3 = ProjectManagementBizOp.retrieveProjects("nonExistantUser");
        Assert.assertEquals(gotPs3, new ArrayList<Map<String, ?>>());

        EasyMock.reset(userDAOMock);
        EasyMock.expect(userDAOMock.findByUserName("")).andReturn(null);
        EasyMock.replay(userDAOMock);

        List<Map<String, ?>> gotPs4 = ProjectManagementBizOp.retrieveProjects("");
        Assert.assertEquals(gotPs4, new ArrayList<Map<String, ?>>());

        EasyMock.reset(userDAOMock);

    }

    /**
     * Test of createProject method, of class ProjectManagementBizOpImpl.
     */
    @Test
    @Transactional
    public void testCreateProject() {
        LOGGER.info("checkCreateProject");

        Capture<Project> p = new Capture<Project>();
        projectDAOMock.save(EasyMock.and(EasyMock.isA(Project.class),
                EasyMock.and(EasyMock.capture(p),
                (Project) TestUtils.setId(30))));
        EasyMock.expectLastCall();
        EasyMock.replay(projectDAOMock);

        int Id = ProjectManagementBizOp.createProject("Proyecto1", "Desc1",
                createDate(12, 4, 2010), createDate(15, 5, 2009));
        Assert.assertEquals(30, Id);
        Project proj = p.getValue();
        Assert.assertEquals("Proyecto1", proj.getProjectName());
        Assert.assertEquals("Desc1", proj.getProjectDescription());
        Assert.assertEquals(createDate(12, 4, 2010), proj.getProjectDueDate());
        Assert.assertEquals(createDate(15, 5, 2009), proj.getProjectPlannedDate());
        EasyMock.reset(projectDAOMock);


        p = new Capture<Project>();
        projectDAOMock.save(EasyMock.and(EasyMock.isA(Project.class),
                EasyMock.and(EasyMock.capture(p),
                (Project) TestUtils.setId(30))));
        EasyMock.expectLastCall();
        EasyMock.replay(projectDAOMock);

        Id = ProjectManagementBizOp.createProject("", "Desc1",
                createDate(12, 4, 2010), createDate(15, 5, 2009));
        Assert.assertEquals(30, Id);
        proj = p.getValue();
        Assert.assertEquals("", proj.getProjectName());
        Assert.assertEquals("Desc1", proj.getProjectDescription());
        Assert.assertEquals(createDate(12, 4, 2010), proj.getProjectDueDate());
        Assert.assertEquals(createDate(15, 5, 2009), proj.getProjectPlannedDate());

        EasyMock.reset(projectDAOMock);

        p = new Capture<Project>();
        projectDAOMock.save(EasyMock.and(EasyMock.isA(Project.class),
                EasyMock.and(EasyMock.capture(p),
                (Project) TestUtils.setId(30))));
        EasyMock.expectLastCall();
        EasyMock.replay(projectDAOMock);
        Id = ProjectManagementBizOp.createProject("Proyecto1", "",
                createDate(12, 4, 2010), createDate(15, 5, 2009));
        Assert.assertEquals(30, Id);
        proj = p.getValue();
        Assert.assertEquals("Proyecto1", proj.getProjectName());
        Assert.assertEquals("", proj.getProjectDescription());
        Assert.assertEquals(createDate(12, 4, 2010), proj.getProjectDueDate());
        Assert.assertEquals(createDate(15, 5, 2009), proj.getProjectPlannedDate());
        EasyMock.reset(projectDAOMock);


    }

    /**
     * Test of updateProject method, of class ProjectManagementBizOpImpl.
     */
    @Test
    @Transactional
    public void testUpdateProject() {
        LOGGER.info("checkUpdateProject");
        Capture<Project> p = new Capture<Project>();
        projectDAOMock.update(EasyMock.and(EasyMock.isA(Project.class), EasyMock.capture(p)));
        EasyMock.expectLastCall();
        EasyMock.replay(projectDAOMock);
        boolean exceptionThrown = false;
        try {
            ProjectManagementBizOp.updateProject(30, "Proyecto2", "Desc2",
                    createDate(10, 2, 2003), createDate(20, 4, 2019));
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectDAOMock);
        }
        Assert.assertFalse(exceptionThrown);
        Project proj = p.getValue();
        Assert.assertEquals("Proyecto2", proj.getProjectName());
        Assert.assertEquals("Desc2", proj.getProjectDescription());
        Assert.assertEquals(createDate(10, 2, 2003), proj.getProjectDueDate());
        Assert.assertEquals(createDate(20, 4, 2019), proj.getProjectPlannedDate());

        p = new Capture<Project>();
        projectDAOMock.update(EasyMock.and(EasyMock.isA(Project.class), EasyMock.capture(p)));
        EasyMock.expectLastCall();
        EasyMock.replay(projectDAOMock);
        exceptionThrown = false;
        try {
            ProjectManagementBizOp.updateProject(30, "", "Desc2",
                    createDate(10, 2, 2003), createDate(20, 4, 2019));
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectDAOMock);
        }
        Assert.assertFalse(exceptionThrown);
        proj = p.getValue();
        Assert.assertEquals("", proj.getProjectName());
        Assert.assertEquals("Desc2", proj.getProjectDescription());
        Assert.assertEquals(createDate(10, 2, 2003), proj.getProjectDueDate());
        Assert.assertEquals(createDate(20, 4, 2019), proj.getProjectPlannedDate());

        p = new Capture<Project>();
        projectDAOMock.update(EasyMock.and(EasyMock.isA(Project.class), EasyMock.capture(p)));
        EasyMock.expectLastCall();
        EasyMock.replay(projectDAOMock);
        exceptionThrown = false;
        try {
            ProjectManagementBizOp.updateProject(30, "Proyecto2", "",
                    createDate(10, 2, 2003), createDate(20, 4, 2019));
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectDAOMock);
        }
        Assert.assertFalse(exceptionThrown);
        proj = p.getValue();
        Assert.assertEquals("Proyecto2", proj.getProjectName());
        Assert.assertEquals("", proj.getProjectDescription());
        Assert.assertEquals(createDate(10, 2, 2003), proj.getProjectDueDate());
        Assert.assertEquals(createDate(20, 4, 2019), proj.getProjectPlannedDate());


    }

    /**
     * Test of deleteProject method, of class ProjectManagementBizOpImpl.
     */
    @Test
    @Transactional
    public void testDeleteProject() {
        LOGGER.info("checkDeleteProject");
        boolean exceptionThrown = false;
        List<Task> t = new ArrayList<Task>();
        Project p = createProject(30, "Proyecto1", t, "Desc1",
                createDate(11, 2, 2010), createDate(13, 2, 2010));

        EasyMock.expect(projectDAOMock.findById(30)).andReturn(p);
        Capture<Project> capturedProjectMock = new Capture<Project>();
        projectDAOMock.delete(EasyMock.and(EasyMock.isA(Project.class), EasyMock.capture(capturedProjectMock)));
        EasyMock.replay(projectDAOMock);

        try {
            ProjectManagementBizOp.deleteProject(30);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectDAOMock);
        }
        Assert.assertFalse(exceptionThrown);
        Project capturedProject = capturedProjectMock.getValue();
        Assert.assertEquals("Proyecto1", capturedProject.getProjectName());
        Assert.assertEquals("Desc1", capturedProject.getProjectDescription());
        Assert.assertEquals(createDate(11, 2, 2010), capturedProject.getProjectDueDate());
        Assert.assertEquals(createDate(13, 2, 2010), capturedProject.getProjectPlannedDate());


        t.add(new Task());
        p = createProject(30, "Proyecto1", t, "Desc1",
                createDate(11, 2, 2010), createDate(13, 2, 2010));

        EasyMock.expect(projectDAOMock.findById(30)).andReturn(p);
        capturedProjectMock = new Capture<Project>();
        projectDAOMock.delete(EasyMock.and(EasyMock.isA(Project.class), EasyMock.capture(capturedProjectMock)));
        EasyMock.replay(projectDAOMock);
        exceptionThrown = false;
        try {
            ProjectManagementBizOp.deleteProject(30);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectDAOMock);
        }

        Assert.assertTrue(exceptionThrown);


    }

    /**
     * Test of getProject method, of class ProjectManagementBizOpImpl.
     */
    @Test
    @Transactional
    public void testGetProject() {
        LOGGER.info("checkGetProject");
        List<Task> t = new ArrayList<Task>();
        Project pr = createProject(30, "Proyecto1", t, "Desc1",
                createDate(11, 2, 2010), createDate(13, 2, 2010));

        Map<String, Object> p = new HashMap<String, Object>();
        p.put("projectId", pr.getProjectId());
        p.put("projectName", pr.getProjectName());
        p.put("projectDescription", pr.getProjectDescription());
        p.put("projectDueDate", pr.getProjectDueDate());
        p.put("projectPlannedDate", pr.getProjectPlannedDate());
        p.put("empty", pr.getTasks().isEmpty());

        EasyMock.expect(projectDAOMock.findById(30)).andReturn(pr);
        EasyMock.replay(projectDAOMock);

        Map<String, ?> getP = ProjectManagementBizOp.getProject(30);

        Assert.assertEquals(p, getP);
        EasyMock.reset(projectDAOMock);



        p = new HashMap<String, Object>();
        EasyMock.expect(projectDAOMock.findById(40)).andReturn(null);
        EasyMock.replay(projectDAOMock);

        getP = ProjectManagementBizOp.getProject(40);

        Assert.assertEquals(p, getP);
        EasyMock.reset(projectDAOMock);

    }
}
