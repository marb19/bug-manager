/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: AccessDeniedExceptionTest.java 207 2010-10-12 03:34:31Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-11 22:34:31 -0500 (Mon, 11 Oct 2010) $
 * Last Version      : $Revision: 207 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 207 $
 */
public class AccessDeniedExceptionTest_1 {

    private static final Log LOGGER = LogFactory.getLog(
            AccessDeniedExceptionTest.class);

    @Test
    public void testEmptyConstructor() {
        AccessDeniedException be = new AccessDeniedException();
        Assert.assertNull(be.getMessage());
        Assert.assertNull(be.getCause());
    }

    @Test
    public void testMsgConstructor() {
        AccessDeniedException be = new AccessDeniedException("my message");
        Assert.assertEquals("my message", be.getMessage());
        Assert.assertNull(be.getCause());
    }

    @Test
    public void testCauseConstructor() {
        Exception cause = new Exception("my message");
        AccessDeniedException be = new AccessDeniedException(cause);
        Assert.assertTrue(be.getMessage().contains("my message"));
        Assert.assertSame(cause, be.getCause());
    }

    @Test
    public void testMsgCauseConstructor() {
        Exception cause = new Exception();
        AccessDeniedException be =
                new AccessDeniedException("my message", cause);
        Assert.assertEquals("my message", be.getMessage());
        Assert.assertSame(cause, be.getCause());
    }

}
