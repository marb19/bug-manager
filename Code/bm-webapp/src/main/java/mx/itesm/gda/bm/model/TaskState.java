/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskState.java 279 2010-10-30 15:43:29Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 10:43:29 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 279 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 279 $
 */
public enum TaskState {

    NOT_STARTED(false, false, false),
    STARTED(true, false, false),
    COMPLETED(true, true, false),
    CANCELED(false, false, true);

    private final boolean started;

    private final boolean completed;

    private final boolean canceled;

    TaskState(boolean my_started, boolean my_completed, boolean my_canceled) {
        started = my_started;
        completed = my_completed;
        canceled = my_canceled;
    }

    /**
     * @return the started
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * @return the completed
     */
    public boolean isCompleted() {
        return completed;
    }

    /**
     * @return the canceled
     */
    public boolean isCanceled() {
        return canceled;
    }

}
