/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ViewTaskControllerTest.java 343 2010-11-18 00:26:31Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-17 18:26:31 -0600 (Mié, 17 Nov 2010) $
 * Last Version      : $Revision: 343 $
 *
 * Original Author   : Marco Rangel
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import junit.framework.Assert;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 343 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = { "/testControllerContext.xml" })
public class ReportControllerTest_1 {

    @Autowired
    private ReportController reportController;

    private static final Log LOGGER = LogFactory.getLog(
            ViewTaskControllerTest.class);

    @Test
    public void testBuildReport() {
        LOGGER.info("testBuildReport");

        //Caso de earnedValue
        ModelAndView mv = reportController.buildReport("earnedValue", "pdf", 1, "u");
        Assert.assertEquals(mv.getViewName(), "EV");
        Assert.assertEquals(mv.getModel().get("format"), "pdf");
        Assert.assertEquals(mv.getModel().get("project_id"), 1);
        Assert.assertEquals(mv.getModel().get("username"), "u");

        //Caso de tasksByProject
        mv = reportController.buildReport("tasksByProject", "pdf", 1, "u");
        Assert.assertEquals(mv.getViewName(), "avanceProyecto");
        Assert.assertEquals(mv.getModel().get("format"), "pdf");
        Assert.assertEquals(mv.getModel().get("project_id"), 1);
        Assert.assertEquals(mv.getModel().get("username"), "u");

        //Caso de tasksByUser
        mv = reportController.buildReport("tasksByUser", "pdf", 1, "u");
        Assert.assertEquals(mv.getViewName(), "avancePersona");
        Assert.assertEquals(mv.getModel().get("format"), "pdf");
        Assert.assertEquals(mv.getModel().get("project_id"), 1);
        Assert.assertEquals(mv.getModel().get("username"), "u");

    }

}
