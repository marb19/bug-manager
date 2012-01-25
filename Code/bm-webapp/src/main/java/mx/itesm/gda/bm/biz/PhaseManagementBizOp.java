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
public interface PhaseManagementBizOp extends BizOp{
    
    public List<Map<String, ?>> retrieveAllPhases();
    
    public List<Map<String, ?>> retrieveProjectPhases(int projectId);
    
    public int createPhase(String phaseName, String phaseDescription, int projectID, int projectOrder, String phaseType);
    
    public void modifyPhase(int phaseID, String phaseName, String phaseDescription, int projectOrder, String phaseType);
    
    public void deletePhase(int phaseID);
    
    public Map<String, ?> getPhase(int phaseId);
    
    public List<String> getTypes();
    
    public void modifyOrder(int projectID, int order);
}