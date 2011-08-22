/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectTypeDAO.java 113 2010-09-21 05:24:13Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-09-21 00:24:13 -0500 (Tue, 21 Sep 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao;

import java.util.List;
import mx.itesm.gda.bm.model.DefectType;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 113 $
 */
public interface DefectTypeDAO extends BaseItemDAO<DefectType> {

    public DefectType findById(int defectType_id);

    public List<DefectType> searchByDefectTypeName(String defectType_name);

}
