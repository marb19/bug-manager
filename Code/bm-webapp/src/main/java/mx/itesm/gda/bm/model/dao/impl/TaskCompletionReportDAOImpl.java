/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskCompletionReportDAOImpl.java 133 2010-09-28 04:29:01Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-09-27 23:29:01 -0500 (Mon, 27 Sep 2010) $
 * Last Version      : $Revision: 133 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.TaskCompletionReport;
import mx.itesm.gda.bm.model.dao.TaskCompletionReportDAO;
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
public class TaskCompletionReportDAOImpl
        extends BaseItemDAOImpl<TaskCompletionReport>
        implements TaskCompletionReportDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<TaskCompletionReport> getAll() {
        @SuppressWarnings("unchecked")
        List<TaskCompletionReport> result = getEntityManager().createQuery(
                "SELECT t FROM TaskCompletionReport t").getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public TaskCompletionReport findById(int tcrpt_id) {
        return getEntityManager().find(TaskCompletionReport.class, tcrpt_id);
    }

}
