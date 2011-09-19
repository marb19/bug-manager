/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: Project.java 247 2010-10-18 05:58:01Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-18 00:58:01 -0500 (Mon, 18 Oct 2010) $
 * Last Version      : $Revision: 247 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
@Table(name = "Project")
public class Project extends AbstractItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @BusinessKey
    private Integer projectId;

    @Column(length = 255, nullable = false)
    private String projectName;

    @OneToMany(mappedBy = "project")
    private List<Task> tasks;

    @OneToMany(mappedBy = "project")
    private List<Defect> defects;
    
    @Column(length = 4095, nullable = false)
    private String projectDescription;

    @Temporal(TemporalType.DATE)
    private Date projectDueDate;

    @Temporal(TemporalType.DATE)
    private Date projectPlannedDate;

    @OneToMany(mappedBy = "project")
    private List<Phase> phases;

   /* @OneToMany(mappedBy = "project")
    private List<Defect> defects;*/
    /**
     * @return the projectId
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * @param projectId the projectId to set
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the tasks
     */
    public List<Task> getTasks() {
        return tasks;
    }

    /**
     * @param tasks the tasks to set
     */
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * @return the tasks
     */
    public List<Defect> getDefects() {
        return defects;
    }

    /**
     * @param tasks the tasks to set
     */
    public void setDefects(List<Defect> defects) {
        this.defects = defects;
    }
    
    /**
     * @return the projectDescription
     */
    public String getProjectDescription() {
        return projectDescription;
    }

    /**
     * @param projectDescription the projectDescription to set
     */
    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    /**
     * @return the projectDueDate
     */
    public Date getProjectDueDate() {
        return projectDueDate;
    }

    /**
     * @param projectDueDate the projectDueDate to set
     */
    public void setProjectDueDate(Date projectDueDate) {
        this.projectDueDate = projectDueDate;
    }

    /**
     * @return the projectPlannedDate
     */
    public Date getProjectPlannedDate() {
        return projectPlannedDate;
    }

    /**
     * @param projectPlannedDate the projectPlannedDate to set
     */
    public void setProjectPlannedDate(Date projectPlannedDate) {
        this.projectPlannedDate = projectPlannedDate;
    }

    /**
     * @return the phases
     */
    public List<Phase> getPhases() {
        return phases;
    }

    /**
     * @param phases the phases to set
     */
    public void setPhases(List<Phase> phases) {
        this.phases = phases;
    }
}
