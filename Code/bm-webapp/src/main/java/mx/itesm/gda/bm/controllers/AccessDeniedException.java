/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: AccessDeniedException.java 146 2010-10-03 04:38:39Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-02 23:38:39 -0500 (Sat, 02 Oct 2010) $
 * Last Version      : $Revision: 146 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 146 $
 */
public class AccessDeniedException extends ControllerException {

    /**
     * Creates a new instance of <code>AccessDeniedException</code> without
     * detail message.
     */
    public AccessDeniedException() {
    }

    /**
     * Constructs an instance of <code>AccessDeniedException</code> with the
     * specified detail message.
     * @param msg the detail message.
     */
    public AccessDeniedException(String msg) {
        super(msg);
    }

    /**
     * Creates a new instance of <code>AccessDeniedException</code> without
     * detail message.
     */
    public AccessDeniedException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs an instance of <code>AccessDeniedException</code> with the
     * specified detail message.
     * @param msg the detail message.
     */
    public AccessDeniedException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
