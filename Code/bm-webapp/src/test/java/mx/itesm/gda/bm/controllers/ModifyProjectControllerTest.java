/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ModifyProjectControllerTest.java 319 2010-11-12 13:30:55Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-12 07:30:55 -0600 (Fri, 12 Nov 2010) $
 * Last Version      : $Revision: 319 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.controllers;

import java.util.Calendar;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.easymock.Capture;
import org.easymock.EasyMock;
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
public class ModifyProjectControllerTest {

    private static final Log LOGGER = LogFactory.getLog(
            ModifyProjectControllerTest.class);
    @Autowired
    private ProjectManagementBizOp projectMgrMock;
    @Autowired
    private ModifyProjectController ModifyProjectController;

    private static Date createDate(int day, int month, int year) {
        Calendar that_day = new GregorianCalendar(year, month - 1, day);
        return that_day.getTime();
    }

    /**
     * Test of getProjectData method, of class ModifyProjectController.
     */
    @Test
    public void testGetProjectData() {
        LOGGER.info("checkTestGetProjectData");

        Map<String, Object> p = new HashMap<String, Object>();
        p.put("projectId", 30);
        p.put("projectName", "Proj1");
        p.put("projectDescription", "Desc1");
        p.put("projectDueDate", createDate(10, 2, 2010));
        p.put("projectPlannedDate", createDate(20, 3, 2010));
        p.put("empty", false);


        EasyMock.expect(projectMgrMock.getProject(20)).andReturn((Map) p);
        EasyMock.replay(projectMgrMock);
        String got = ModifyProjectController.getProjectData(20, new ModelMap());
        Assert.assertNull(got);
        EasyMock.reset(projectMgrMock);

        boolean exceptionThrown = false;
        EasyMock.expect(projectMgrMock.getProject(-5)).andReturn((Map) p);
        EasyMock.replay(projectMgrMock);
        try {
            got = ModifyProjectController.getProjectData(-5, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


    }

    /**
     * Test of updateProject method, of class ModifyProjectController.
     */
    @Test
    public void testUpdateProject() {
        LOGGER.info("checkTestUpdateProject");
        boolean exceptionThrown = false;

        Capture<String> nameMock = new Capture<String>();
        Capture<String> descMock = new Capture<String>();
        Capture<Date> dueDateMock = new Capture<Date>();
        Capture<Date> plannedDateMock = new Capture<Date>();
        Capture<Integer> idMock = new Capture<Integer>();

        projectMgrMock.updateProject(
                EasyMock.capture(idMock),
                EasyMock.capture(nameMock),
                EasyMock.capture(descMock),
                EasyMock.capture(dueDateMock),
                EasyMock.capture(plannedDateMock));
        EasyMock.expectLastCall();
        EasyMock.replay(projectMgrMock);

        String got = ModifyProjectController.updateProject("Proj1", "Desc1",
                createDate(3, 5, 2010), createDate(6, 9, 2010), 30, new ModelMap());
        Assert.assertEquals(got, "redirect:listProjects.do");
        Assert.assertEquals("Proj1", nameMock.getValue());
        Assert.assertEquals("Desc1", descMock.getValue());
        Assert.assertEquals(createDate(3, 5, 2010), dueDateMock.getValue());
        Assert.assertEquals(createDate(6, 9, 2010), plannedDateMock.getValue());
        Assert.assertTrue(30 == idMock.getValue());
        EasyMock.reset(projectMgrMock);


        projectMgrMock.updateProject(30, "", "Desc1", createDate(3, 5, 2010), createDate(6, 9, 2010));
        EasyMock.expectLastCall();
        EasyMock.replay(projectMgrMock);
        try {
            got = ModifyProjectController.updateProject("", "Desc1",
                    createDate(3, 5, 2010), createDate(6, 9, 2010), 30, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        exceptionThrown = false;
        projectMgrMock.updateProject(30, "Proj1", "", createDate(3, 5, 2010), createDate(6, 9, 2010));
        EasyMock.expectLastCall();
        EasyMock.replay(projectMgrMock);
        try {
            got = ModifyProjectController.updateProject("Proj1", "",
                    createDate(3, 5, 2010), createDate(6, 9, 2010), 30, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        
        exceptionThrown = false;
        projectMgrMock.updateProject(-1, "Proj1", "Desc1", createDate(3, 5, 2010), createDate(6, 9, 2010));
        EasyMock.expectLastCall();
        EasyMock.replay(projectMgrMock);
        try {
            got = ModifyProjectController.updateProject("Proj1", "Desc1",
                    createDate(3, 5, 2010), createDate(6, 9, 2010), -1, new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

    }
}
