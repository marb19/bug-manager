/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: User.java 152 2010-10-04 16:49:06Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-04 11:49:06 -0500 (Mon, 04 Oct 2010) $
 * Last Version      : $Revision: 152 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model;

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import mx.itesm.gda.bm.model.utils.BusinessKey;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 152 $
 */
@Entity
@Table(name = "User_t")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class User extends AbstractItem {

    @Id
    @Column(length = 64)
    @BusinessKey
    private String userName;

    @Column(length = 255, nullable = false)
    private String fullName;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(length = 255, nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private int permissions;

    @Column(length = 64)
    private String passwordRecoveryTicket;

    @Temporal(TemporalType.TIMESTAMP)
    private Date passwordRecoveryExpiration;

    @OneToMany(mappedBy = "assignedUser")
    private List<Task> assignedTasks;

    @OneToMany(mappedBy = "author")
    private List<TaskComment> authoredComments;

    @OneToMany(mappedBy = "reportingUser")
    private List<TaskCompletionReport> authoredCompletionReports;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the permissions
     */
    public int getPermissions() {
        return permissions;
    }

    /**
     * @param permissions the permissions to set
     */
    public void setPermissions(int permissions) {
        this.permissions = permissions;
    }

     /**
     * @return the administrator
     */
    public boolean isAdministrator() {
        return permissions>=30;
    }

    /**
     * @return the assignedTasks
     */
    public List<Task> getAssignedTasks() {
        return assignedTasks;
    }

    /**
     * @param assignedTasks the assignedTasks to set
     */
    public void setAssignedTasks(List<Task> assignedTasks) {
        this.assignedTasks = assignedTasks;
    }

    /**
     * @return the authoredComments
     */
    public List<TaskComment> getAuthoredComments() {
        return authoredComments;
    }

    /**
     * @param authoredComments the authoredComments to set
     */
    public void setAuthoredComments(List<TaskComment> authoredComments) {
        this.authoredComments = authoredComments;
    }

    /**
     * @return the authoredCompletionReports
     */
    public List<TaskCompletionReport> getAuthoredCompletionReports() {
        return authoredCompletionReports;
    }

    /**
     * @param authoredCompletionReports the authoredCompletionReports to set
     */
    public void setAuthoredCompletionReports(
            List<TaskCompletionReport> authoredCompletionReports) {
        this.authoredCompletionReports = authoredCompletionReports;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the passwordRecoveryTicket
     */
    public String getPasswordRecoveryTicket() {
        return passwordRecoveryTicket;
    }

    /**
     * @param passwordRecoveryTicket the passwordRecoveryTicket to set
     */
    public void setPasswordRecoveryTicket(String passwordRecoveryTicket) {
        this.passwordRecoveryTicket = passwordRecoveryTicket;
    }

    /**
     * @return the passwordRecoveryExpiration
     */
    public Date getPasswordRecoveryExpiration() {
        return passwordRecoveryExpiration;
    }

    /**
     * @param passwordRecoveryExpiration the passwordRecoveryExpiration to set
     */
    public void setPasswordRecoveryExpiration(Date passwordRecoveryExpiration) {
        this.passwordRecoveryExpiration = passwordRecoveryExpiration;
    }

}
