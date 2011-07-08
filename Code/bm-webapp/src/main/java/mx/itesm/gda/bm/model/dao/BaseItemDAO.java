/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: BaseItemDAO.java 152 2010-10-04 16:49:06Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-04 11:49:06 -0500 (Mon, 04 Oct 2010) $
 * Last Version      : $Revision: 152 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao;

import java.util.List;
import mx.itesm.gda.bm.model.BaseItem;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 152 $
 */
public interface BaseItemDAO<T extends BaseItem> {

    public List<T> getAll();

    public void save(T item);

    public void update(T item);

    public void delete(T item);

    public void lock(T item);

    public void refresh(T item);

}
