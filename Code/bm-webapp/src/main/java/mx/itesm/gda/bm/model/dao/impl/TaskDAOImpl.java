/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskDAOImpl.java 193 2010-10-09 07:39:00Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 02:39:00 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 193 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.dao.TaskDAO;
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
public class TaskDAOImpl extends BaseItemDAOImpl<Task> implements TaskDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getAll() {
        @SuppressWarnings("unchecked")
        List<Task> tasks = (List<Task>)getEntityManager().createQuery(
                "SELECT t FROM Task t").getResultList();
        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Task findById(int task_id) {
        return getEntityManager().find(Task.class, task_id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> searchByTaskName(String task_name) {
        @SuppressWarnings("unchecked")
        List<Task> tasks = (List<Task>)getEntityManager().createQuery(
                "SELECT t FROM Task t WHERE t.taskName LIKE :name").
                setParameter("name", "%" + task_name + "%").getResultList();
        return tasks;
    }

}
