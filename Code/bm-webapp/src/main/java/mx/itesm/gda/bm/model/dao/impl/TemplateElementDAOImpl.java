/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TemplateDAOImpl.java 193 2010-10-09 07:39:00Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 02:39:00 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.TemplateElement;
import mx.itesm.gda.bm.model.dao.TemplateElementDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Repository
public class TemplateElementDAOImpl extends BaseItemDAOImpl<TemplateElement> implements TemplateElementDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<TemplateElement> getAll() {
        @SuppressWarnings("unchecked")
        List<TemplateElement> defects = (List<TemplateElement>)getEntityManager().createQuery(
                "SELECT t FROM TemplateElement t").getResultList();
        return defects;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public TemplateElement findById(int templateElementId) {
        return getEntityManager().find(TemplateElement.class, templateElementId);
    }

}
