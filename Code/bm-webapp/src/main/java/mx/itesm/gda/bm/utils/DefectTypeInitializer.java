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
import mx.itesm.gda.bm.model.DefectType;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
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
public class DefectTypeInitializer {

    private static final Log LOGGER = LogFactory.getLog(
            DefectTypeInitializer.class);

    @Autowired
    private DefectTypeDAO defectTypeDAO;

    @PersistenceContext
    private EntityManager em;

    @PostConstruct
    @Transactional
    public void init() {
        LOGGER.debug("Checking for defect types");
        List<DefectType> defectTypes = defectTypeDAO.getAll();

        if(defectTypes.isEmpty()) {
            DefectType dt = new DefectType();
            dt.setDefectTypeName("Documentacion");
            dt.setDefectTypeDescription("Documentos, comentarios o mensajes que no se entienden o son incorrectos.");
            defectTypeDAO.save(dt);
            LOGGER.warn("DefectType Documentacion created");

            DefectType dt2 = new DefectType();
            dt2.setDefectTypeName("Sintaxis");
            dt2.setDefectTypeDescription("Defecto usualmente detectado por el compilador, como errores de sintaxis, declaraciones omitidas, etc.");
            defectTypeDAO.save(dt2);
            LOGGER.warn("DefectType Sintaxis created");

            DefectType dt3 = new DefectType();
            dt3.setDefectTypeName("Construccion/Empaquetado");
            dt3.setDefectTypeDescription("Errores en control de versiones o en manejo de cambios.");
            defectTypeDAO.save(dt3);
            LOGGER.warn("DefectType Construccion/Empaquetamiento created");

            DefectType dt4 = new DefectType();
            dt4.setDefectTypeName("Asignacion");
            dt4.setDefectTypeDescription("Errores en manejo de datos o llamadas a procedimientos, como operadores incorrectos, asignacion incorrecta de objetos, asignación duplicada u omitida.");
            defectTypeDAO.save(dt4);
            LOGGER.warn("DefectType Asignacion created");

            DefectType dt5 = new DefectType();
            dt5.setDefectTypeName("Interface");
            dt5.setDefectTypeDescription("Error en diseño de interfaces. La interfaz está incompleta o incorrecta, los objetos no son visibles.");
            defectTypeDAO.save(dt5);
            LOGGER.warn("DefectType Interface created");

            DefectType dt6 = new DefectType();
            dt6.setDefectTypeName("Chequeo");
            dt6.setDefectTypeDescription("Manejo de errores omitido, incorrecto o inadecuado.");
            defectTypeDAO.save(dt6);
            LOGGER.warn("DefectType Chequeo created");

            DefectType dt7 = new DefectType();
            dt7.setDefectTypeName("Datos");
            dt7.setDefectTypeDescription("Estructura y contenido de los datos incorrecto, omitido o inadecuado.");
            defectTypeDAO.save(dt7);
            LOGGER.warn("DefectType Datos created");

            DefectType dt8 = new DefectType();
            dt8.setDefectTypeName("Funcion");
            dt8.setDefectTypeDescription("Errores en algoritmos o funcionalidad. Operaciones ejecutadas muy temprano, muy tarde o de la manera incorrecta, algoritmo incorrecto. ");
            defectTypeDAO.save(dt8);
            LOGGER.warn("DefectType Funcion created");

            DefectType dt9 = new DefectType();
            dt9.setDefectTypeName("Sistema");
            dt9.setDefectTypeDescription("Errores debido a influencias externas fuera del alcance del programa, como red, hardware o sincronizacion.");
            defectTypeDAO.save(dt9);
            LOGGER.warn("DefectType Sistema created");

            DefectType dt10 = new DefectType();
            dt10.setDefectTypeName("Ambiente");
            dt10.setDefectTypeDescription("Defecto en el ambiente de desarrollo o en los sistemas de soporte, como defectos en el compilador o herramientas.");
            defectTypeDAO.save(dt10);
            LOGGER.warn("DefectType Ambiente created");
        }
    }

}
