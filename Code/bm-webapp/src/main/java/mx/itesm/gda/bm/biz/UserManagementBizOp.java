/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserManagementBizOp.java 265 2010-10-21 19:10:19Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-21 14:10:19 -0500 (Thu, 21 Oct 2010) $
 * Last Version      : $Revision: 265 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz;

import java.util.List;
import java.util.Map;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 265 $
 */
public interface UserManagementBizOp extends BizOp {

    public List<Map<String, ?>> retrieveUsers();

    public String createUser(String userName, int permissions, String fullName,
            String password, String email);

    public void deleteUser(String userName);

    public void modifyUser(String userName, int permissions, String fullName,
            String email);

    public void modifyUserPassword(String userName, String password);

    public Map<String, ?> getUser(String userName);

    public Map<String, ?> getUserByEmail(String userName);

}
