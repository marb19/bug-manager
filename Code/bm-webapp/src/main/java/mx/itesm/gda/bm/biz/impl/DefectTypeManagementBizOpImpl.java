/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itesm.gda.bm.biz.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.BizException;
import mx.itesm.gda.bm.biz.DefectTypeManagementBizOp;;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import mx.itesm.gda.bm.model.DefectType;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import mx.itesm.gda.bm.model.DefectType;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import mx.itesm.gda.bm.model.DefectType;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import mx.itesm.gda.bm.model.DefectType;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;

/**
 *
 * @author CDE-Marco
 */
@Scope("prototype")
@Component
public class DefectTypeManagementBizOpImpl extends AbstractBizOp implements
        DefectTypeManagementBizOp{
    
    @Autowired
    private DefectTypeDAO defectTypeDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> retrieveDefectTypes(){
    
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();

        List<DefectType> defectTypes;
        defectTypes = defectTypeDAO.getAll();
            
        for(DefectType defectType : defectTypes) {
            Map<String, Object> dt = new HashMap<String, Object>();
            dt.put("ID", defectType.getDefectTypeId());
            dt.put("Name", defectType.getDefectTypeName());
            dt.put("Description", defectType.getDefectTypeDescription());
            boolean isEmpty= false/*(Definir condiciones)*/;
            dt.put("isEmpty", isEmpty);
            ret.add(dt);
        }

        return ret;
    }
    
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public int createDefectType(String defectTypeName, String defectTypeDescription){
        
        DefectType dt = new DefectType();
        dt.setDefectTypeName(defectTypeName);
        dt.setDefectTypeDescription(defectTypeDescription);
        defectTypeDAO.save(dt);
        
        return dt.getDefectTypeId();
    }
    
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void modifyDefectType(int defectTypeID, String defectTypeName, String defectTypeDescription){
              
        DefectType dt = defectTypeDAO.findById(defectTypeID);
        if (dt == null){
            throw new BizException("Cannot modify non-existing defect type");
        }
        dt.setDefectTypeName(defectTypeName);
        dt.setDefectTypeDescription(defectTypeDescription);
        defectTypeDAO.update(dt);
    }
    
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void deleteDefectType(int defectTypeID){
        
        DefectType dt = defectTypeDAO.findById(defectTypeID);
        if (dt == null){
            throw new BizException("Cannot delete non-existing defect type");
        }
        //TODO: Checar los casos en que ya se hayan registrado defectos de ese tipo
        //if(NO HAY DEFECTOS DE ESTE TIPO) {
        //    throw new BizException("Cannot delete non-empty defect type");
        //}
        defectTypeDAO.delete(dt);
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Map<String, ?> getDefectType(int defectTypeID) {
        DefectType defectType = defectTypeDAO.findById(defectTypeID);
        if (defectType == null){
            return null;
        }
        Map<String, Object> dt = new HashMap<String, Object>();
        dt.put("defectTypeName", defectType.getDefectTypeName());
        dt.put("defectTypeDescription", defectType.getDefectTypeDescription());
        return dt;
    }
}
