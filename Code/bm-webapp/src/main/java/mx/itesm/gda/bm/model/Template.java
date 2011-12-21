/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: Template.java 279 2010-10-30 15:43:29Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 10:43:29 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import mx.itesm.gda.bm.model.utils.BusinessKey;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Entity
@Table(name = "Template")
public class Template extends AbstractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey
    private Integer templateId;

    @Column(length = 255, nullable = false)
    private String templateName;

    @Column(length = 4095, nullable = false)
    private String templateDescription;

    @Column(nullable=false)
    private int templateReviewType;

    @Column(nullable = false)
    private boolean templatePublic;

    @ManyToOne
    private User userName;

    @OneToMany (mappedBy = "template")
    private List<TemplateElement> templateElements;

    /**
     * @return the templateId
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * @param defectId the templateId to set
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * @return the defectName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return the templateDescription
     */
    public String getTemplateDescription() {
        return templateDescription;
    }

    /**
     * @param templateDescription the templateDescription to set
     */
    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    /**
     * @return the templateReviewType
     */
    public int getTemplateReviewType() {
        return templateReviewType;
    }

    /**
     * @param templateReviewType the templateReviewType to set
     */
    public void setTemplateReviewType(int templateReviewType) {
        this.templateReviewType = templateReviewType;
    }

    /**
     * @return the templatePublic
     */
    public boolean getTemplatePublic() {
        return templatePublic;
    }

    /**
     * @param templatePublic the templatePublic to set
     */
    public void setTemplatePublic(boolean templatePublic) {
        this.templatePublic = templatePublic;
    }

    /**
     * @return the userName
     */
    public User getAssignedUser() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setAssignedUser(User userName) {
        this.userName = userName;
    }

        /**
     * @return the templateElements
     */
    public List<TemplateElement> getTemplateElements() {
        return templateElements;
    }

    /**
     * @param templateElements the templateElements to set
     */
    public void setTemplateElements(List<TemplateElement> templateElements) {
        this.templateElements = templateElements;
    }
}