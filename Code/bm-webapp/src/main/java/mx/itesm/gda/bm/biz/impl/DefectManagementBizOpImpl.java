/* ***************************************************************************
 *   Copyright (c) 2011 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: NewUserController.java 312 2010-11-11 06:36:43Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-11-11 00:36:43 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 312 $
 *
 * Original Author   : Humberto Garcia Robles
 * Notes             :
 * ************************************************************************ */

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itesm.gda.bm.biz.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.DefectManagementBizOp;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;

/**
 *
 * @author CDE-Marco
 */
@Scope("prototype")
@Component
public class DefectManagementBizOpImpl extends AbstractBizOp implements
        DefectManagementBizOp{
    
    @Autowired
    private DefectTypeDAO defectTypeDAO;

    @Autowired
    private DefectDAO defectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> retrieveAllDefects() {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();

        List<Defect> defects;
        defects = defectDAO.getAll();
            
        for(Defect defect : defects) {
            /*Map<String, Object> dt = new HashMap<String, Object>();
            dt.put("ID", defectType.getDefectTypeId());
            dt.put("Name", defectType.getDefectTypeName());
            dt.put("Description", defectType.getDefectTypeDescription());
            boolean isEmpty= true/*(Definir condiciones);
            dt.put("isEmpty", isEmpty);
            ret.add(dt);*/
        }

        return ret;
    }

    @Override
    public List<Map<String, ?>> retrieveProjectDefects(int projectID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int createDefect(String defectTypeName, String defectName, String defectDescription, int projectID, int detectionPhaseID, int detectionTask) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void modifyDefect(int defectID, String defectName, String defectDescription, int detectionPhaseID, int detectionTaskID, int inyectionPhaseID, int inyectionTaskID, int remotionPhaseID, String defectTrigger, String impact, int defectTypeID, String qualifier, String age, String source, int investedhours, String assignedUser, Date openDate) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void deleteDefect(int defectID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Map<String, ?> getDefect(int defectID) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int addComment(int defectID, String commentAuthor, String newComment) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
