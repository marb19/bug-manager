/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TestUtils.java 306 2010-11-09 15:35:17Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-09 09:35:17 -0600 (Tue, 09 Nov 2010) $
 * Last Version      : $Revision: 306 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.test.utils;

import org.easymock.EasyMock;
import org.junit.Ignore;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 306 $
 */
@Ignore
public class TestUtils_1 {

    private TestUtils_1() {
        throw new UnsupportedOperationException("No way jose!!");
    }

    public static <T> T setId(Object value) {
        EasyMock.reportMatcher(new JpaIdSetter(value));
        return null;
    }

    public static final String reverse(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        for(int i = s.length() - 1; i >= 0; i--) {
            sb.append(s.charAt(i));
        }
        return sb.toString();
    }

}
