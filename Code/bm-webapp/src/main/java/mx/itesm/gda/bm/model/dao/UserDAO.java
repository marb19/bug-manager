/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserDAO.java 172 2010-10-06 07:11:45Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-06 02:11:45 -0500 (Wed, 06 Oct 2010) $
 * Last Version      : $Revision: 172 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao;

import java.util.List;
import mx.itesm.gda.bm.model.User;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 172 $
 */
public interface UserDAO extends BaseItemDAO<User> {

    public User findByUserName(String user_name);

    public User findByRecoveryTicket(String recovery_ticket);

    public User findByEmail(String email);

    public List<User> getAllAdministrators();

}
