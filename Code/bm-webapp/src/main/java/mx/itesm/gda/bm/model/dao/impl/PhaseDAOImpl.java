/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: PhaseDAOImpl.java 193 2010-10-09 07:39:00Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 02:39:00 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.model.dao.PhaseDAO;
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
public class PhaseDAOImpl extends BaseItemDAOImpl<Phase> implements PhaseDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Phase> getAll() {
        @SuppressWarnings("unchecked")
        List<Phase> phases = (List<Phase>)getEntityManager().createQuery(
                "SELECT p FROM Phase p").getResultList();
        return phases;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Phase findById(int phase_id) {
        return getEntityManager().find(Phase.class, phase_id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Phase> searchByPhaseName(String phase_name) {
        @SuppressWarnings("unchecked")
        List<Phase> phases = (List<Phase>)getEntityManager().createQuery(
                "SELECT p FROM Phase p WHERE p.phaseName LIKE :name").
                setParameter("name", "%" + phase_name + "%").getResultList();
        return phases;
    }
}
