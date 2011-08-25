/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: Defect.java 279 2010-10-30 15:43:29Z zerocoolx@gmail.com $
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
import javax.persistence.Table;
import mx.itesm.gda.bm.model.utils.BusinessKey;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Entity
@Table(name = "DefectType")
public class DefectType extends AbstractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey
    private Integer defectTypeId;

    @Column(length = 255, nullable = false)
    private String defectTypeName;

    @Column(length = 4095, nullable = false)
    private String defectTypeDescription;

    /**
     * @return the taskId
     */
    public Integer getDefectTypeId() {
        return defectTypeId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setDefectTypeId(Integer defectTypeId) {
        this.defectTypeId = defectTypeId;
    }

    /**
     * @return the taskName
     */
    public String getDefectTypeName() {
        return defectTypeName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setDefectTypeName(String defectTypeName) {
        this.defectTypeName = defectTypeName;
    }

    /**
     * @return the taskDescription
     */
    public String getDefectTypeDescription() {
        return defectTypeDescription;
    }

    /**
     * @param taskDescription the taskDescription to set
     */
    public void setDefectTypeDescription(String defectTypeDescription) {
        this.defectTypeDescription = defectTypeDescription;
    }
}
