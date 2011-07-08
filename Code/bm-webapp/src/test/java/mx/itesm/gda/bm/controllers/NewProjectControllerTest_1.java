/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewProjectControllerTest.java 319 2010-11-12 13:30:55Z inzunzo $
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
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import java.util.Date;
import java.util.GregorianCalendar;
import junit.framework.Assert;
import mx.itesm.gda.bm.session.UserLoginSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
public class NewProjectControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            NewProjectControllerTest.class);
    @Autowired
    private ProjectManagementBizOp projectMgrMock;
    @Autowired
    private NewProjectController NewProjectController;
    @Autowired
    private UserLoginSession loginSession;

    private static Date createDate(int day, int month, int year) {
        Calendar that_day = new GregorianCalendar(year, month - 1, day);
        return that_day.getTime();
    }

    /**
     * Test of displayForm method, of class NewProjectController.
     */
    @Test
    public void testDisplayForm() {
        LOGGER.info("checkProjectDisplayForm");
        String got = NewProjectController.displayForm(new ModelMap());
        Assert.assertNull(got);
    }

    /**
     * Test of newProject method, of class NewProjectController.
     */
    @Test
    public void testNewProject() {
        LOGGER.info("checkNewProject");
        boolean exceptionThrown = false;

        EasyMock.expect(projectMgrMock.createProject("Proj1", "Desc1",
                createDate(3, 5, 2010), createDate(6, 9, 2010))).andReturn(30);
        EasyMock.replay(projectMgrMock);

        String got = NewProjectController.newProject("Proj1", "Desc1",
                createDate(3, 5, 2010), createDate(6, 9, 2010), new ModelMap());
        Assert.assertEquals(got, "redirect:listProjects.do");
        EasyMock.reset(projectMgrMock);



        EasyMock.expect(projectMgrMock.createProject("Proj1", "Desc1",
                createDate(3, 5, 2010), createDate(6, 9, 2010))).andReturn(30);
        EasyMock.replay(projectMgrMock);
        try {
            got = NewProjectController.newProject("", "Desc1",
                    createDate(3, 5, 2010), createDate(6, 9, 2010), new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        EasyMock.expect(projectMgrMock.createProject(null, "Desc1",
                createDate(3, 5, 2010), createDate(6, 9, 2010))).andReturn(30);
        EasyMock.replay(projectMgrMock);
        exceptionThrown = false;
        try {
            got = NewProjectController.newProject(null, "Desc1",
                    createDate(3, 5, 2010), createDate(6, 9, 2010), new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


        EasyMock.expect(projectMgrMock.createProject("Proj1", "Desc1",
                createDate(3, 5, 2010), createDate(6, 9, 2010))).andReturn(30);
        EasyMock.replay(projectMgrMock);
        exceptionThrown = false;
        try {
            got = NewProjectController.newProject("Proj1", "",
                    createDate(3, 5, 2010), createDate(6, 9, 2010), new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);

        EasyMock.expect(projectMgrMock.createProject("Proj1", null,
                createDate(3, 5, 2010), createDate(6, 9, 2010))).andReturn(30);
        EasyMock.replay(projectMgrMock);
        exceptionThrown = false;
        try {
            got = NewProjectController.newProject("Proj1", null,
                    createDate(3, 5, 2010), createDate(6, 9, 2010), new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(projectMgrMock);
        }
        Assert.assertTrue(exceptionThrown);


    }
}
