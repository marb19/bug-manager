/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectState.java 279 2010-10-30 15:43:29Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 10:43:29 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
public enum DefectState {

    SUBMITTED(false, false, false),
    ACCEPTED(true, false, false),
    FIXED(true, true, false),
    CANCELED(false, false, true);

    private final boolean accepted;

    private final boolean fixed;

    private final boolean canceled;

    DefectState(boolean my_started, boolean my_completed, boolean my_canceled) {
        accepted = my_started;
        fixed = my_completed;
        canceled = my_canceled;
    }

    /**
     * @return the started
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * @return the completed
     */
    public boolean isFixed() {
        return fixed;
    }

    /**
     * @return the canceled
     */
    public boolean isCanceled() {
        return canceled;
    }

}
