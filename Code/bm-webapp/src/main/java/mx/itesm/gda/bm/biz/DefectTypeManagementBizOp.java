/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itesm.gda.bm.biz;

import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.model.Project;

/**
 *
 * @author CDE-Marco
 */
public interface DefectTypeManagementBizOp extends BizOp{
    
    public List<Map<String, ?>> retrieveDefectTypes();
    
    public int createDefectType(String defectTypeName, String defectTypeDescription);
    
    public void modifyDefectType(int defectTypeID, String defectTypeName, String defectTypeDescription);
    
    public void deleteDefectType(int defectTypeID);
    
    public Map<String, ?> getDefectType(int defectTypeID);
    
}
