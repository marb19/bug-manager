/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskCommentDAOImpl.java 133 2010-09-28 04:29:01Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-09-27 23:29:01 -0500 (Mon, 27 Sep 2010) $
 * Last Version      : $Revision: 133 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.TaskComment;
import mx.itesm.gda.bm.model.dao.TaskCommentDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 133 $
 */
@Scope("prototype")
@Repository
public class TaskCommentDAOImpl extends BaseItemDAOImpl<TaskComment> implements
        TaskCommentDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<TaskComment> getAll() {
        @SuppressWarnings("unchecked")
        List<TaskComment> result = getEntityManager().createQuery(
                "SELECT t FROM TaskComment t").getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public TaskComment findById(int task_comment_id) {
        return getEntityManager().find(TaskComment.class, task_comment_id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<TaskComment> searchByCommentText(String comment_text) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
