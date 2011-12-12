/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TemplateDAOImpl.java 193 2010-10-09 07:39:00Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 02:39:00 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.Template;
import mx.itesm.gda.bm.model.dao.TemplateDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Repository
public class TemplateDAOImpl extends BaseItemDAOImpl<Template> implements TemplateDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Template> getAll() {
        @SuppressWarnings("unchecked")
        List<Template> defects = (List<Template>)getEntityManager().createQuery(
                "SELECT t FROM Template t").getResultList();
        return defects;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Template findById(int template_id) {
        return getEntityManager().find(Template.class, template_id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Template> searchByTemplateName(String template_name) {
        @SuppressWarnings("unchecked")
        List<Template> templates = (List<Template>)getEntityManager().createQuery(
                "SELECT t FROM Template t WHERE t.templateName LIKE :name").
                setParameter("name", "%" + template_name + "%").getResultList();
        return templates;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Template> searchByUser(String username){
        @SuppressWarnings("unchecked")
        List<Template> result = getEntityManager().createQuery("SELECT t FROM Template t "
                + "WHERE u.userName = :username")
                .setParameter("username", username)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Template> searchByReviewType(int templateReviewType){
        @SuppressWarnings("unchecked")
        List<Template> result = getEntityManager().createQuery("SELECT t FROM Template t "
                + "WHERE rt.templateReviewType = :templateReviewType")
                .setParameter("templateReviewType", templateReviewType)
                .getResultList();
        return result;
    }

}
