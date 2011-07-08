/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserDAOImpl.java 181 2010-10-08 04:17:42Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-07 23:17:42 -0500 (Thu, 07 Oct 2010) $
 * Last Version      : $Revision: 181 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 181 $
 */
@Scope("prototype")
@Repository
public class UserDAOImpl extends BaseItemDAOImpl<User> implements UserDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<User> getAll() {
        @SuppressWarnings("unchecked")
        List<User> result = getEntityManager().createQuery(
                "SELECT u FROM User u").getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<User> getAllAdministrators() {
        @SuppressWarnings("unchecked")
        List<User> result = getEntityManager().createQuery(
                "SELECT u FROM User u WHERE u.administrator = true").
                getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public User findByUserName(String user_name) {
        if(user_name == null) {
            return null;
        }
        return getEntityManager().find(User.class, user_name);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public User findByRecoveryTicket(String recovery_ticket) {
        try {
            return (User)getEntityManager().createQuery("SELECT u FROM User u "
                    + "WHERE u.passwordRecoveryTicket = :recovery_ticket "
                    + "AND u.passwordRecoveryExpiration > CURRENT_TIMESTAMP").
                    setParameter("recovery_ticket", recovery_ticket).
                    getSingleResult();
        } catch(EmptyResultDataAccessException erdae) {
            return null;
        }
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public User findByEmail(String email) {
        try {
            return (User)getEntityManager().createQuery("SELECT u FROM User u "
                    + "WHERE u.email = :email").setParameter("email", email).
                    getSingleResult();
        } catch(EmptyResultDataAccessException erdae) {
            return null;
        }
    }

}
