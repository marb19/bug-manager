/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: AbstractItem.java 152 2010-10-04 16:49:06Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-04 11:49:06 -0500 (Mon, 04 Oct 2010) $
 * Last Version      : $Revision: 152 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

import mx.itesm.gda.bm.model.utils.ModelUtils;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 152 $
 */
public abstract class AbstractItem implements BaseItem {

    @Override
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    public boolean equals(Object that) {
        return ModelUtils.equals(this, that);
    }

    @Override
    public int hashCode() {
        return ModelUtils.hashCode(this);
    }

}
