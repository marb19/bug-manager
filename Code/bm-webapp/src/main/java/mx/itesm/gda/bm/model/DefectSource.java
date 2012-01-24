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
public enum DefectSource {

    INHOUSE(true, false, false,false),
    LIBRARY(false, true, false, false),
    OUTSOURCED(false, false, true, false),
    PORTED(false, false, false, true);

    private final boolean inhouse;
    private final boolean library;
    private final boolean outsourced;
    private final boolean ported;

    DefectSource(boolean my_inhouse, boolean my_library, boolean my_outsourced, boolean my_ported) {
        inhouse = my_inhouse;
        library = my_library;
        outsourced = my_outsourced;
        ported = my_ported;
    }

    /**
     * @return the started
     */
    public boolean isInHouse() {
        return inhouse;
    }

    /**
     * @return the completed
     */
    public boolean isLibrary() {
        return library;
    }

    /**
     * @return the canceled
     */
    public boolean isOutSourced() {
        return outsourced;
    }
    
    public boolean isPorted() {
        return ported;
    }

}
