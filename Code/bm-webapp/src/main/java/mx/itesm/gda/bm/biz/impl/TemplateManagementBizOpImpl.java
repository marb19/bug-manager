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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.BizException;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.TemplateDAO;
import mx.itesm.gda.bm.biz.TemplateManagementBizOp;
import mx.itesm.gda.bm.model.DefectType;
import mx.itesm.gda.bm.model.Template;
import mx.itesm.gda.bm.model.TemplateElement;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import mx.itesm.gda.bm.model.dao.TemplateElementDAO;
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
    TemplateElementDAO templateElementDAO;

    @Autowired
    DefectTypeDAO defectTypeDAO;

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
            int templateReviewType, boolean templatePublic,
            String assignedUser) {

        User u = userDAO.findByUserName(assignedUser);

        Template t = new Template();
        t.setTemplateName(templateName);
        t.setTemplateDescription(templateDescription);
        t.setTemplateReviewType(templateReviewType);
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
            int templateReviewType, boolean templatePublic,
            String assignedUser) {
        Template t = templateDAO.findById(templateId);
        User u = userDAO.findByUserName(assignedUser);

        t.setTemplateName(templateName);
        t.setTemplateDescription(templateDescription);
        t.setAssignedUser(u);
        t.setTemplateReviewType(templateReviewType);
        t.setTemplatePublic(templatePublic);

        templateDAO.update(t);

        return t.getTemplateId();
    }

    @Override
    public List<Map<String, ?>> retrieveTemplates(String userName) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();

        for(Template template : templateDAO.getAll()) {
            if(template.getAssignedUser().getUserName().equals(userName)){
                Map<String, Object> t = new HashMap<String, Object>();
                t.put("templateId", template.getTemplateId());
                t.put("templateName", template.getTemplateName());
                t.put("templateDescription", template.getTemplateDescription());
                t.put("templateReviewType", template.getTemplateReviewType());
                t.put("templatePublic", template.getTemplatePublic());
                t.put("assignedUser", userName);
                ret.add(t);
            }
        }

        return ret;
    }

    @Override
    public void saveElement (int templateId, int defectTypeId, String elementDescription){
        Template t = templateDAO.findById(templateId);
        DefectType d = defectTypeDAO.findById(defectTypeId);
        TemplateElement te = new TemplateElement();
        te.setTemplate(t);
        te.setDefectType(d);
        te.setElementDescription(elementDescription);
        templateElementDAO.save(te);
    }

    @Override
    public Map<String, ?> getTemplateElement(Integer templateElementId) {

        TemplateElement templateElement = templateElementDAO.findById(templateElementId);
        Map<String, Object> t = new HashMap<String, Object>();
            t.put("elementId", templateElement.getElementId());
            t.put("templateId", templateElement.getTemplate().getTemplateId());
            t.put("defectTypeId", templateElement.getDefectType().getDefectTypeId());
            t.put("defectTypeName", templateElement.getDefectType().getDefectTypeName());
            t.put("elementDescription", templateElement.getElementDescription());

        return t;
    }

    @Override
    public List<Map<String, ?>> getTemplateElements (Integer templateId){
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Template template = templateDAO.findById(templateId);
        for(TemplateElement templateElement : template.getTemplateElements()) {
            Map<String, Object> t = new HashMap<String, Object>();
            t.put("elementId", templateElement.getElementId());
            t.put("templateId", templateElement.getTemplate().getTemplateId());
            t.put("defectTypeId", templateElement.getDefectType().getDefectTypeId());
            t.put("defectTypeName", templateElement.getDefectType().getDefectTypeName());
            t.put("elementDescription", templateElement.getElementDescription());
            ret.add(t);
        }

        return ret;
    }

    @Override
    public int addElement(int templateId, int defectTypeId, String elementDescription){
        Template t = templateDAO.findById(templateId);
        DefectType dt = defectTypeDAO.findById(defectTypeId);
        TemplateElement newTemplateElement = new TemplateElement();
        newTemplateElement.setTemplate(t);
        newTemplateElement.setDefectType(dt);
        newTemplateElement.setElementDescription(elementDescription);
        templateElementDAO.save(newTemplateElement);
        return newTemplateElement.getElementId();
    }

    @Override
    public int modifyTemplateElement(int templateElementId, int defectTypeId, String elementDescription){
        TemplateElement element = templateElementDAO.findById(templateElementId);
        DefectType dt = defectTypeDAO.findById(defectTypeId);

        element.setDefectType(dt);
        element.setElementDescription(elementDescription);

        templateElementDAO.update(element);

        return element.getElementId();
    }
}
