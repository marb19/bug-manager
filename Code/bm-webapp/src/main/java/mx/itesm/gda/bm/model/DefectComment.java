/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskComment.java 247 2010-10-18 05:58:01Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-18 00:58:01 -0500 (Mon, 18 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
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
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Entity
@Table(name = "DefectComment")
public class DefectComment extends AbstractItem {

    @Id
    @BusinessKey
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer defectCommentId;

    @ManyToOne(optional = false)
    private Defect defect;

    @ManyToOne(optional = false)
    private User author;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date commentDate;

    @Column(length = 16383, nullable = false)
    private String commentText;

    /**
     * @return the defectCommentId
     */
    public Integer getDefectCommentId() {
        return defectCommentId;
    }

    /**
     * @param defectCommentId the defectCommentId to set
     */
    public void setDefectCommentId(Integer defectCommentId) {
        this.defectCommentId = defectCommentId;
    }

    /**
     * @return the defect
     */
    public Defect getDefect() {
        return defect;
    }

    /**
     * @param defect the defect to set
     */
    public void setDefect(Defect defect) {
        this.defect = defect;
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
