/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DeleteUserControllerTest.java 318 2010-11-12 04:43:33Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-11 22:43:33 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 318 $
 *
 * Original Author   : Arturo Inzunza
 * Notes             :
 * ************************************************************************ */
package mx.itesm.gda.bm.controllers;

import junit.framework.Assert;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.Capture;
import org.easymock.EasyMock;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.ui.ModelMap;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 318 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"/testControllerContext.xml"})
public class DeleteUserControllerTest {

    private static final Log LOGGER = LogFactory.getLog(
            DeleteUserControllerTest.class);
    @Autowired
    private UserManagementBizOp userMgrMock;
    @Autowired
    private DeleteUserController DeleteUserController;

    /**
     * Test of deleteUser method, of class DeleteUserController.
     */
    @Test
    public void testDeleteUser() {
        LOGGER.info("checkTestDeleteUser");
        boolean exceptionThrown = false;
        userMgrMock.deleteUser("");
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);
        try {
            DeleteUserController.deleteUser("", new ModelMap());
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(userMgrMock);
        }

        Assert.assertTrue(exceptionThrown);


        Capture<String> userNameMock = new Capture<String>();
        userMgrMock.deleteUser(EasyMock.capture(userNameMock));
        EasyMock.expectLastCall();
        EasyMock.replay(userMgrMock);

        String got = DeleteUserController.deleteUser("uName", new ModelMap());
        Assert.assertEquals("redirect:listUsers.do", got);
        Assert.assertEquals("uName", userNameMock.getValue());
        EasyMock.reset(userMgrMock);
    }
}
