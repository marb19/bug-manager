/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserAccessBizConfig.java 197 2010-10-09 20:07:05Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 15:07:05 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 197 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz;

import org.springframework.beans.factory.annotation.Required;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 197 $
 */
public class UserAccessBizConfig {

    private String recoveryMailFrom;

    private String recoveryLinkMailTemplate;

    private String recoveryNewPasswordMailTemplate;

    /**
     * @return the recoveryMailFrom
     */
    public String getRecoveryMailFrom() {
        return recoveryMailFrom;
    }

    /**
     * @param recoveryMailFrom the recoveryMailFrom to set
     */
    @Required
    public void setRecoveryMailFrom(String recoveryMailFrom) {
        this.recoveryMailFrom = recoveryMailFrom;
    }

    /**
     * @return the recoveryLinkMailTemplate
     */
    public String getRecoveryLinkMailTemplate() {
        return recoveryLinkMailTemplate;
    }

    /**
     * @param recoveryLinkMailTemplate the recoveryLinkMailTemplate to set
     */
    @Required
    public void setRecoveryLinkMailTemplate(String recoveryLinkMailTemplate) {
        this.recoveryLinkMailTemplate = recoveryLinkMailTemplate;
    }

    /**
     * @return the recoveryNewPasswordMailTemplate
     */
    public String getRecoveryNewPasswordMailTemplate() {
        return recoveryNewPasswordMailTemplate;
    }

    /**
     * @param recoveryNewPasswordMailTemplate the recoveryNewPasswordMailTemplate to set
     */
    @Required
    public void setRecoveryNewPasswordMailTemplate(
            String recoveryNewPasswordMailTemplate) {
        this.recoveryNewPasswordMailTemplate = recoveryNewPasswordMailTemplate;
    }

}
