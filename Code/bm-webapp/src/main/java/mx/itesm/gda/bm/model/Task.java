/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: Task.java 279 2010-10-30 15:43:29Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 10:43:29 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 279 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import mx.itesm.gda.bm.model.utils.BusinessKey;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 279 $
 */
@Entity
@Table(name = "Task")
public class Task extends AbstractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey
    private Integer taskId;

    @Column(length = 255, nullable = false)
    private String taskName;

    @Column(length = 4095, nullable = false)
    private String taskDescription;

    @ManyToOne(optional = true)
    private Phase phase;

    @ManyToOne(optional = false)
    private Project project;

    @ManyToOne
    private User assignedUser;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date startDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date endDate;

    @Temporal(TemporalType.DATE)
    private Date completionDate;

    private int estimatedHours;

    private int investedHours;

    private int remainingHours;

    private int taskSize;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TaskState taskState;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private TaskType taskType;

    @OneToMany(mappedBy = "task")
    private List<TaskComment> taskComments;

    @OneToMany(mappedBy = "task")
    private List<TaskCompletionReport> taskCompletionReports;

    /**
     * @return the taskId
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    /**
     * @return the taskDescription
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * @param taskDescription the taskDescription to set
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * @return the phase
     */
    public Phase getPhase() {
        return phase;
    }

    /**
     * @param phase the phase to set
     */
    public void setPhase(Phase phase) {
        this.phase = phase;
    }

    /**
     * @return the project
     */
    public Project getProject() {
        return project;
    }

    /**
     * @param project the project to set
     */
    public void setProject(Project project) {
        this.project = project;
    }

    /**
     * @return the assignedUser
     */
    public User getAssignedUser() {
        return assignedUser;
    }

    /**
     * @param assignedUser the assignedUser to set
     */
    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
    }

    /**
     * @return the startDate
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the completionDate
     */
    public Date getCompletionDate() {
        return completionDate;
    }

    /**
     * @param completionDate the completionDate to set
     */
    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * @return the estimatedHours
     */
    public int getEstimatedHours() {
        return estimatedHours;
    }

    /**
     * @param estimatedHours the estimatedHours to set
     */
    public void setEstimatedHours(int estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    /**
     * @return the investedHours
     */
    public int getInvestedHours() {
        return investedHours;
    }

    /**
     * @param investedHours the investedHours to set
     */
    public void setInvestedHours(int investedHours) {
        this.investedHours = investedHours;
    }

    /**
     * @return the remainingHours
     */
    public int getRemainingHours() {
        return remainingHours;
    }

    /**
     * @param remainingHours the remainingHours to set
     */
    public void setRemainingHours(int remainingHours) {
        this.remainingHours = remainingHours;
    }

    /**
     * @return the taskState
     */
    public TaskState getTaskState() {
        return taskState;
    }

    /**
     * @param taskState the taskState to set
     */
    public void setTaskState(TaskState taskState) {
        this.taskState = taskState;
    }

    /**
     * @return the taskType
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * @param taskType the taskType to set
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    /**
     * @return the taskComments
     */
    public List<TaskComment> getTaskComments() {
        return taskComments;
    }

    /**
     * @param taskComments the taskComments to set
     */
    public void setTaskComments(List<TaskComment> taskComments) {
        this.taskComments = taskComments;
    }

    /**
     * @return the taskCompletionReports
     */
    public List<TaskCompletionReport> getTaskCompletionReports() {
        return taskCompletionReports;
    }

    /**
     * @param taskCompletionReports the taskCompletionReports to set
     */
    public void setTaskCompletionReports(
            List<TaskCompletionReport> taskCompletionReports) {
        this.taskCompletionReports = taskCompletionReports;
    }

     /**
     * @return the size
     */
    public int getSize() {
        return taskSize;
    }

    /**
     * @param taskSize the size to set
     */
    public void setSize(int taskSize) {
        this.taskSize = taskSize;
    }
}
