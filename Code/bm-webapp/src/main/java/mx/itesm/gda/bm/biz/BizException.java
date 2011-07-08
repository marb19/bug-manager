/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: BizException.java 152 2010-10-04 16:49:06Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-04 11:49:06 -0500 (Mon, 04 Oct 2010) $
 * Last Version      : $Revision: 152 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 152 $
 */
public class BizException extends RuntimeException {

    /**
     * Creates a new instance of <code>BizException</code> without detail message.
     */
    public BizException() {
    }

    /**
     * Constructs an instance of <code>BizException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BizException(String msg) {
        super(msg);
    }

    /**
     * Creates a new instance of <code>BizException</code> without detail message.
     */
    public BizException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an instance of <code>BizException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public BizException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
