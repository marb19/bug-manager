/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: Phase.java 279 2010-10-30 15:43:29Z zerocoolx@gmail.com $
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import mx.itesm.gda.bm.model.utils.BusinessKey;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Entity
@Table(name = "Phase")
public class Phase extends AbstractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey
    private Integer phaseId;

    @Column(length = 255, nullable = false)
    private String phaseName;

    @ManyToOne(optional = false)
    private Project project;

    private int projectOrder;

    /**
     * @return the taskId
     */
    public Integer getPhaseId() {
        return phaseId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setPhaseId(Integer phaseId) {
        this.phaseId = phaseId;
    }

    /**
     * @return the taskName
     */
    public String getPhaseName() {
        return phaseName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setPhaseName(String defectTypeName) {
        this.phaseName = phaseName;
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
     * @return the projectOrder
     */
    public int getProjectOrder() {
        return projectOrder;
    }

    /**
     * @param taskDescription the taskDescription to set
     */
    public void setProjectOrder(int projectOrder) {
        this.projectOrder = projectOrder;
    }
}