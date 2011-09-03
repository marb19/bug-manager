/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserAdminInitializer.java 338 2010-11-17 04:59:26Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-16 22:59:26 -0600 (Tue, 16 Nov 2010) $
 * Last Version      : $Revision: 338 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.utils;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 338 $
 */
@Repository
public class UserAdminInitializer {

    private static final Log LOGGER = LogFactory.getLog(
            UserAdminInitializer.class);

    private String userName;

    private String password;

    private String fullName;

    private String email;

    @Autowired
    private UserDAO userDAO;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void init() {
        LOGGER.debug("Checking for admin users");
        List<User> users = userDAO.getAllAdministrators();

        if(users.isEmpty()) {
            User userAdmin = new User();
            userAdmin.setUserName(userName);
            userAdmin.setPassword(password);
            userAdmin.setFullName(fullName);
            userAdmin.setEmail(email);
            userAdmin.setPermissions(30);

            userDAO.save(userAdmin);
            LOGGER.warn("Admin user created");
        }
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    @Required
    public void setUserName(String userName) {
        this.userName = userName;
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
    @Required
    public void setPassword(String password) {
        this.password = password;
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
    @Required
    public void setFullName(String fullName) {
        this.fullName = fullName;
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
    @Required
    public void setEmail(String email) {
        this.email = email;
    }

}
