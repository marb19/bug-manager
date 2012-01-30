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
        Project project = p.getProject();
        
        if (p == null){
            throw new BizException("Cannot delete non-existing phase");
        }

        if(!p.getTasks().isEmpty()){
            throw new BizException("Cannot delete non-empty phase");
        }
        
        List<Phase> phases = project.getPhases();
        
        for(Phase phase:phases){
            int actualOrder = phase.getProjectOrder();
            if(actualOrder > p.getProjectOrder()){
                phase.setProjectOrder(actualOrder - 1);
            }
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
    
    @Override
    public void autoCycle(int projectID, int cycleType){
        
        Project p = projectDAO.findById(projectID);
        
        if(p == null){
            throw new BizException("Proyecto inexistente");
        }
        
        if(p.getPhases().size() > 0){
            throw new BizException("Solo sirve para proyectos sin fases");
        }
        
        if(cycleType != 1 && cycleType != 2){
            throw new BizException("Ciclo de vida inexistente");
        }
        
        if(cycleType == 1){
            waterFallCycle(projectID);
        }
        
        if(cycleType == 2){
            iterativeCycle(projectID);
        }
    }
    
    public void waterFallCycle(int projectID){
        
        Project p = projectDAO.findById(projectID);
        
        //Requerimientos y revisión
        Phase req = new Phase();
        req.setPhaseName("Requerimientos");
        req.setPhaseDescription("Licitación y análisis de requerimientos");
        req.setProject(p);
        req.setProjectOrder(1);
        req.setType(PhaseType.REQUIREMENTS);
        phaseDAO.save(req);
        
        Phase reqr = new Phase();
        reqr.setPhaseName("Revisión de Requerimientos");
        reqr.setPhaseDescription("Revisión del documento de requerimientos");
        reqr.setProject(p);
        reqr.setProjectOrder(2);
        reqr.setType(PhaseType.REVIEW);
        phaseDAO.save(reqr);
        
        //Diseño y revisión
        Phase des = new Phase();
        des.setPhaseName("Diseño");
        des.setPhaseDescription("Diseño y arquitectura del sistema");
        des.setProject(p);
        des.setProjectOrder(3);
        des.setType(PhaseType.DESIGN);
        phaseDAO.save(des);
        
        Phase desr = new Phase();
        desr.setPhaseName("Revisión de Diseño");
        desr.setPhaseDescription("Revisión del documento de diseño");
        desr.setProject(p);
        desr.setProjectOrder(4);
        desr.setType(PhaseType.REVIEW);
        phaseDAO.save(desr);
        
        //Codificación y revisión
        Phase cod = new Phase();
        cod.setPhaseName("Codificación");
        cod.setPhaseDescription("Construcción del sistema");
        cod.setProject(p);
        cod.setProjectOrder(5);
        cod.setType(PhaseType.CODING);
        phaseDAO.save(cod);
        
        Phase codr = new Phase();
        codr.setPhaseName("Revisión de la Codificación");
        codr.setPhaseDescription("Revisión de la construcción del sistema");
        codr.setProject(p);
        codr.setProjectOrder(6);
        codr.setType(PhaseType.REVIEW);
        phaseDAO.save(codr);
        
        //Pruebas
        Phase test = new Phase();
        test.setPhaseName("Pruebas");
        test.setPhaseDescription("Pruebas del sistema");
        test.setProject(p);
        test.setProjectOrder(7);
        test.setType(PhaseType.TESTING);
        phaseDAO.save(test);
        
        //Mantenimiento
        Phase mant = new Phase();
        mant.setPhaseName("Mantenimiento");
        mant.setPhaseDescription("Mantenimiento del sistema");
        mant.setProject(p);
        mant.setProjectOrder(8);
        mant.setType(PhaseType.MAINTENANCE);
        phaseDAO.save(mant);
    }
            
    public void iterativeCycle(int projectID){
        
        Project p = projectDAO.findById(projectID);
        
        Phase req = new Phase();
        req.setPhaseName("Requerimientos");
        req.setPhaseDescription("Licitación y análisis de requerimientos");
        req.setProject(p);
        req.setProjectOrder(1);
        req.setType(PhaseType.REQUIREMENTS);
        phaseDAO.save(req);
        
        Phase reqr = new Phase();
        reqr.setPhaseName("Revisión de Requerimientos");
        reqr.setPhaseDescription("Revisión del documento de requerimientos");
        reqr.setProject(p);
        reqr.setProjectOrder(2);
        reqr.setType(PhaseType.REVIEW);
        phaseDAO.save(reqr);
        
        //Iteración I
        //Diseño y revisión
        Phase desI = new Phase();
        desI.setPhaseName("Diseño I");
        desI.setPhaseDescription("Diseño y arquitectura del sistema");
        desI.setProject(p);
        desI.setProjectOrder(3);
        desI.setType(PhaseType.DESIGN);
        phaseDAO.save(desI);
        
        Phase desrI = new Phase();
        desrI.setPhaseName("Revisión del Diseño I");
        desrI.setPhaseDescription("Revisión del documento de diseño");
        desrI.setProject(p);
        desrI.setProjectOrder(4);
        desrI.setType(PhaseType.REVIEW);
        phaseDAO.save(desrI);
        
        //Codificación y revisión
        Phase codI = new Phase();
        codI.setPhaseName("Codificación I");
        codI.setPhaseDescription("Construcción del sistema");
        codI.setProject(p);
        codI.setProjectOrder(5);
        codI.setType(PhaseType.CODING);
        phaseDAO.save(codI);
        
        Phase codrI = new Phase();
        codrI.setPhaseName("Revisión de la Codificación I");
        codrI.setPhaseDescription("Revisión de la construcción del sistema");
        codrI.setProject(p);
        codrI.setProjectOrder(6);
        codrI.setType(PhaseType.REVIEW);
        phaseDAO.save(codrI);
        
        //Pruebas
        Phase testI = new Phase();
        testI.setPhaseName("Pruebas I");
        testI.setPhaseDescription("Pruebas del sistema");
        testI.setProject(p);
        testI.setProjectOrder(7);
        testI.setType(PhaseType.TESTING);
        phaseDAO.save(testI);
        
        //Iteración II
        //Diseño y revisión
        Phase desII = new Phase();
        desII.setPhaseName("Diseño II");
        desII.setPhaseDescription("Diseño y arquitectura del sistema");
        desII.setProject(p);
        desII.setProjectOrder(8);
        desII.setType(PhaseType.DESIGN);
        phaseDAO.save(desII);
        
        Phase desrII = new Phase();
        desrII.setPhaseName("Revisión del Diseño II");
        desrII.setPhaseDescription("Revisión del documento de diseño");
        desrII.setProject(p);
        desrII.setProjectOrder(9);
        desrII.setType(PhaseType.REVIEW);
        phaseDAO.save(desrII);
        
        //Codificación y revisión
        Phase codII = new Phase();
        codII.setPhaseName("Codificación II");
        codII.setPhaseDescription("Construcción del sistema");
        codII.setProject(p);
        codII.setProjectOrder(10);
        codII.setType(PhaseType.CODING);
        phaseDAO.save(codII);
        
        Phase codrII = new Phase();
        codrII.setPhaseName("Revisión de la Codificación II");
        codrII.setPhaseDescription("Revisión de la construcción del sistema");
        codrII.setProject(p);
        codrII.setProjectOrder(11);
        codrII.setType(PhaseType.REVIEW);
        phaseDAO.save(codrII);
        
        //Pruebas
        Phase testII = new Phase();
        testII.setPhaseName("Pruebas II");
        testII.setPhaseDescription("Pruebas del sistema");
        testII.setProject(p);
        testII.setProjectOrder(12);
        testII.setType(PhaseType.TESTING);
        phaseDAO.save(testII);
        
        //Mantenimiento
        Phase mant = new Phase();
        mant.setPhaseName("Mantenimiento");
        mant.setPhaseDescription("Mantenimiento del sistema");
        mant.setProject(p);
        mant.setProjectOrder(13);
        mant.setType(PhaseType.MAINTENANCE);
        phaseDAO.save(mant);
    }
}
