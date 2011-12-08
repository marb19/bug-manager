/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskManagementBizOpImpl.java 343 2010-11-18 00:26:31Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-17 18:26:31 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 343 $
 *
 * Original Author   : Marco Antonio Rangel Bocardo
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.BizException;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskComment;
import mx.itesm.gda.bm.model.TaskCompletionReport;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.dao.TaskCommentDAO;
import mx.itesm.gda.bm.model.dao.TaskCompletionReportDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 343 $
 */
@Scope("prototype")
@Component
public class TaskManagementBizOpImpl extends AbstractBizOp implements
        TaskManagementBizOp {

    @Autowired
    TaskDAO taskDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TaskCommentDAO taskCommentDAO;

    @Autowired
    private TaskCompletionReportDAO taskCompletionReportDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> retrieveTasks(int projectID) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Project project = projectDAO.findById(projectID);

        for(Task task : project.getTasks()) {
            Map<String, Object> t = new HashMap<String, Object>();
            t.put("taskID", task.getTaskId());
            t.put("taskName", task.getTaskName());
            t.put("project", mapProject(task.getProject()));
            t.put("assignedUser", mapUser(task.getAssignedUser()));
            t.put("startDate", task.getStartDate());
            t.put("endDate", task.getEndDate());
            t.put("investedHours", task.getInvestedHours());
            t.put("estimatedHours", task.getEstimatedHours());
            t.put("remainingHours", task.getRemainingHours());
            t.put("completionDate", task.getCompletionDate());
            t.put("status", task.getTaskState().name());
            t.put("commentsNumber", task.getTaskComments().size());
            ret.add(t);
        }

        return ret;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> retrieveComments(int taskID) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Task task = taskDAO.findById(taskID);

        for(TaskComment comment : task.getTaskComments()) {
            Map<String, Object> c = new HashMap<String, Object>();
            c.put("author", comment.getAuthor().getFullName());
            c.put("date", comment.getCommentDate());
            c.put("text", comment.getCommentText());
            ret.add(c);
        }

        return ret;
    }


    private Map<String, ?> mapUser(User user) {
        Map<String, Object> u = new HashMap<String, Object>();
        u.put("userName", user.getUserName());
        u.put("fullName", user.getFullName());
        u.put("email", user.getEmail());
        return u;
    }

    private Map<String, ?> mapProject(Project project) {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("projectId", project.getProjectId());
        p.put("projectName", project.getProjectName());
        p.put("projectDescription", project.getProjectDescription());
        p.put("projectDueDate", project.getProjectDueDate());
        p.put("projectPlannedDate", project.getProjectPlannedDate());
        p.put("empty", project.getTasks().isEmpty());
        return p;
    }

    @Override
    public Map<String, ?> getTask(Integer taskID) {

        Task task = taskDAO.findById(taskID);
        Map<String, Object> t = new HashMap<String, Object>();

        t.put("projectID", task.getProject().getProjectId());
        t.put("taskName", task.getTaskName());
        t.put("description", task.getTaskDescription());
        t.put("assignedUser", mapUser(task.getAssignedUser()));
        t.put("startDate", task.getStartDate());
        t.put("endDate", task.getEndDate());
        t.put("estimatedHours", task.getEstimatedHours());
        t.put("investedHours", task.getInvestedHours());
        t.put("remainingHours", task.getRemainingHours());
        t.put("status", task.getTaskState().name());

        return t;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public int createTask(String taskName, int project, String description,
            String assignedUser, int estimatedHours, Date startDate, Date endDate) {

        User u = userDAO.findByUserName(assignedUser);
        Project p = projectDAO.findById(project);
        
        Task t = new Task();
        t.setTaskName(taskName);
        t.setProject(p);
        t.setTaskDescription(description);
        t.setAssignedUser(u);
        t.setEstimatedHours(estimatedHours);
        t.setRemainingHours(estimatedHours);
        t.setStartDate(startDate);
        t.setEndDate(endDate);
        t.setTaskState(TaskState.NOT_STARTED);
        taskDAO.save(t);
        return t.getTaskId();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public void deleteTask(Integer taskID) {
        Task t = taskDAO.findById(taskID);

        if (t == null){
            throw new BizException("Cannot delete non-existing task");
        }

        if (t.getTaskState() != TaskState.NOT_STARTED || !t.getTaskComments().isEmpty()){
            throw new BizException("Cannot delete non-empty task");
        }

        taskDAO.delete(t);
    }

    @Override
    public int modifyTask(int taskID, String taskName, String description, String assignedUser, String status, int estimatedHours, int investedHours, int remainingHours, Date startDate, Date endDate) {
        Task t = taskDAO.findById(taskID);
        User u = userDAO.findByUserName(assignedUser);

        t.setTaskName(taskName);
        t.setTaskDescription(description);
        t.setAssignedUser(u);
        t.setTaskState(TaskState.valueOf(status));
        t.setEstimatedHours(estimatedHours);
        t.setRemainingHours(remainingHours);
        
        if(TaskState.valueOf(status) != TaskState.COMPLETED && TaskState.valueOf(status) != TaskState.CANCELED){
            t.setInvestedHours(t.getInvestedHours() + investedHours);
        }

        if(TaskState.valueOf(status) == TaskState.NOT_STARTED && investedHours > 0){
            t.setTaskState(TaskState.STARTED);
        }

        if(remainingHours == 0 && TaskState.valueOf(status) != TaskState.CANCELED){
            t.setTaskState(TaskState.COMPLETED);
        }

        if(TaskState.valueOf(status) == TaskState.COMPLETED && t.getCompletionDate() == null){
            t.setCompletionDate(new Date());
        }

        t.setStartDate(startDate);
        t.setEndDate(endDate);

        TaskCompletionReport tcr = new TaskCompletionReport();
        tcr.setTask(t);
        tcr.setInvestedHoursToDate(investedHours);
        tcr.setRemainingHoursToDate(remainingHours);
        tcr.setReportDate(new Date());
        tcr.setReportingUser(u);

        taskCompletionReportDAO.save(tcr);

        return t.getTaskId();
    }

    @Override
    public int addComment(int taskID, String commentAuthor, String newComment){
        Task t = taskDAO.findById(taskID);
        TaskComment newTaskComment = new TaskComment();
        User author = userDAO.findByUserName(commentAuthor);
        newTaskComment.setAuthor(author);
        newTaskComment.setCommentDate(new Date());
        newTaskComment.setCommentText(newComment);
        newTaskComment.setTask(t);
        taskCommentDAO.save(newTaskComment);
        return newTaskComment.getTaskCommentId();
    }

    @Override
    public List<Map<String, ?>> retrieveTasks(int projectID, int phaseID) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Project project = projectDAO.findById(projectID);

        for(Task task : project.getTasks()) {
            if(task.getPhase().getPhaseId() == phaseID){
                Map<String, Object> t = new HashMap<String, Object>();
                t.put("taskID", task.getTaskId());
                t.put("taskName", task.getTaskName());
                t.put("project", mapProject(task.getProject()));
                t.put("assignedUser", mapUser(task.getAssignedUser()));
                t.put("startDate", task.getStartDate());
                t.put("endDate", task.getEndDate());
                t.put("investedHours", task.getInvestedHours());
                t.put("estimatedHours", task.getEstimatedHours());
                t.put("remainingHours", task.getRemainingHours());
                t.put("completionDate", task.getCompletionDate());
                t.put("status", task.getTaskState().name());
                t.put("commentsNumber", task.getTaskComments().size());
                ret.add(t);
            }
        }
        
        return ret;
    }
}
