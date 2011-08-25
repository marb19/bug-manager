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
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.DefectType;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import org.springframework.transaction.annotation.Propagation;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.DefectType;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import org.springframework.transaction.annotation.Transactional;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.DefectType;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import org.springframework.transaction.annotation.Propagation;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.DefectType;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;

/**
 *
 * @author CDE-Marco
 */
public class DefectTypeManagementBizOpImpl extends AbstractBizOp implements
        DefectTypeManagementBizOp{
    
    @Autowired
    private DefectTypeDAO defectTypeDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @Override
    public List<Map<String, ?>> retrieveDefectTypes(String userName){
    
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        User user = userDAO.findByUserName(userName);

        if(user.isAdministrator()) {
            List<DefectType> defectTypes;
            defectTypes = defectTypeDAO.getAll();
            
            for(DefectType defectType : defectTypes) {
                Map<String, Object> dt = new HashMap<String, Object>();
                dt.put("defectTypeId", defectType.getDefectTypeId());
                dt.put("defectTypeName", defectType.getDefectTypeName());
                dt.put("defectTypeDescription", defectType.getDefectTypeDescription());
                ret.add(dt);
            }
        }

        return ret;
    }
    
    @Override
    public int createDefectType(String defectTypeName, String defectTypeDescription){
        
        DefectType dt = new DefectType();
        dt.setDefectTypeName(defectTypeName);
        dt.setDefectTypeDescription(defectTypeDescription);
        defectTypeDAO.save(dt);
        
        return dt.getDefectTypeId();
    }
    
    @Override
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
