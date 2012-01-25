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
import mx.itesm.gda.bm.biz.PhaseManagementBizOp;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.model.PhaseType;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.PhaseDAO;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author CDE-Marco
 */
@Scope("prototype")
@Component
public class PhaseManagementBizOpImpl extends AbstractBizOp implements PhaseManagementBizOp{

    @Autowired
    private ProjectDAO projectDAO;
    
    @Autowired
    private PhaseDAO phaseDAO;
    
    @Override
    public List<Map<String, ?>> retrieveAllPhases() {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();

        List<Phase> phases;
        phases = phaseDAO.getAll();

        for (Phase phase : phases) {
            Map<String, Object> p = new HashMap<String, Object>();
            p.put("phaseID", phase.getPhaseId());
            p.put("phaseName", phase.getPhaseName());
            p.put("phaseDesc", phase.getPhaseDescription());
            p.put("phaseType", phase.getType().name());
            p.put("projectOrder", phase.getProjectOrder());
            ret.add(p);
        }

        return ret;
    }

    @Override
    public List<Map<String, ?>> retrieveProjectPhases(int projectId) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Project project = projectDAO.findById(projectId);
        
        List<Phase> phases = project.getPhases();
        Phase [] phasesArray = new Phase[phases.size()];
        
        for(int i = 0; i < phases.size(); i++){
            phasesArray[phases.get(i).getProjectOrder() - 1] = phases.get(i);
        }
        
        for(int i = 0; i < phasesArray.length; i++){
            Map<String, Object> p = new HashMap<String, Object>();
            p.put("phaseID", phasesArray[i].getPhaseId());
            p.put("phaseName", phasesArray[i].getPhaseName());
            p.put("phaseDesc", phasesArray[i].getPhaseDescription());
            p.put("phaseType", phasesArray[i].getType().name());
            p.put("projectOrder", phasesArray[i].getProjectOrder());
            ret.add(p);
        }
        
        return ret;
    }

    @Override
    public int createPhase(String phaseName, String phaseDescription, int projectID, int projectOrder, String phaseType) {
        
        Project p = projectDAO.findById(projectID);
        Phase ph = new Phase();
        ph.setPhaseName(phaseName);
        ph.setPhaseDescription(phaseDescription);
        ph.setProject(p);
        ph.setProjectOrder(projectOrder);
        ph.setType(PhaseType.valueOf(phaseType));
        phaseDAO.save(ph);
        
        return ph.getPhaseId();
    }

    @Override
    public void modifyPhase(int phaseID, String phaseName, String phaseDescription, int projectOrder, String phaseType) {
        Phase phase = phaseDAO.findById(phaseID);
        
        if(phaseName != null && !phaseName.equals("")){
            phase.setPhaseName(phaseName);
        }
        
        if(phaseDescription != null && !phaseDescription.equals("")){
            phase.setPhaseDescription(phaseDescription);
        }
        
        if(phaseType != null && !phaseType.equals("")){
            phase.setType(PhaseType.valueOf(phaseType));
        }
        
        if(projectOrder > 0){
            phase.setProjectOrder(projectOrder);
        }
    }

    @Override
    public void deletePhase(int phaseID) {
        Phase p = phaseDAO.findById(phaseID);

        if (p == null){
            throw new BizException("Cannot delete non-existing phase");
        }

        if(!p.getTasks().isEmpty()){
            throw new BizException("Cannot delete non-empty phase");
        }

        phaseDAO.delete(p);
    }

    @Override
    public Map<String, ?> getPhase(int phaseId) {
        Phase phase = phaseDAO.findById(phaseId);
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("phaseID", phase.getPhaseId());
        p.put("phaseName", phase.getPhaseName());
        p.put("phaseDesc", phase.getPhaseDescription());
        p.put("phaseType", phase.getType().name());
        p.put("projectOrder", phase.getProjectOrder());
        
        return p;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<String> getTypes() {
        List<String> phaseTypes = new ArrayList<String>() {};
        for (PhaseType phaseType : PhaseType.values()){
            phaseTypes.add(phaseType.name());
        }
        return phaseTypes;
    }

    @Override
    public void modifyOrder(int projectID, int order) {
        Project project = projectDAO.findById(projectID);
        List<Phase> phases = project.getPhases();
        
        for (Phase phase : phases){ 
            int actualOrder = phase.getProjectOrder();
            if(actualOrder >= order){
                phase.setProjectOrder(actualOrder + 1);
            }
        }
    }
    
}
