/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectTypeDAOImpl.java 193 2010-10-09 07:39:00Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 02:39:00 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.DefectType;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 193 $
 */
@Scope("prototype")
@Repository
public class DefectTypeDAOImpl extends BaseItemDAOImpl<DefectType> implements DefectTypeDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<DefectType> getAll() {
        @SuppressWarnings("unchecked")
        List<DefectType> defectTypes = (List<DefectType>)getEntityManager().createQuery(
                "SELECT d FROM DefectType d").getResultList();
        return defectTypes;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public DefectType findById(int defectType_id) {
        return getEntityManager().find(DefectType.class, defectType_id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<DefectType> searchByDefectTypeName(String defectType_name) {
        @SuppressWarnings("unchecked")
        List<DefectType> defectTypes = (List<DefectType>)getEntityManager().createQuery(
                "SELECT d FROM DefectType d WHERE d.taskName LIKE :name").
                setParameter("name", "%" + defectType_name + "%").getResultList();
        return defectTypes;
    }

}
