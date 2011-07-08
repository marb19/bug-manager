/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: BaseItemDAOImpl.java 152 2010-10-04 16:49:06Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-04 11:49:06 -0500 (Mon, 04 Oct 2010) $
 * Last Version      : $Revision: 152 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import mx.itesm.gda.bm.model.BaseItem;
import mx.itesm.gda.bm.model.dao.BaseItemDAO;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 152 $
 */
public abstract class BaseItemDAOImpl<T extends BaseItem> implements
        BaseItemDAO<T> {

    protected EntityManager entityManager;

    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(T item) {
        entityManager.remove(item);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void save(T item) {
        entityManager.persist(item);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void update(T item) {
        entityManager.merge(item);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void lock(T item) {
        entityManager.lock(item, LockModeType.WRITE);
    }

    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public void refresh(T item) {
        entityManager.refresh(item);
    }

    /**
     * @return the entityManager
     */
    public EntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * @param entityManager the entityManager to set
     */
    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

}
