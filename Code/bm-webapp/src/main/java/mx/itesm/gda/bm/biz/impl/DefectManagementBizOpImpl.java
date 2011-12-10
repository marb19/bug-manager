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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.BizException;
import mx.itesm.gda.bm.biz.DefectManagementBizOp;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectComment;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.DefectType;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.DefectCommentDAO;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import mx.itesm.gda.bm.model.dao.PhaseDAO;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;

/**
 *
 * @author CDE-Marco
 */
@Scope("prototype")
@Component
public class DefectManagementBizOpImpl extends AbstractBizOp implements
        DefectManagementBizOp {

    @Autowired
    private ProjectDAO projectDAO;
    
    @Autowired
    private DefectDAO defectDAO;

    @Autowired
    private DefectTypeDAO defectTypeDAO;
    
    @Autowired
    private PhaseDAO phaseDAO;
    
    @Autowired
    private TaskDAO taskDAO;
    
    @Autowired
    private UserDAO userDAO;
    
    @Autowired
    private DefectCommentDAO defectCommentDAO;
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> retrieveAllDefects() {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();

        List<Defect> defects;
        defects = defectDAO.getAll();

        for (Defect defect : defects) {
            Map<String, Object> d = new HashMap<String, Object>();
            d.put("defectId", defect.getDefectId());
            d.put("defectName", defect.getDefectName());
            d.put("defectDesc", defect.getDefectDescription());
            d.put("project", mapProject(defect.getProject()));
            d.put("detectionPhase", mapPhase(defect.getDetectionPhase()));
            d.put("detectionTask", mapTask(defect.getDetectionTask()));
            d.put("inyectionPhase", mapPhase(defect.getInyectionPhase()));
            d.put("inyectionTask", mapTask(defect.getInyectionTask()));
            d.put("remotionPhase", mapPhase(defect.getRemotionPhase()));
            d.put("defectTrigger", defect.getDefectTrigger());
            d.put("impact", defect.getImpact());
            d.put("defectType", mapDefectType(defect.getDefectType()));
            d.put("qualifier", defect.getQualifier());
            d.put("age", defect.getAge());
            d.put("source", defect.getSource());
            d.put("reference", defect.getReference());
            d.put("investedHours", defect.getInvestedHours());
            d.put("assignedUser", mapUser(defect.getAssignedUser()));
            d.put("openDate", defect.getOpenDate());
            d.put("closeDate", defect.getCloseDate());
            d.put("defectState", defect.getDefectState().name());
            d.put("commentsNumber", defect.getDefectComments().size());
            ret.add(d);
        }

        return ret;
    }

    private Map<String, ?> mapUser(User user) {
        Map<String, Object> u = new HashMap<String, Object>();
        u.put("userName", user.getUserName());
        u.put("fullName", user.getFullName());
        u.put("email", user.getEmail());
        return u;
    }

    private Map<String, ?> mapProject(Project project) {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("projectId", project.getProjectId());
        p.put("projectName", project.getProjectName());
        p.put("projectDescription", project.getProjectDescription());
        p.put("projectDueDate", project.getProjectDueDate());
        p.put("projectPlannedDate", project.getProjectPlannedDate());
        p.put("empty", project.getTasks().isEmpty());
        return p;
    }

    private Map<String, ?> mapPhase(Phase phase) {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("phaseId", phase.getPhaseId());
        p.put("phaseName", phase.getPhaseName());
        p.put("projectId", phase.getProject().getProjectId());
        p.put("projectOrder", phase.getProjectOrder());
        return p;
    }

    private Map<String, ?> mapTask(Task task) {
        Map<String, Object> t = new HashMap<String, Object>();
        t.put("taskId", task.getTaskId());
        t.put("taskName", task.getTaskName());
        t.put("taskDescription", task.getTaskDescription());
        t.put("projectId", task.getProject().getProjectId());
        t.put("assignedUser", task.getAssignedUser().getUserName());
        t.put("startDate", task.getStartDate());
        t.put("endDate", task.getEndDate());
        t.put("completionDate", task.getCompletionDate());
        t.put("estimatedHours", task.getEstimatedHours());
        t.put("investedHourS", task.getInvestedHours());
        t.put("remainingHours", task.getInvestedHours());
        t.put("taskState", task.getTaskState());
        t.put("taskComments", task.getTaskComments());
        return t;
    }

    private Map<String, ?> mapDefectType(DefectType defectType) {
        Map<String, Object> dt = new HashMap<String, Object>();
        dt.put("defectTypeId", defectType.getDefectTypeId());
        dt.put("defectTypeName", defectType.getDefectTypeName());
        dt.put("defectTypeDescription", defectType.getDefectTypeDescription());
        return dt;
    }

    @Override
    public List<Map<String, ?>> retrieveProjectDefects(int projectId) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Project project = projectDAO.findById(projectId);

        for (Defect defect : project.getDefects()) {
            Map<String, Object> d = new HashMap<String, Object>();
            d.put("defectId", defect.getDefectId());
            d.put("defectName", defect.getDefectName());
            d.put("defectDesc", defect.getDefectDescription());
            d.put("project", mapProject(defect.getProject()));
            d.put("detectionPhase", mapPhase(defect.getDetectionPhase()));
            d.put("detectionTask", mapTask(defect.getDetectionTask()));
            d.put("inyectionPhase", mapPhase(defect.getInyectionPhase()));
            d.put("inyectionTask", mapTask(defect.getInyectionTask()));
            d.put("remotionPhase", mapPhase(defect.getRemotionPhase()));
            d.put("defectTrigger", defect.getDefectTrigger());
            d.put("impact", defect.getImpact());
            d.put("defectType", mapDefectType(defect.getDefectType()));
            d.put("qualifier", defect.getQualifier());
            d.put("age", defect.getAge());
            d.put("source", defect.getSource());
            d.put("reference", defect.getReference());
            d.put("investedHours", defect.getInvestedHours());
            d.put("assignedUser", mapUser(defect.getAssignedUser()));
            d.put("openDate", defect.getOpenDate());
            d.put("closeDate", defect.getCloseDate());
            d.put("defectState", defect.getDefectState().name());
            d.put("commentsNumber", defect.getDefectComments().size());
            ret.add(d);
        }
        return ret;
    }

    @Override
    public int createDefect(String defectName, String defectDescription, int projectId, int detectionPhaseId, int detectionTaskId) {
        
        Project p = projectDAO.findById(projectId);
        Phase dp = phaseDAO.findById(detectionPhaseId);
        Task dTask = taskDAO.findById(detectionTaskId);
     
        Defect d = new Defect();
        d.setDefectName(defectName);
        d.setDefectDescription(defectDescription);
        d.setProject(p);
        d.setDetectionPhase(dp);
        d.setDetectionTask(dTask);
        d.setDefectState(DefectState.SUBMITTED);
        d.setOpenDate(new Date());
        defectDAO.save(d);
        
        return d.getDefectId();
    }

    @Override
    public void modifyDefect(int defectID, String defectName, String defectDescription, 
                             int detectionPhaseID, int detectionTaskID, int inyectionPhaseID,
                             int inyectionTaskID, int remotionPhaseID, String defectTrigger,
                             String impact, int defectTypeID, String qualifier,
                             String age, String source, String reference, int investedHours,
                             String assignedUser, Date openDate, String status) {
        
        Defect defect = defectDAO.findById(defectID);
        Phase detectionPhase = phaseDAO.findById(detectionPhaseID);
        Task detectionTask = taskDAO.findById(detectionTaskID);
        
        //No se checan porque el controlador no permite que sean nulos
        defect.setDefectName(defectName);
        defect.setDefectDescription(defectDescription);
        defect.setDetectionPhase(detectionPhase);
        defect.setDetectionTask(detectionTask);
        
        if(DefectState.valueOf(status) != DefectState.FIXED && DefectState.valueOf(status) != DefectState.CANCELED){
            defect.setInvestedHours(defect.getInvestedHours() + investedHours);
        }
        
        if(DefectState.valueOf(status) == DefectState.SUBMITTED && investedHours > 0){
            defect.setDefectState(DefectState.ACCEPTED);
        }
        
        if(defect.getDefectState() == DefectState.ACCEPTED && DefectState.valueOf(status) == DefectState.FIXED){
            defect.setDefectState(DefectState.FIXED);
            defect.setCloseDate(new Date());
        }
        
        if(defect.getInvestedHours() == 0 && defect.getDefectState() == DefectState.SUBMITTED && DefectState.valueOf(status) == DefectState.CANCELED){
            defect.setDefectState(DefectState.CANCELED);
            defect.setCloseDate(new Date());
        }
        
        //Se necesitan checar que existan ya que pueden ser nulos
        Phase inyectionPhase = phaseDAO.findById(inyectionPhaseID);
        if(inyectionPhase != null){
            defect.setInyectionPhase(inyectionPhase);
        }
        Task inyectionTask = taskDAO.findById(inyectionTaskID);
        if(inyectionTask != null){
            defect.setInyectionTask(inyectionTask);
        }
        Phase remotionPhase = phaseDAO.findById(remotionPhaseID);
        if(remotionPhase != null){
            defect.setRemotionPhase(remotionPhase);
        }
        if(defectTrigger != null && !defectTrigger.equals("")){
            defect.setDefectTrigger(defectTrigger);
        }
        if(impact != null && !impact.equals("")){
            defect.setImpact(impact);
        }
        DefectType defectType = defectTypeDAO.findById(defectTypeID);
        if(defectType != null){
            defect.setDefectType(defectType);
        }
        if(qualifier != null && !qualifier.equals("")){
            defect.setQualifier(qualifier);
        }
        if(age != null && !age.equals("")){
            defect.setAge(age);
        }
        if(source != null && !source.equals("")){
            defect.setSource(source);
        }
        if(reference != null && !reference.equals("")){
            defect.setReference(reference);
        }
        User user = userDAO.findByUserName(assignedUser);
        if(user != null){
            defect.setAssignedUser(user);
        }
    }

    @Override
    public void deleteDefect(int defectID) {
        Defect d = defectDAO.findById(defectID);

        if (d == null){
            throw new BizException("Cannot delete non-existing defect");
        }

        if(d.getDefectState() != DefectState.SUBMITTED || !d.getDefectComments().isEmpty()){
            throw new BizException("Cannot delete non-empty defect");
        }

        defectDAO.delete(d);
    }

    @Override
    public Map<String, ?> getDefect(int defectID) {

        Defect defect = defectDAO.findById(defectID);
        Map<String, Object> d = new HashMap<String, Object>();
        d.put("defectId", defect.getDefectId());
        d.put("defectName", defect.getDefectName());
        d.put("defectDesc", defect.getDefectDescription());
        d.put("project", mapProject(defect.getProject()));
        d.put("detectionPhase", mapPhase(defect.getDetectionPhase()));
        d.put("detectionTask", mapTask(defect.getDetectionTask()));
        d.put("inyectionPhase", mapPhase(defect.getInyectionPhase()));
        d.put("inyectionTask", mapTask(defect.getInyectionTask()));
        d.put("remotionPhase", mapPhase(defect.getRemotionPhase()));
        d.put("defectTrigger", defect.getDefectTrigger());
        d.put("impact", defect.getImpact());
        d.put("defectType", mapDefectType(defect.getDefectType()));
        d.put("qualifier", defect.getQualifier());
        d.put("age", defect.getAge());
        d.put("source", defect.getSource());
        d.put("reference", defect.getReference());
        d.put("investedHours", defect.getInvestedHours());
        d.put("assignedUser", mapUser(defect.getAssignedUser()));
        d.put("openDate", defect.getOpenDate());
        d.put("closeDate", defect.getCloseDate());
        d.put("defectState", defect.getDefectState().name());
        d.put("commentsNumber", defect.getDefectComments().size());

        return d;
    }

    @Override
    public int addComment(int defectID, String commentAuthor, String newComment) {
        Defect d = defectDAO.findById(defectID);
        DefectComment newDefectComment = new DefectComment();
        User author = userDAO.findByUserName(commentAuthor);
        newDefectComment.setAuthor(author);
        newDefectComment.setCommentDate(new Date());
        newDefectComment.setCommentText(newComment);
        newDefectComment.setDefect(d);
        defectCommentDAO.save(newDefectComment);
        return newDefectComment.getDefectCommentId();
    }

    @Override
    public List<Map<String, ?>> retrieveComments(int defectId) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Defect defect = defectDAO.findById(defectId);

        for(DefectComment comment : defect.getDefectComments()) {
            Map<String, Object> c = new HashMap<String, Object>();
            c.put("author", comment.getAuthor().getFullName());
            c.put("date", comment.getCommentDate());
            c.put("text", comment.getCommentText());
            ret.add(c);
        }

        return ret;
    }
}
