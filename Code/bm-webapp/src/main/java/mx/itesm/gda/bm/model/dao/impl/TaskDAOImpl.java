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
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.PhaseType;
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

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getTasksByType(TaskType type){
        @SuppressWarnings("unchecked")        

        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "WHERE t.taskType = :type")
                .setParameter("type", type)
                .getResultList();
        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getTasksByState(TaskState state){
        @SuppressWarnings("unchecked")        

        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "WHERE t.taskState = :state")
                .setParameter("state", state)
                .getResultList();

        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getTasksByTypeAndState(TaskType type, TaskState state){
        @SuppressWarnings("unchecked")
                
        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "WHERE t.taskType = :type AND t.taskState = :state")
                .setParameter("type", type)
                .setParameter("state", state)
                .getResultList();
                      
        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getTasksByTypeStateProject(TaskType type, TaskState state, int project_id){
        @SuppressWarnings("unchecked")

        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "JOIN t.project p WHERE t.taskType = :type AND t.taskState = :state "
                + "AND p.projectId = :project_id")
                .setParameter("type", type)
                .setParameter("state", state)
                .setParameter("project_id", project_id)
                .getResultList();

        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getTasksByTypeStateUser(TaskType type, TaskState state, String username){
        @SuppressWarnings("unchecked")

        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "JOIN t.assignedUser u WHERE t.taskType = :type AND t.taskState = :state "
                + "AND u.userName = :username")
                .setParameter("type", type)
                .setParameter("state", state)
                .setParameter("username", username)
                .getResultList();

        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getTasksByStatePhaseType(TaskState state, PhaseType type){
        @SuppressWarnings("unchecked")

        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "JOIN t.phase p WHERE p.phaseType = :type AND t.taskState = :state")
                .setParameter("type", type)
                .setParameter("state", state)
                .getResultList();

        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getQualityTasksByStatePhaseType(TaskState state, PhaseType type){
        @SuppressWarnings("unchecked")

        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "JOIN t.phase p WHERE p.phaseType = :type AND (t.taskType = :personalReview "
                + "OR t.taskType = :peerReview OR t.taskType = :walk "
                + "OR t.taskType = :inspection) AND t.taskState = :state")
                .setParameter("type", type)
                .setParameter("state", state)
                .setParameter("personalReview", TaskType.PERSONAL_REVIEW)
                .setParameter("peerReview", TaskType.PEER_REVIEW)
                .setParameter("walk", TaskType.WALKTHROUGH)
                .setParameter("inspection", TaskType.INSPECTION)
                .getResultList();

        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getTasksByStatePhaseTypeProject(TaskState state, PhaseType type, int project_id){
        @SuppressWarnings("unchecked")

        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "JOIN t.phase p JOIN t.project pr WHERE pr.projectId = :project_id AND "
                + "p.phaseType = :type AND t.taskState = :state")
                .setParameter("type", type)
                .setParameter("project_id", project_id)
                .setParameter("state", state)
                .getResultList();

        return tasks;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Task> getQualityTasksByStatePhaseTypeProject(TaskState state, PhaseType type, int project_id){
        @SuppressWarnings("unchecked")

        List<Task> tasks = (List<Task>)getEntityManager().createQuery("SELECT t FROM Task t "
                + "JOIN t.phase p JOIN t.project pr WHERE pr.projectId = :project_id AND "
                + "p.phaseType = :type AND (t.taskType = :personalReview "
                + "OR t.taskType = :peerReview OR t.taskType = :walk "
                + "OR t.taskType = :inspection) AND t.taskState = :state")
                .setParameter("type", type)
                .setParameter("project_id", project_id)
                .setParameter("state", state)
                .setParameter("personalReview", TaskType.PERSONAL_REVIEW)
                .setParameter("peerReview", TaskType.PEER_REVIEW)
                .setParameter("walk", TaskType.WALKTHROUGH)
                .setParameter("inspection", TaskType.INSPECTION)
                .getResultList();

        return tasks;
    }
}
