/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectDAOImpl.java 193 2010-10-09 07:39:00Z zerocoolx@gmail.com $
 * Last Revised By   : $Author: zerocoolx@gmail.com $
 * Last Checked In   : $Date: 2010-10-09 02:39:00 -0500 (Sat, 09 Oct 2010) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Humberto Garcia
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.util.List;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.PhaseType;
import mx.itesm.gda.bm.model.TaskType;
/**
 *
 * @author $Author: zerocoolx@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Repository
public class DefectDAOImpl extends BaseItemDAOImpl<Defect> implements DefectDAO {

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> getAll() {
        @SuppressWarnings("unchecked")
        List<Defect> defects = (List<Defect>)getEntityManager().createQuery(
                "SELECT d FROM Defect d").getResultList();
        return defects;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Defect findById(int defect_id) {
        return getEntityManager().find(Defect.class, defect_id);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByDefectName(String defect_name) {
        @SuppressWarnings("unchecked")
        List<Defect> defects = (List<Defect>)getEntityManager().createQuery(
                "SELECT d FROM Defect d WHERE d.defectName LIKE :name").
                setParameter("name", "%" + defect_name + "%").getResultList();
        return defects;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByDetectionPhase(Integer phase_id){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.detectionPhase dp WHERE dp.phaseId = :phase_id "
                + "AND (d.defectState = :accepted OR d.defectState = :fixed)")
                .setParameter("phase_id", phase_id)
                .setParameter("accepted", DefectState.ACCEPTED)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByTypeAndUser(int defectType_id, String username){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.defectType dt JOIN d.assignedUser u WHERE "
                + "dt.defectTypeId = :defectType_id AND u.userName = :username "
                + "AND d.defectState = :fixed")
                .setParameter("defectType_id", defectType_id)
                .setParameter("username", username)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByTypeAndProject(int defectType_id, int project_id){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.defectType dt JOIN d.project p WHERE "
                + "dt.defectTypeId = :defectType_id AND p.projectId = :project_id "
                + "AND d.defectState = :fixed")
                .setParameter("defectType_id", defectType_id)
                .setParameter("project_id", project_id)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByType(int defectType_id){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.defectType dt WHERE dt.defectTypeId = :defectType_id "
                + "AND d.defectState = :fixed")
                .setParameter("defectType_id", defectType_id)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByUserAndInyPhase(int phase_id, String username){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.inyectionPhase ip JOIN d.assignedUser u WHERE "
                + "ip.phaseId = :phase_id AND u.userName = :username "
                + "AND (d.defectState = :accepted OR d.defectState = :fixed)")
                .setParameter("phase_id", phase_id)
                .setParameter("username", username)
                .setParameter("accepted", DefectState.ACCEPTED)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByUserAndRemPhase(int phase_id, String username){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.remotionPhase ip JOIN d.assignedUser u WHERE "
                + "ip.phaseId = :phase_id AND u.userName = :username "
                + "AND d.defectState = :fixed")
                .setParameter("phase_id", phase_id)
                .setParameter("username", username)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByInyectionPhase(int phase_id){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.inyectionPhase ip WHERE "
                + "ip.phaseId = :phase_id AND (d.defectState = :accepted OR d.defectState = :fixed)")
                .setParameter("phase_id", phase_id)
                .setParameter("accepted", DefectState.ACCEPTED)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByRemotionPhase(int phase_id){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.remotionPhase ip WHERE "
                + "ip.phaseId = :phase_id AND d.defectState = :fixed")
                .setParameter("phase_id", phase_id)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByState(DefectState defectState){
        @SuppressWarnings("unchecked")        
        
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "WHERE d.defectState = :defectState")
                .setParameter("defectState", defectState)
                .getResultList();
        
        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByStateAndProject(DefectState defectState, int project_id){
        @SuppressWarnings("unchecked")

        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.project p WHERE d.defectState = :defectState "
                + "AND p.projectId = :project_id")
                .setParameter("defectState", defectState)
                .setParameter("project_id", project_id)
                .getResultList();

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByStateAndUser(DefectState defectState, String username){
        @SuppressWarnings("unchecked")

        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.assignedUser u WHERE d.defectState = :defectState "
                + "AND u.userName = :username")
                .setParameter("defectState", defectState)
                .setParameter("username", username)
                .getResultList();

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByStateAndPhaseType(DefectState defectState, PhaseType type){
        @SuppressWarnings("unchecked")

        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.remotionPhase p WHERE d.defectState = :defectState AND p.phaseType = :type")
                .setParameter("defectState", defectState)
                .setParameter("type", type)
                .getResultList();

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByStatePhaseTypeProject(DefectState defectState, PhaseType type,
            int project_id){
        @SuppressWarnings("unchecked")

        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.remotionPhase p JOIN d.project pr WHERE "
                + "d.defectState = :defectState AND p.phaseType = :type "
                + "AND pr.projectId = :project_id")
                .setParameter("defectState", defectState)
                .setParameter("type", type)
                .setParameter("project_id", project_id)
                .getResultList();

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByStateAndTaskType(DefectState state, TaskType type){
        @SuppressWarnings("unchecked")

        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.remotionTask t WHERE "
                + "d.defectState = :state AND t.taskType = :type")
                .setParameter("state", state)
                .setParameter("type", type)
                .getResultList();

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByStateTaskTypeProject(DefectState state,
            TaskType type, int project_id){
        @SuppressWarnings("unchecked")

        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.remotionTask t JOIN d.project p WHERE "
                + "d.defectState = :state AND t.taskType = :type "
                + "AND p.projectId = :project_id")
                .setParameter("state", state)
                .setParameter("type", type)
                .setParameter("project_id", project_id)
                .getResultList();

        return result;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByInyPhaseProject(int phase_id, int project_id){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.inyectionPhase ph JOIN d.project p WHERE "
                + "ph.phaseId = :phase_id AND p.projectId = :project_id "
                + "AND (d.defectState = :accepted OR d.defectState = :fixed)")
                .setParameter("phase_id", phase_id)
                .setParameter("project_id", project_id)
                .setParameter("accepted", DefectState.ACCEPTED)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();

        return result;

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByDetPhaseProject(int phase_id, int project_id){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.detectionPhase ph JOIN d.project p WHERE "
                + "ph.phaseId = :phase_id AND p.projectId = :project_id "
                + "AND (d.defectState = :accepted OR d.defectState = :fixed)")
                .setParameter("phase_id", phase_id)
                .setParameter("project_id", project_id)
                .setParameter("accepted", DefectState.ACCEPTED)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();

        return result;

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByRemPhaseProject(int phase_id, int project_id){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.remotionPhase ph JOIN d.project p WHERE "
                + "ph.phaseId = :phase_id AND p.projectId = :project_id "
                + "AND d.defectState = :fixed")
                .setParameter("phase_id", phase_id)
                .setParameter("project_id", project_id)
                .setParameter("fixed", DefectState.FIXED)
                .getResultList();

        return result;

    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Defect> searchByProjectAndUser(int projectID, String userName){
        @SuppressWarnings("unchecked")
        List<Defect> result = getEntityManager().createQuery("SELECT d FROM Defect d "
                + "JOIN d.assignedUser u JOIN d.project pr WHERE u.userName =:userName AND pr.projectId=:projectID")
                .setParameter("projectID", projectID)
                .setParameter("userName", userName)
                .getResultList();
        return result;
    }
}
