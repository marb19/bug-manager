/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectCommentDAOImpl.java 133 2010-09-28 04:29:01Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-09-27 23:29:01 -0500 (Mon, 27 Sep 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.DefectComment;
import mx.itesm.gda.bm.model.dao.DefectCommentDAO;
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
public class DefectCommentDAOImpl extends BaseItemDAOImpl<DefectComment> implements
        DefectCommentDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<DefectComment> getAll() {
        @SuppressWarnings("unchecked")
        List<DefectComment> result = getEntityManager().createQuery(
                "SELECT d FROM DefectComment d").getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public DefectComment findById(int defect_comment_id) {
        return getEntityManager().find(DefectComment.class, defect_comment_id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<DefectComment> searchByCommentText(String comment_text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
