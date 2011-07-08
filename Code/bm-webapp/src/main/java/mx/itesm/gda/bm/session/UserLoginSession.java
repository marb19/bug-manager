/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserLoginSession.java 146 2010-10-03 04:38:39Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-02 23:38:39 -0500 (Sat, 02 Oct 2010) $
 * Last Version      : $Revision: 146 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.session;

import java.io.Serializable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 146 $
 */
@Component
@Scope("session")
public class UserLoginSession implements Serializable {

    private String loggedUserName;

    private String loginMessage;

    /**
     * @return the loggedUserName
     */
    public String getLoggedUserName() {
        return loggedUserName;
    }

    /**
     * @param loggedUserName the loggedUserName to set
     */
    public void setLoggedUserName(String loggedUserName) {
        this.loggedUserName = loggedUserName;
    }

    /**
     * @return the loginMessage
     */
    public String getLoginMessage() {
        return loginMessage;
    }

    /**
     * @param loginMessage the loginMessage to set
     */
    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

}
