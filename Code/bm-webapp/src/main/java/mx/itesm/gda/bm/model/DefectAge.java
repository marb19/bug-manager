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
public enum DefectAge {

    BASE(true, false, false,false),
    NEWDEFECT(false, true, false, false),
    REWRITTEN(false, false, true, false),
    REFIXED(false, false, false, true);

    private final boolean base;
    private final boolean newdefect;
    private final boolean rewritten;
    private final boolean refixed;

    DefectAge(boolean my_base, boolean my_newdefect, boolean my_rewritten, boolean my_refixed) {
        base = my_base;
        newdefect = my_newdefect;
        rewritten = my_rewritten;
        refixed = my_refixed;
    }

    /**
     * @return the started
     */
    public boolean isBase() {
        return base;
    }

    /**
     * @return the completed
     */
    public boolean isNewDefect() {
        return newdefect;
    }

    /**
     * @return the canceled
     */
    public boolean isRewritten() {
        return rewritten;
    }
    
    public boolean isRefixed() {
        return refixed;
    }

}
