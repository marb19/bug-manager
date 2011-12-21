/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TemplateManagementBizOp.java 315 2010-11-12 03:19:45Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-11-11 21:19:45 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 315 $
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
 * @version $Revision: 315 $
 */
public interface TemplateManagementBizOp extends BizOp {

    public int createTemplate(String templateName, String templateDescription,
            int templateReviewType, boolean templatePublic,
            String assignedUser);

    public int modifyTemplate(int templateId, String templateName, String templateDescription,
            int templateReviewType, boolean templatePublic,
            String assignedUser);

    public int modifyTemplateElement(int templateElementId, int defectTypeId, String elementDescription);
    
    public Map<String, ?> getTemplate(Integer templateId);

    public void deleteTemplate(Integer templateId);

    public List<Map<String, ?>> retrieveTemplates(String userName);

    public void saveElement (int templateId, int defectTypeId, String elementDescription);

    public Map<String, ?> getTemplateElement(Integer templateElementId);

    public List<Map<String, ?>> getTemplateElements (Integer templateId);

    public int addElement(int templateId, int defectTypeId, String elementDescription);
}
