/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: UserAdminInitializer.java 338 2010-11-17 04:59:26Z hgrobles@gmail.com $
 * Last Revised By   : $Author: hgrobles@gmail.com $
 * Last Checked In   : $Date: 2010-11-16 22:59:26 -0600 (Tue, 16 Nov 2010) $
 * Last Version      : $Revision: 338 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.utils;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import mx.itesm.gda.bm.model.Template;
import mx.itesm.gda.bm.model.TemplateElement;
import mx.itesm.gda.bm.model.TemplateReviewType;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import mx.itesm.gda.bm.model.dao.TemplateDAO;
import mx.itesm.gda.bm.model.dao.TemplateElementDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: hgrobles@gmail.com $
 * @version $Revision: 338 $
 */
@Repository
public class TemplateInitializer {

    private static final Log LOGGER = LogFactory.getLog(
            TemplateInitializer.class);

    @Autowired
    private TemplateDAO templateDAO;

    @Autowired
    private TemplateElementDAO templateElementDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DefectTypeDAO defectTypeDAO;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void init() {
        LOGGER.debug("Checking for admin users");
        List<Template> templates = templateDAO.getAll();

        User u = userDAO.findByUserName("admin");

        if(templates.isEmpty() && u!=null) {

            Template t = new Template();
            t.setTemplateName("Revision de codigo default");
            t.setTemplateDescription("Plantilla default para revision de codigo.");
            t.setTemplateReviewType(TemplateReviewType.PERSONAL_REVIEW);
            t.setTemplatePublic(true);
            t.setAssignedUser(u);
            templateDAO.save(t);

            TemplateElement newTemplateElement = new TemplateElement();
            newTemplateElement.setTemplate(t);
            newTemplateElement.setDefectType(defectTypeDAO.findById(4));
            newTemplateElement.setElementDescription("Verificar que las clases importadas esten completas.");
            templateElementDAO.save(newTemplateElement);

            TemplateElement newTemplateElement2 = new TemplateElement();
            newTemplateElement2.setTemplate(t);
            newTemplateElement2.setDefectType(defectTypeDAO.findById(4));
            newTemplateElement2.setElementDescription("Verificar variables y parametros de inicializacion al incio del programa, inicio de un ciclo y comienzo de funcion.");
            templateElementDAO.save(newTemplateElement2);

            TemplateElement newTemplateElement3 = new TemplateElement();
            newTemplateElement3.setTemplate(t);
            newTemplateElement3.setDefectType(defectTypeDAO.findById(1));
            newTemplateElement3.setElementDescription("Verificar que los nombres de variables y metodos sean consistentes.");
            templateElementDAO.save(newTemplateElement3);

            TemplateElement newTemplateElement4 = new TemplateElement();
            newTemplateElement4.setTemplate(t);
            newTemplateElement4.setDefectType(defectTypeDAO.findById(2));
            newTemplateElement4.setElementDescription("Verificar la consistencia de los parentesis. Cada parentesis abierto tiene que ser cerrado.");
            templateElementDAO.save(newTemplateElement4);

            TemplateElement newTemplateElement5 = new TemplateElement();
            newTemplateElement5.setTemplate(t);
            newTemplateElement5.setDefectType(defectTypeDAO.findById(4));
            newTemplateElement5.setElementDescription("Verificar el uso correcto de los operadores logicos.");
            templateElementDAO.save(newTemplateElement5);

            TemplateElement newTemplateElement6 = new TemplateElement();
            newTemplateElement6.setTemplate(t);
            newTemplateElement6.setDefectType(defectTypeDAO.findById(2));
            newTemplateElement6.setElementDescription("Verificar que la sintaxis y puntuacion de cada linea es la correcta.");
            templateElementDAO.save(newTemplateElement6);

            TemplateElement newTemplateElement7 = new TemplateElement();
            newTemplateElement7.setTemplate(t);
            newTemplateElement7.setDefectType(defectTypeDAO.findById(1));
            newTemplateElement7.setElementDescription("Verificar que el codigo sigue los estandares de codificacion establecidos.");
            templateElementDAO.save(newTemplateElement7);

        }
    }

}
