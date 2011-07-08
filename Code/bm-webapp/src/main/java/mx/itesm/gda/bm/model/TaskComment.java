/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskComment.java 247 2010-10-18 05:58:01Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-18 00:58:01 -0500 (Mon, 18 Oct 2010) $
 * Last Version      : $Revision: 247 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

import java.util.Date;
import javax.persistence.Column;
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
 * @version $Revision: 247 $
 */
@Entity
@Table(name = "TaskComment")
public class TaskComment extends AbstractItem {

    @Id
    @BusinessKey
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer taskCommentId;

    @ManyToOne(optional = false)
    private Task task;

    @ManyToOne(optional = false)
    private User author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date commentDate;

    @Column(length = 16383, nullable = false)
    private String commentText;

    /**
     * @return the taskCommentId
     */
    public Integer getTaskCommentId() {
        return taskCommentId;
    }

    /**
     * @param taskCommentId the taskCommentId to set
     */
    public void setTaskCommentId(Integer taskCommentId) {
        this.taskCommentId = taskCommentId;
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
     * @return the author
     */
    public User getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(User author) {
        this.author = author;
    }

    /**
     * @return the commentDate
     */
    public Date getCommentDate() {
        return commentDate;
    }

    /**
     * @param commentDate the commentDate to set
     */
    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    /**
     * @return the commentText
     */
    public String getCommentText() {
        return commentText;
    }

    /**
     * @param commentText the commentText to set
     */
    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

}
