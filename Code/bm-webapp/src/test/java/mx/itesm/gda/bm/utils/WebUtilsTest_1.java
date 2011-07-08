/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: WebUtilsTest.java 214 2010-10-13 09:33:12Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-13 04:33:12 -0500 (Wed, 13 Oct 2010) $
 * Last Version      : $Revision: 214 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 214 $
 */
public class WebUtilsTest_1 {

    private static final Log LOGGER = LogFactory.getLog(WebUtilsTest.class);

    private HttpServletRequest hsrHttp80;

    private HttpServletRequest hsrHttps443;

    private HttpServletRequest hsrHttp8080;

    private HttpServletRequest hsrHttps8080;

    private HttpServletRequest hsrFtp21;

    @Before
    public void setUp() {
        hsrHttp80 = EasyMock.createMock(HttpServletRequest.class);
        EasyMock.expect(hsrHttp80.getScheme()).andReturn("http");
        EasyMock.expect(hsrHttp80.getServerName()).andReturn("www.bipolar.mx");
        EasyMock.expect(hsrHttp80.getServerPort()).andReturn(80);
        EasyMock.expect(hsrHttp80.getContextPath()).andReturn("/bm");
        EasyMock.replay(hsrHttp80);

        hsrHttps443 = EasyMock.createMock(HttpServletRequest.class);
        EasyMock.expect(hsrHttps443.getScheme()).andReturn("https");
        EasyMock.expect(hsrHttps443.getServerName()).andReturn("www.bipolar.mx");
        EasyMock.expect(hsrHttps443.getServerPort()).andReturn(443);
        EasyMock.expect(hsrHttps443.getContextPath()).andReturn("/bm");
        EasyMock.replay(hsrHttps443);

        hsrHttp8080 = EasyMock.createMock(HttpServletRequest.class);
        EasyMock.expect(hsrHttp8080.getScheme()).andReturn("http");
        EasyMock.expect(hsrHttp8080.getServerName()).andReturn("www.bipolar.mx");
        EasyMock.expect(hsrHttp8080.getServerPort()).andReturn(8080);
        EasyMock.expect(hsrHttp8080.getContextPath()).andReturn("/bm");
        EasyMock.replay(hsrHttp8080);

        hsrHttps8080 = EasyMock.createMock(HttpServletRequest.class);
        EasyMock.expect(hsrHttps8080.getScheme()).andReturn("https");
        EasyMock.expect(hsrHttps8080.getServerName()).andReturn("www.bipolar.mx");
        EasyMock.expect(hsrHttps8080.getServerPort()).andReturn(8080);
        EasyMock.expect(hsrHttps8080.getContextPath()).andReturn("/bm");
        EasyMock.replay(hsrHttps8080);

        hsrFtp21 = EasyMock.createMock(HttpServletRequest.class);
        EasyMock.expect(hsrFtp21.getScheme()).andReturn("ftp");
        EasyMock.expect(hsrFtp21.getServerName()).andReturn("www.bipolar.mx");
        EasyMock.expect(hsrFtp21.getServerPort()).andReturn(21);
        EasyMock.expect(hsrFtp21.getContextPath()).andReturn("/bm");
        EasyMock.replay(hsrFtp21);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testConstructor() throws Throwable {
        Constructor<WebUtils> constructor =
                WebUtils.class.getDeclaredConstructor();
        if(!constructor.isAccessible()) {
            constructor.setAccessible(true);
        }
        try {
            constructor.newInstance();
        } catch(InvocationTargetException ite) {
            throw ite.getCause();
        }
    }

    /**
     * Test of getWebAppUrl method, of class WebUtils.
     */
    @Test
    public void testGetWebAppUrl() {
        LOGGER.info("getWebAppUrl");

        String result = WebUtils.getWebAppUrl(hsrHttp80);
        Assert.assertEquals("http://www.bipolar.mx/bm", result);

        result = WebUtils.getWebAppUrl(hsrHttps443);
        Assert.assertEquals("https://www.bipolar.mx/bm", result);

        result = WebUtils.getWebAppUrl(hsrHttp8080);
        Assert.assertEquals("http://www.bipolar.mx:8080/bm", result);

        result = WebUtils.getWebAppUrl(hsrHttps8080);
        Assert.assertEquals("https://www.bipolar.mx:8080/bm", result);

        result = WebUtils.getWebAppUrl(hsrFtp21);
        Assert.assertEquals("ftp://www.bipolar.mx:21/bm", result);
    }

    /**
     * Test of generatePassword method, of class WebUtils.
     */
    @Test
    public void testGeneratePassword() {
        LOGGER.info("generatePassword");
        Pattern check = Pattern.compile("[A-Za-z0-9]{7}");
        Random random = new SecureRandom();
        for(int i = 0; i < 10000; i++) {
            String result = WebUtils.generatePassword(random);
            Assert.assertTrue(check.matcher(result).matches());
        }
    }

    /**
     * Test of checkEmail method, of class WebUtils.
     */
    @Test
    public void testCheckEmail() {
        LOGGER.info("checkEmail");
        Assert.assertTrue(WebUtils.checkEmail("natalia@bipolar.mx"));
        Assert.assertTrue(WebUtils.checkEmail("gisela@bipolar.com.mx"));
        Assert.assertTrue(WebUtils.checkEmail("marco@bipolar.com"));
        Assert.assertTrue(WebUtils.checkEmail("alex@bipolar.cc"));
        Assert.assertTrue(WebUtils.checkEmail("arturo@bi-polar.us"));

        Assert.assertFalse(WebUtils.checkEmail("arturo@-bipolar.us"));
        Assert.assertFalse(WebUtils.checkEmail("arturo@bipolar"));
        Assert.assertFalse(WebUtils.checkEmail("arturo@bipolar-.us"));
        Assert.assertFalse(WebUtils.checkEmail("arturo@.bipolar.us"));
        Assert.assertFalse(WebUtils.checkEmail("arturo@bipolar.us."));
    }

}
