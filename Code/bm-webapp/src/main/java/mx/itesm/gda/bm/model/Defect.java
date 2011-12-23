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
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Entity
@Table(name = "Defect")
public class Defect extends AbstractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey
    private Integer defectId;

    @Column(length = 255, nullable = false)
    private String defectName;

    @Column(length = 4095, nullable = false)
    private String defectDescription;

    @ManyToOne(optional = false)
    private Project project;

    @ManyToOne(optional = false)
    private Phase detectionPhase;

    @ManyToOne(optional = false)
    private Task detectionTask;

    @ManyToOne(optional = true)
    private Phase inyectionPhase;

    @ManyToOne(optional = true)
    private Task inyectionTask;

    @ManyToOne(optional = true)
    private Phase remotionPhase;

    @ManyToOne(optional = true)
    private Task remotionTask;

    @Column(length = 255, nullable = true)
    private String defectTrigger;

    @Column(length = 255, nullable = true)
    private String impact;

    @ManyToOne(optional = true)
    private DefectType defectType;

    @Column(length = 255, nullable = true)
    private String qualifier;

    @Column(length = 255, nullable = true)
    private String age;

    @Column(length = 255, nullable = true)
    private String source;

    @Column(length = 255, nullable = true)
    private String reference;

    private int investedHours;

    @ManyToOne
    private User assignedUser;

    @Temporal(TemporalType.DATE)
    @Column(nullable = false)
    private Date openDate;

    @Temporal(TemporalType.DATE)
    @Column(nullable = true)
    private Date closeDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private DefectState defectState;

    @OneToMany(mappedBy = "defect")
    private List<DefectComment> defectComments;

    /**
     * @return the defectId
     */
    public Integer getDefectId() {
        return defectId;
    }

    /**
     * @param defectId the defectId to set
     */
    public void setDefectId(Integer defectId) {
        this.defectId = defectId;
    }

    /**
     * @return the defectName
     */
    public String getDefectName() {
        return defectName;
    }

    /**
     * @param defectName the defectName to set
     */
    public void setDefectName(String defectName) {
        this.defectName = defectName;
    }

    /**
     * @return the defectDescription
     */
    public String getDefectDescription() {
        return defectDescription;
    }

    /**
     * @param defectDescription the defectDescription to set
     */
    public void setDefectDescription(String defectDescription) {
        this.defectDescription = defectDescription;
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
     * @return the detectionPhase
     */
    public Phase getDetectionPhase() {
        return detectionPhase;
    }

    /**
     * @param detectionPhase the detectionPhase to set
     */
    public void setDetectionPhase(Phase detectionPhase) {
        this.detectionPhase = detectionPhase;
    }

    /**
     * @return the detectionTask
     */
    public Task getDetectionTask() {
        return detectionTask;
    }

    /**
     * @param detectionTask the detectionTask to set
     */
    public void setDetectionTask(Task detectionTask) {
        this.detectionTask = detectionTask;
    }

    /**
     * @return the inyectionPhase
     */
    public Phase getInyectionPhase() {
        return inyectionPhase;
    }

    /**
     * @param inyectionPhase the inyectionPhase to set
     */
    public void setInyectionPhase(Phase inyectionPhase) {
        this.inyectionPhase = inyectionPhase;
    }

    /**
     * @return the inyectionTask
     */
    public Task getInyectionTask() {
        return inyectionTask;
    }

    /**
     * @param inyectionTask the inyectionTask to set
     */
    public void setInyectionTask(Task inyectionTask) {
        this.inyectionTask = inyectionTask;
    }

    /**
     * @return the remotionPhase
     */
    public Phase getRemotionPhase() {
        return remotionPhase;
    }

    /**
     * @param remotionPhase the remotionPhase to set
     */
    public void setRemotionPhase(Phase remotionPhase) {
        this.remotionPhase = remotionPhase;
    }

    /**
     * @return the remotionTask
     */
    public Task getRemotionTask() {
        return remotionTask;
    }

    /**
     * @param remotionTask the remotionTask to set
     */
    public void setRemotionTask(Task remotionTask) {
        this.remotionTask = remotionTask;
    }

    /**
     * @return the defectTrigger
     */
    public String getDefectTrigger() {
        return defectTrigger;
    }

    /**
     * @param defectTrigger the defectTrigger to set
     */
    public void setDefectTrigger(String defectTrigger) {
        this.defectTrigger = defectTrigger;
    }

    /**
     * @return the impact
     */
    public String getImpact() {
        return impact;
    }

    /**
     * @param impact the impact to set
     */
    public void setImpact(String impact) {
        this.impact = impact;
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
     * @return the qualifier
     */
    public String getQualifier() {
        return qualifier;
    }

    /**
     * @param qualifier the qualifier to set
     */
    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    /**
     * @return the age
     */
    public String getAge() {
        return age;
    }

    /**
     * @param age the age to set
     */
    public void setAge(String age) {
        this.age = age;
    }

    /**
     * @return the source
     */
    public String getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(String source) {
        this.source = source;
    }

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
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
     * @return the openDate
     */
    public Date getOpenDate() {
        return openDate;
    }

    /**
     * @param openDate the openDate to set
     */
    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    /**
     * @return the closeDate
     */
    public Date getCloseDate() {
        return closeDate;
    }

    /**
     * @param closeDate the closeDate to set
     */
    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
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
     * @return the defectState
     */
    public DefectState getDefectState() {
        return defectState;
    }

    /**
     * @param defectState the defectState to set
     */
    public void setDefectState(DefectState defectState) {
        this.defectState = defectState;
    }

    /**
     * @return the defectComments
     */
    public List<DefectComment> getDefectComments() {
        return defectComments;
    }

    /**
     * @param defectComments the defectComments to set
     */
    public void setDefectComments(List<DefectComment> defectComments) {
        this.defectComments = defectComments;
    }

}
