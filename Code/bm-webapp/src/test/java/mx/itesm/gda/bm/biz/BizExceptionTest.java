/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: BizExceptionTest.java 308 2010-11-10 00:58:54Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-09 18:58:54 -0600 (Tue, 09 Nov 2010) $
 * Last Version      : $Revision: 308 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 308 $
 */
public class BizExceptionTest {

    private static final Log LOGGER = LogFactory.getLog(BizExceptionTest.class);

    @Test
    public void testEmptyConstructor() {
        BizException be = new BizException();
        Assert.assertNull(be.getMessage());
        Assert.assertNull(be.getCause());
    }

    @Test
    public void testMsgConstructor() {
        BizException be = new BizException("my message");
        Assert.assertEquals("my message", be.getMessage());
        Assert.assertNull(be.getCause());
    }

    @Test
    public void testCauseConstructor() {
        Exception cause = new Exception("my message");
        BizException be = new BizException(cause);
        Assert.assertTrue(be.getMessage().contains("my message"));
        Assert.assertSame(cause, be.getCause());
    }

    @Test
    public void testMsgCauseConstructor() {
        Exception cause = new Exception();
        BizException be = new BizException("my message", cause);
        Assert.assertEquals("my message", be.getMessage());
        Assert.assertSame(cause, be.getCause());
    }
}