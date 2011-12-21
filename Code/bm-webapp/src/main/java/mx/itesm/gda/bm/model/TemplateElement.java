/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TemplateElement.java 279 2010-10-30 15:43:29Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-30 10:43:29 -0500 (Sat, 30 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import mx.itesm.gda.bm.model.utils.BusinessKey;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Entity
@Table(name = "TemplateElement")
public class TemplateElement extends AbstractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey
    private Integer elementId;

    @ManyToOne
    @JoinColumn(name = "templateId")
    private Template template;

    @ManyToOne
    @JoinColumn(name = "defectTypeId")
    private DefectType defectType;

    @Column(length = 4095, nullable = false)
    private String elementDescription;

    /**
     * @return the elementId
     */
    public Integer getElementId() {
        return elementId;
    }

    /**
     * @param elementId the elementId to set
     */
    public void setElementId(Integer elementId) {
        this.elementId = elementId;
    }

    /**
     * @return the template
     */
    public Template getTemplate() {
        return template;
    }

    /**
     * @param template the template to set
     */
    public void setTemplate(Template template) {
        this.template = template;
    }

    /**
     * @return the defectType
     */
    public DefectType getDefectType() {
        return defectType;
    }

    /**
     * @param defectType the defectType to set
     */
    public void setDefectType(DefectType defectType) {
        this.defectType = defectType;
    }

    /**
     * @return the elementDescription
     */
    public String getElementDescription() {
        return elementDescription;
    }

    /**
     * @param elementDescription the elementDescription to set
     */
    public void setElementDescription(String elementDescription) {
        this.elementDescription = elementDescription;
    }
}