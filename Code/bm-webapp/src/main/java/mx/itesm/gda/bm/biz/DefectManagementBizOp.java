/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.itesm.gda.bm.biz;

import java.util.List;
import java.util.Map;

/**
 *
 * @author CDE-Marco
 */
public interface DefectManagementBizOp extends BizOp{
    
    public List<Map<String, ?>> retrieveAllDefects();
    
    public List<Map<String, ?>> retrieveProjectDefects(int projectId);
    
    public int createDefect(String defectName, String defectDescription, int projectID, int detectionPhaseID, int detectionTask);
    
    public int modifyDefect(int defectID, String defectName, String defectDescription, int detectionPhaseID, int detectionTaskID, int inyectionPhaseID, int inyectionTaskID, int remotionPhaseID,
            int remotionTaskID, String defectTrigger, String impact, int defectTypeID, String qualifier, String age, String source, int referenceID, int investedhours, String assignedUser, String status);
    
    public void deleteDefect(int defectID);
    
    public Map<String, ?> getDefect(int defectId);
    
    public int addComment(int defectID, String commentAuthor, String newComment);
    
    public List<Map<String, ?>> retrieveComments(int defectId);
}