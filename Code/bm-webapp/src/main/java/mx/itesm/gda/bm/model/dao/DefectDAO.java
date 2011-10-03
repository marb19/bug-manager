/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectDAO.java 113 2010-09-21 05:24:13Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-09-21 00:24:13 -0500 (Tue, 21 Sep 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao;

import java.util.List;
import mx.itesm.gda.bm.model.Defect;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
public interface DefectDAO extends BaseItemDAO<Defect> {

    public Defect findById(int defect_id);

    public List<Defect> searchByDefectName(String defect_name);

    public List<Defect> searchByDetectionPhase(Integer phase_id);

    public List<Defect> searchByTypeAndUser(int defectType_id, String username);

    public List<Defect> searchByTypeAndProject(int defectType_id, int project_id);

    public List<Defect> searchByType(int defectType_id);

    public List<Defect> searchByUserAndInyPhase(int phase_id, String username);

    public List<Defect> searchByUserAndRemPhase(int phase_id, String username);

    public List<Defect> searchByInyectionPhase(int phase_id);

    public List<Defect> searchByRemotionPhase(int phase_id);
}
