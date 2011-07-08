/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserAccessBizOp.java 133 2010-09-28 04:29:01Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-09-27 23:29:01 -0500 (Mon, 27 Sep 2010) $
 * Last Version      : $Revision: 133 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz;

import java.util.Map;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 133 $
 */
public interface UserAccessBizOp extends BizOp {

    public boolean checkValidUser(String username);

    public boolean validateUserLogin(String username, String password);

    public boolean checkUserAdministrator(String username);

    public boolean initiatePasswordRecovery(String email);

    public boolean validatePasswordRecoveryTicket(String pwd_ticket);

    public Map<String, ?> getUserModel(String username);

}
