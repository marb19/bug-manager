/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: WebUtils.java 197 2010-10-09 20:07:05Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 15:07:05 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 197 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.utils;

import java.math.BigInteger;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 197 $
 */
public class WebUtils {

    private static final Map<String, Integer> DEFAULT_SCHEME_PORT;

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "[-&'*+.0-9=?A-Z^_a-z{}~]+@"
            + "[0-9A-Za-z](-?[0-9A-Za-z])*\\.[0-9A-Za-z]([-.]?[0-9A-Za-z])*");

    static {
        Map<String, Integer> m = new HashMap<String, Integer>();
        m.put("http", 80);
        m.put("https", 443);
        DEFAULT_SCHEME_PORT = Collections.unmodifiableMap(m);
    }

    private WebUtils() {
        throw new UnsupportedOperationException("No way");
    }

    public static String getWebAppUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String host = request.getServerName();
        int port = request.getServerPort();
        String ctx_path = request.getContextPath();

        StringBuilder b = new StringBuilder(scheme);
        b.append("://").append(host);
        if(!DEFAULT_SCHEME_PORT.containsKey(scheme)
                || DEFAULT_SCHEME_PORT.get(scheme) != port) {
            b.append(":").append(port);
        }
        b.append(ctx_path);
        return b.toString();
    }

    public static String generatePassword(Random r) {
        BigInteger p = new BigInteger(40, r);
        StringBuilder s = new StringBuilder(7);
        BigInteger fifty_two = BigInteger.valueOf(52);
        for(int i = 0; i < 7; i++) {
            BigInteger[] d = p.divideAndRemainder(fifty_two);
            p = d[0];
            int m = d[1].intValue();
            char c = (char)('A' + m + ((m >= 26) ? ('a' - ('Z' + 1)) : 0));
            s.append(c);
        }
        return s.toString();
    }

    public static boolean checkEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

}
