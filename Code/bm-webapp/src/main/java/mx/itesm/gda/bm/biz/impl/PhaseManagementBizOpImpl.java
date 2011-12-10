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
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.PhaseDAO;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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
            p.put("projectOrder", phase.getProjectOrder());
            ret.add(p);
        }

        return ret;
    }

    @Override
    public List<Map<String, ?>> retrieveProjectPhases(int projectId) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Project project = projectDAO.findById(projectId);

        for (Phase phase : project.getPhases()) {
            Map<String, Object> p = new HashMap<String, Object>();
            p.put("phaseID", phase.getPhaseId());
            p.put("phaseName", phase.getPhaseName());
            p.put("phaseDesc", phase.getPhaseDescription());
            p.put("projectOrder", phase.getProjectOrder());
            ret.add(p);
        }
        return ret;
    }

    @Override
    public int createPhase(String phaseName, String phaseDescription, int projectID, int projectOrder) {
        
        Project p = projectDAO.findById(projectID);
        Phase ph = new Phase();
        ph.setPhaseName(phaseName);
        ph.setPhaseDescription(phaseDescription);
        ph.setProject(p);
        ph.setProjectOrder(projectOrder);
        phaseDAO.save(ph);
        
        return ph.getPhaseId();
    }

    @Override
    public void modifyPhase(int phaseID, String phaseName, String phaseDescription, int projectOrder) {
        Phase phase = phaseDAO.findById(phaseID);
        
        if(phaseName != null && !phaseName.equals("")){
            phase.setPhaseName(phaseName);
        }
        
        if(phaseDescription != null && !phaseDescription.equals("")){
            phase.setPhaseDescription(phaseDescription);
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
        p.put("projectOrder", phase.getProjectOrder());
        
        return p;
    }
    
}
