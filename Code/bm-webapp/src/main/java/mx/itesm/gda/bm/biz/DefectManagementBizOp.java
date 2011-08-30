/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itesm.gda.bm.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author CDE-Marco
 */
public interface DefectManagementBizOp extends BizOp{
    
    public List<Map<String, ?>> retrieveAllDefects();
    
    public List<Map<String, ?>> retrieveProjectDefects(int projectID);
    
    public int createDefect(String defectTypeName, String defectName, String defectDescription, int projectID, int detectionPhaseID, int detectionTask);
    
    public void modifyDefect(int defectID, String defectName, String defectDescription, int detectionPhaseID, int detectionTaskID, int inyectionPhaseID, int inyectionTaskID, int remotionPhaseID,
            String defectTrigger, String impact, int defectTypeID, String qualifier, String age, String source, int investedhours, String assignedUser, Date openDate);
    
    public void deleteDefect(int defectID);
    
    public Map<String, ?> getDefect(int defectID);
    
    public int addComment(int defectID, String commentAuthor, String newComment);
    
}