/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ProjectDAOImpl.java 282 2010-10-31 03:07:22Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 22:07:22 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 282 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.Calendar;
import java.util.List;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 282 $
 */
@Scope("prototype")
@Repository
public class ProjectDAOImpl extends BaseItemDAOImpl<Project> implements
        ProjectDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Project> getAll() {
        @SuppressWarnings("unchecked")
        List<Project> result = getEntityManager().createQuery(
                "SELECT p FROM Project p").getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Project findById(int project_id) {
        return getEntityManager().find(Project.class, project_id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Project> searchByProjectName(String project_name) {
        @SuppressWarnings("unchecked")
        List<Project> result = getEntityManager().createQuery(
                "SELECT p FROM Project p "
                + "WHERE p.projectName LIKE :project_name").
                setParameter("project_name", "%" + project_name + "%").
                getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Project> searchByUserNameEnrolled(String user_name) {
        Calendar day_before_yesterday = Calendar.getInstance();
        day_before_yesterday.add(Calendar.DAY_OF_MONTH, -2);
        @SuppressWarnings("unchecked")
        List<Project> result = getEntityManager().createQuery(
                "SELECT DISTINCT p FROM Project p JOIN p.tasks t "
                + "WHERE t.assignedUser.userName = :uname "
                + "AND (t.taskState <> :status "
                + "OR t.completionDate >= :thatday)").
                setParameter("uname", user_name).
                setParameter("status", TaskState.COMPLETED).
                setParameter("thatday", day_before_yesterday.getTime()).
                getResultList();
        return result;
    }

}
