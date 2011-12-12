/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TemplateManagementBizOpImpl.java 343 2010-11-18 00:26:31Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-11-17 18:26:31 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 343 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.util.HashMap;
import java.util.Map;
import mx.itesm.gda.bm.biz.BizException;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.TemplateDAO;
import mx.itesm.gda.bm.biz.TemplateManagementBizOp;
import mx.itesm.gda.bm.model.Template;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 343 $
 */
@Scope("prototype")
@Component
public class TemplateManagementBizOpImpl extends AbstractBizOp implements
        TemplateManagementBizOp {

    @Autowired
    TemplateDAO templateDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public Map<String, ?> getTemplate(Integer templateID) {

        Template template = templateDAO.findById(templateID);
        Map<String, Object> t = new HashMap<String, Object>();

        t.put("templateId", template.getTemplateId());
        t.put("templateName", template.getTemplateName());
        t.put("templateDescription", template.getTemplateDescription());
        t.put("templateReviewType", template.getTemplateReviewType());
        t.put("assignedUser", mapUser(template.getAssignedUser()));
        t.put("templatePublic", template.getTemplatePublic());

        return t;
    }

    private Map<String, ?> mapUser(User user) {
        Map<String, Object> u = new HashMap<String, Object>();
        u.put("userName", user.getUserName());
        u.put("fullName", user.getFullName());
        u.put("email", user.getEmail());
        return u;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public int createTemplate(String templateName, String templateDescription,
            int templateReviewType, byte [] templateBlob, boolean templatePublic,
            String assignedUser) {

        User u = userDAO.findByUserName(assignedUser);

        Template t = new Template();
        t.setTemplateName(templateName);
        t.setTemplateDescription(templateDescription);
        t.setTemplateReviewType(templateReviewType);
        t.setTemplateBlob(templateBlob);
        t.setTemplatePublic(templatePublic);
        t.setAssignedUser(u);
        templateDAO.save(t);
        
        return t.getTemplateId();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public void deleteTemplate(Integer templateID) {
        Template t = templateDAO.findById(templateID);

        if (t == null){
            throw new BizException("Cannot delete non-existing template");
        }

        templateDAO.delete(t);
    }

    @Override
    public int modifyTemplate(int templateId, String templateName, String templateDescription,
            int templateReviewType, byte [] templateBlob, boolean templatePublic,
            String assignedUser) {
        Template t = templateDAO.findById(templateId);
        User u = userDAO.findByUserName(assignedUser);

        t.setTemplateName(templateName);
        t.setTemplateDescription(templateDescription);
        t.setAssignedUser(u);
        t.setTemplateReviewType(templateReviewType);
        t.setTemplateBlob(templateBlob);
        t.setTemplatePublic(templatePublic);

        templateDAO.update(t);

        return t.getTemplateId();
    }

}
