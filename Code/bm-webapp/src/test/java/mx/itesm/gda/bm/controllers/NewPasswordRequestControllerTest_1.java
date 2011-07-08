/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewPasswordRequestControllerTest.java 269 2010-10-23 19:59:04Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-23 14:59:04 -0500 (Sat, 23 Oct 2010) $
 * Last Version      : $Revision: 269 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import mx.itesm.gda.bm.biz.UserAccessBizOp;
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
public class NewPasswordRequestControllerTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            NewPasswordRequestControllerTest.class);

    @Autowired
    private UserAccessBizOp userAccessBizOpMock;

    @Autowired
    private NewPasswordRequestController newPRController;

    /**
     * Test of requestNewPassword method, of class NewPasswordRequestController.
     */
    @Test
    public void testRequestNewPassword() {
        LOGGER.info("requestNewPassword");
        newPRController.requestNewPassword();
    }

    /**
     * Test of doRequestNewPassword method, of class NewPasswordRequestController.
     */
    @Test
    public void testDoRequestNewPassword() {
        LOGGER.info("doRequestNewPassword");

        EasyMock.expect(userAccessBizOpMock.initiatePasswordRecovery(
                "some_email@bipolar.mx")).andReturn(Boolean.TRUE);
        EasyMock.replay(userAccessBizOpMock);
        ModelMap model = new ModelMap();

        String ret = newPRController.doRequestNewPassword(
                "some_email@bipolar.mx", model);

        Assert.assertEquals(ret, "redirect:newPasswordRequested.do");

        EasyMock.reset(userAccessBizOpMock);
    }

    /**
     * Test of newPasswordRequested method, of class NewPasswordRequestController.
     */
    @Test
    public void testNewPasswordRequested() {
        LOGGER.info("newPasswordRequested");
        newPRController.newPasswordRequested();
    }

    /**
     * Test of newPassword method, of class NewPasswordRequestController.
     */
    @Test
    public void testNewPassword() {
        LOGGER.info("newPassword");

        EasyMock.expect(userAccessBizOpMock.validatePasswordRecoveryTicket(
                "my_recovery_ticket_123456")).andReturn(Boolean.TRUE);
        EasyMock.replay(userAccessBizOpMock);
        ModelMap model = new ModelMap();

        String ret = newPRController.newPassword("my_recovery_ticket_123456",
                model);

        Assert.assertNull(ret);

        EasyMock.reset(userAccessBizOpMock);
        EasyMock.expect(userAccessBizOpMock.validatePasswordRecoveryTicket(
                "my_recovery_ticket_123456")).andReturn(Boolean.FALSE);
        EasyMock.replay(userAccessBizOpMock);
        model = new ModelMap();

        ret = newPRController.newPassword("my_recovery_ticket_123456", model);

        Assert.assertEquals(ret, "redirect:ticketNotFound.do");

        EasyMock.reset(userAccessBizOpMock);
    }

    /**
     * Test of ticketNotFound method, of class NewPasswordRequestController.
     */
    @Test
    public void testTicketNotFound() {
        LOGGER.info("ticketNotFound");
        newPRController.ticketNotFound();
    }

}
