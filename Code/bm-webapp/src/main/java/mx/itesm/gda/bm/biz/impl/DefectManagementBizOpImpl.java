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
import mx.itesm.gda.bm.biz.DefectTypeManagementBizOp;
import mx.itesm.gda.bm.biz.PhaseManagementBizOp;
import mx.itesm.gda.bm.biz.ProjectManagementBizOp;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.biz.UserManagementBizOp;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectAge;
import mx.itesm.gda.bm.model.DefectComment;
import mx.itesm.gda.bm.model.DefectSource;
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
    @Autowired
    private ProjectManagementBizOp projMgr;
    @Autowired
    private PhaseManagementBizOp phaseMgr;
    @Autowired
    private TaskManagementBizOp taskMgr;
    @Autowired
    private DefectTypeManagementBizOp defectTypeMgr;
    @Autowired
    private UserManagementBizOp userMgr;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> retrieveAllDefects() {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();

        List<Defect> defects;
        defects = defectDAO.getAll();

        for (Defect defect : defects) {
            Map<String, Object> d = new HashMap<String, Object>();
            d.put("defectID", defect.getDefectId());
            d.put("defectName", defect.getDefectName());
            d.put("defectDesc", defect.getDefectDescription());
            d.put("project", projMgr.getProject(defect.getProject().getProjectId()));
            d.put("detectionPhase", phaseMgr.getPhase(defect.getDetectionPhase().getPhaseId()));
            d.put("detectionTask", taskMgr.getTask(defect.getDetectionTask().getTaskId()));
            if (defect.getInyectionPhase() != null) {
                d.put("inyectionPhase", phaseMgr.getPhase(defect.getInyectionPhase().getPhaseId()));
            } else {
                d.put("inyectionPhase", "");
            }
            if (defect.getInyectionTask() != null) {
                d.put("inyectionTask", taskMgr.getTask(defect.getInyectionTask().getTaskId()));
            } else {
                d.put("inyectionTask", "");
            }
            if (defect.getRemotionPhase() != null) {
                d.put("remotionPhase", phaseMgr.getPhase(defect.getRemotionPhase().getPhaseId()));
            } else {
                d.put("remotionPhase", "");
            }
            if (defect.getRemotionTask() != null) {
                d.put("remotionTask", taskMgr.getTask(defect.getRemotionTask().getTaskId()));
            } else {
                d.put("remotionTask", "");
            }
            if (defect.getDefectTrigger() != null) {
                d.put("defectTrigger", defect.getDefectTrigger());
            } else {
                d.put("defectTrigger", "");
            }
            if (defect.getImpact() != null) {
                d.put("impact", defect.getImpact());
            } else {
                d.put("impact", "");
            }
            if (defect.getDefectType() != null) {
                d.put("defectType", defectTypeMgr.getDefectType(defect.getDefectType().getDefectTypeId()));
            } else {
                d.put("defectType", "");
            }
            if (defect.getQualifier() != null) {
                d.put("qualifier", defect.getQualifier());
            } else {
                d.put("qualifier", "");
            }
            if (defect.getAge() != null) {
                d.put("age", defect.getAge());
            } else {
                d.put("age", "");
            }
            if (defect.getSource() != null) {
                d.put("source", defect.getSource());
            } else {
                d.put("source", "");
            }
            if (defect.getReference() != null) {
                d.put("reference", defect.getReference());
            } else {
                d.put("reference", "");
            }
            d.put("investedHours", defect.getInvestedHours());
            if (defect.getAssignedUser() != null) {
                d.put("assignedUser", userMgr.getUser(defect.getAssignedUser().getUserName()));
            } else {
                d.put("assignedUser", "");
            }
            d.put("openDate", defect.getOpenDate());
            if (defect.getCloseDate() != null) {
                d.put("closeDate", defect.getCloseDate());
            } else {
                d.put("closeDate", "");
            }
            d.put("defectState", defect.getDefectState().name());
            d.put("commentsNumber", defect.getDefectComments().size());
            ret.add(d);
        }

        return ret;
    }

    @Override
    public List<Map<String, ?>> retrieveProjectDefects(int projectId) {
        List<Map<String, ?>> ret = new ArrayList<Map<String, ?>>();
        Project project = projectDAO.findById(projectId);

        for (Defect defect : project.getDefects()) {
            Map<String, Object> d = new HashMap<String, Object>();
            d.put("defectID", defect.getDefectId());
            d.put("defectName", defect.getDefectName());
            d.put("defectDesc", defect.getDefectDescription());
            d.put("project", projMgr.getProject(projectId));
            d.put("detectionPhase", phaseMgr.getPhase(defect.getDetectionPhase().getPhaseId()));
            d.put("detectionTask", taskMgr.getTask(defect.getDetectionTask().getTaskId()));
            if (defect.getInyectionPhase() != null) {
                d.put("inyectionPhase", phaseMgr.getPhase(defect.getInyectionPhase().getPhaseId()));
            } else {
                d.put("inyectionPhase", "");
            }
            if (defect.getInyectionTask() != null) {
                d.put("inyectionTask", taskMgr.getTask(defect.getInyectionTask().getTaskId()));
            } else {
                d.put("inyectionTask", "");
            }
            if (defect.getRemotionPhase() != null) {
                d.put("remotionPhase", phaseMgr.getPhase(defect.getRemotionPhase().getPhaseId()));
            } else {
                d.put("remotionPhase", "");
            }
            if (defect.getRemotionTask() != null) {
                d.put("remotionTask", taskMgr.getTask(defect.getRemotionTask().getTaskId()));
            } else {
                d.put("remotionTask", "");
            }
            if (defect.getDefectTrigger() != null) {
                d.put("defectTrigger", defect.getDefectTrigger());
            } else {
                d.put("defectTrigger", "");
            }
            if (defect.getImpact() != null) {
                d.put("impact", defect.getImpact());
            } else {
                d.put("impact", "");
            }
            if (defect.getDefectType() != null) {
                d.put("defectType", defectTypeMgr.getDefectType(defect.getDefectType().getDefectTypeId()));
            } else {
                d.put("defectType", "");
            }
            if (defect.getQualifier() != null) {
                d.put("qualifier", defect.getQualifier());
            } else {
                d.put("qualifier", "");
            }
            if (defect.getAge() != null) {
                d.put("age", defect.getAge().name());
            } else {
                d.put("age", "");
            }
            if (defect.getSource() != null) {
                d.put("source", defect.getSource().name());
            } else {
                d.put("source", "");
            }
            if (defect.getReference() != null) {
                d.put("reference", defect.getReference());
            } else {
                d.put("reference", "");
            }
            d.put("investedHours", defect.getInvestedHours());
            if (defect.getAssignedUser() != null) {
                d.put("assignedUser", userMgr.getUser(defect.getAssignedUser().getUserName()));
            } else {
                d.put("assignedUser", "");
            }
            d.put("openDate", defect.getOpenDate());
            if (defect.getCloseDate() != null) {
                d.put("closeDate", defect.getCloseDate());
            } else {
                d.put("closeDate", "");
            }
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
    public int modifyDefect(int defectID, String defectName, String defectDescription,
            int detectionPhaseID, int detectionTaskID, int inyectionPhaseID,
            int inyectionTaskID, int remotionPhaseID, int remotionTaskID, 
            String defectTrigger, String impact, int defectTypeID, String qualifier,
            String age, String source, int referenceID, int investedHours,
            String assignedUser, String status) {

        Defect defect = defectDAO.findById(defectID);
        Phase detectionPhase = phaseDAO.findById(detectionPhaseID);
        Task detectionTask = taskDAO.findById(detectionTaskID);

        //No se checan porque el controlador no permite que sean nulos
        defect.setDefectName(defectName);
        defect.setDefectDescription(defectDescription);
        defect.setDetectionPhase(detectionPhase);
        defect.setDetectionTask(detectionTask);

        if (DefectState.valueOf(status) != DefectState.FIXED && DefectState.valueOf(status) != DefectState.CANCELED) {
            defect.setInvestedHours(defect.getInvestedHours() + investedHours);
        }

        if (DefectState.valueOf(status) == DefectState.SUBMITTED && investedHours > 0) {
            defect.setDefectState(DefectState.ACCEPTED);
        }

        if (defect.getDefectState() == DefectState.ACCEPTED && DefectState.valueOf(status) == DefectState.FIXED && defect.getCloseDate() == null) {
            defect.setDefectState(DefectState.FIXED);
            defect.setCloseDate(new Date());
        }

        if ((defect.getDefectState() == DefectState.SUBMITTED || defect.getDefectState() == DefectState.ACCEPTED) && DefectState.valueOf(status) == DefectState.CANCELED) {
            defect.setDefectState(DefectState.CANCELED);
            defect.setCloseDate(new Date());
        }

        //Se necesitan checar que existan ya que pueden ser nulos
        Phase inyectionPhase = phaseDAO.findById(inyectionPhaseID);
        if (inyectionPhase != null) {
            defect.setInyectionPhase(inyectionPhase);
        }
        Task inyectionTask = taskDAO.findById(inyectionTaskID);
        if (inyectionTask != null) {
            defect.setInyectionTask(inyectionTask);
        }
        Phase remotionPhase = phaseDAO.findById(remotionPhaseID);
        if (remotionPhase != null) {
            defect.setRemotionPhase(remotionPhase);
        }
        Task remotionTask = taskDAO.findById(remotionTaskID);
        if (remotionTask != null) {
            defect.setRemotionTask(remotionTask);
        }
        if (defectTrigger != null && !defectTrigger.equals("")) {
            defect.setDefectTrigger(defectTrigger);
        }
        if (impact != null && !impact.equals("")) {
            defect.setImpact(impact);
        }
        DefectType defectType = defectTypeDAO.findById(defectTypeID);
        if (defectType != null) {
            defect.setDefectType(defectType);
        }
        if (qualifier != null && !qualifier.equals("")) {
            defect.setQualifier(qualifier);
        }
        if (age != null && !age.equals("")) {
            if(DefectAge.valueOf(age) == DefectAge.BASE){
                defect.setAge(DefectAge.BASE);
            }
            else if(DefectAge.valueOf(age) == DefectAge.NEWDEFECT){
                defect.setAge(DefectAge.NEWDEFECT);
            }
            else if(DefectAge.valueOf(age) == DefectAge.REFIXED){
                defect.setAge(DefectAge.REFIXED);
            }
            else if(DefectAge.valueOf(age) == DefectAge.REWRITTEN){
                defect.setAge(DefectAge.REWRITTEN);
            }
        }
        if (source != null && !source.equals("")) {
            if(DefectSource.valueOf(source) == DefectSource.INHOUSE){
                defect.setSource(DefectSource.INHOUSE);
            }
            else if(DefectSource.valueOf(source) == DefectSource.LIBRARY){
                defect.setSource(DefectSource.LIBRARY);
            }
            else if(DefectSource.valueOf(source) == DefectSource.OUTSOURCED){
                defect.setSource(DefectSource.OUTSOURCED);
            }
            else if(DefectSource.valueOf(source) == DefectSource.PORTED){
                defect.setSource(DefectSource.PORTED);
            }
        }
        if (referenceID > 0 && defectID != referenceID) {
            defect.setReference(referenceID);
        }
        User user = userDAO.findByUserName(assignedUser);
        if (user != null) {
            defect.setAssignedUser(user);
        }
        
        return defect.getDefectId();
    }

    @Override
    public void deleteDefect(int defectID) {
        Defect d = defectDAO.findById(defectID);

        if (d == null) {
            throw new BizException("Cannot delete non-existing defect");
        }

        if (d.getDefectState() != DefectState.SUBMITTED || !d.getDefectComments().isEmpty()) {
            throw new BizException("Cannot delete non-empty defect");
        }

        defectDAO.delete(d);
    }

    @Override
    public Map<String, ?> getDefect(int defectID) {

        Defect defect = defectDAO.findById(defectID);
        Map<String, Object> d = new HashMap<String, Object>();
        d.put("defectID", defect.getDefectId());
        d.put("defectName", defect.getDefectName());
        d.put("defectDesc", defect.getDefectDescription());
        d.put("project", projMgr.getProject(defect.getProject().getProjectId()));
        d.put("detectionPhase", phaseMgr.getPhase(defect.getDetectionPhase().getPhaseId()));
        d.put("detectionTask", taskMgr.getTask(defect.getDetectionTask().getTaskId()));
        if (defect.getInyectionPhase() != null) {
            d.put("inyectionPhase", phaseMgr.getPhase(defect.getInyectionPhase().getPhaseId()));
        } else {
            d.put("inyectionPhase", "");
        }
        if (defect.getInyectionTask() != null) {
            d.put("inyectionTask", taskMgr.getTask(defect.getInyectionTask().getTaskId()));
        } else {
            d.put("inyectionTask", "");
        }
        if (defect.getRemotionPhase() != null) {
            d.put("remotionPhase", phaseMgr.getPhase(defect.getRemotionPhase().getPhaseId()));
        } else {
            d.put("remotionPhase", "");
        }
        if (defect.getRemotionTask() != null) {
                d.put("remotionTask", taskMgr.getTask(defect.getRemotionTask().getTaskId()));
            } else {
                d.put("remotionTask", "");
            }
        if (defect.getDefectTrigger() != null) {
            d.put("defectTrigger", defect.getDefectTrigger());
        } else {
            d.put("defectTrigger", "");
        }
        if (defect.getImpact() != null) {
            d.put("impact", defect.getImpact());
        } else {
            d.put("impact", "");
        }
        if (defect.getDefectType() != null) {
            d.put("defectType", defectTypeMgr.getDefectType(defect.getDefectType().getDefectTypeId()));
        } else {
            d.put("defectType", "");
        }
        if (defect.getQualifier() != null) {
            d.put("qualifier", defect.getQualifier());
        } else {
            d.put("qualifier", "");
        }
        if (defect.getAge() != null) {
            d.put("age", defect.getAge().name());
        } else {
            d.put("age", "");
        }
        if (defect.getSource() != null) {
            d.put("source", defect.getSource().name());
        } else {
            d.put("source", "");
        }
        if (defect.getReference() != null) {
            d.put("reference", defect.getReference());
        } else {
            d.put("reference", "");
        }
        d.put("investedHours", defect.getInvestedHours());
        if (defect.getAssignedUser() != null) {
            d.put("assignedUser", userMgr.getUser(defect.getAssignedUser().getUserName()));
        } else {
            d.put("assignedUser", "");
        }
        d.put("openDate", defect.getOpenDate());
        if (defect.getCloseDate() != null) {
            d.put("closeDate", defect.getCloseDate());
        } else {
            d.put("closeDate", "");
        }
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

        for (DefectComment comment : defect.getDefectComments()) {
            Map<String, Object> c = new HashMap<String, Object>();
            c.put("author", comment.getAuthor().getFullName());
            c.put("date", comment.getCommentDate());
            c.put("text", comment.getCommentText());
            ret.add(c);
        }

        return ret;
    }
}
