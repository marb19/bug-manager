/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskCompletionReport.java 279 2010-10-30 15:43:29Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 10:43:29 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 279 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
@Table(name = "TaskCompletionReport")
public class TaskCompletionReport extends AbstractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey
    private Integer taskCompletionReportId;

    @ManyToOne(optional = false)
    private Task task;

    @ManyToOne(optional = false)
    private User reportingUser;

    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;

    private int investedHoursToDate;

    private int remainingHoursToDate;

    /**
     * @return the taskCompletionReportId
     */
    public Integer getTaskCompletionReportId() {
        return taskCompletionReportId;
    }

    /**
     * @param taskCompletionReportId the taskCompletionReportId to set
     */
    public void setTaskCompletionReportId(Integer taskCompletionReportId) {
        this.taskCompletionReportId = taskCompletionReportId;
    }

    /**
     * @return the task
     */
    public Task getTask() {
        return task;
    }

    /**
     * @param task the task to set
     */
    public void setTask(Task task) {
        this.task = task;
    }

    /**
     * @return the reportingUser
     */
    public User getReportingUser() {
        return reportingUser;
    }

    /**
     * @param reportingUser the reportingUser to set
     */
    public void setReportingUser(User reportingUser) {
        this.reportingUser = reportingUser;
    }

    /**
     * @return the reportDate
     */
    public Date getReportDate() {
        return reportDate;
    }

    /**
     * @param reportDate the reportDate to set
     */
    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    /**
     * @return the investedHoursToDate
     */
    public int getInvestedHoursToDate() {
        return investedHoursToDate;
    }

    /**
     * @param investedHoursToDate the investedHoursToDate to set
     */
    public void setInvestedHoursToDate(int investedHoursToDate) {
        this.investedHoursToDate = investedHoursToDate;
    }

    /**
     * @return the remainingHoursToDate
     */
    public int getRemainingHoursToDate() {
        return remainingHoursToDate;
    }

    /**
     * @param remainingHoursToDate the remainingHoursToDate to set
     */
    public void setRemainingHoursToDate(int remainingHoursToDate) {
        this.remainingHoursToDate = remainingHoursToDate;
    }

}
