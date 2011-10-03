/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectsInyRemBizOpImpl.java 279 2011-09-21 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2011-09-09 10:43:29 -0500 (Wed, 21 Sep 2011) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import mx.itesm.gda.bm.biz.DefectsInyRemBizOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.model.dao.PhaseDAO;
/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Component
public class DefectsInyRemBizOpImpl extends AbstractBizOp implements DefectsInyRemBizOp{

    @Autowired
    private PhaseDAO phaseDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private DefectDAO defectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getDefectsInyRemReport(int level, int project_id, String username){
        String xmlData = null;
        List<Phase> phases = null;
        Project project = null;

        switch(level){
            case 1:
                phases = phaseDAO.getAll();
                xmlData = getReportByUser(phases, username);
                break;
            case 2:
                project = projectDAO.findById(project_id);
                String projectName = project.getProjectName();
                phases = project.getPhases();
                xmlData = getReportByProject(phases, projectName);
                break;
            case 3:
                phases = phaseDAO.getAll();
                xmlData = getReportByCompany(phases);
                break;
            default:
                phases = phaseDAO.getAll();
                xmlData = getReportByCompany(phases);
                break;
        }

        return xmlData;
    }

     private String getReportByUser(List<Phase> listOfPhases, String username){
        String xmlData = null;
        String categories = null;
        String inyectedDefects = null;
        String removedDefects = null;
        ArrayList<String> phaseNames = new ArrayList<String>();
        ArrayList<Integer> inyectionDefects = new ArrayList<Integer>();
        ArrayList<Integer> remotionDefects = new ArrayList<Integer>();

        User user = userDAO.findByUserName(username);
        String fullName = user.getFullName();

        for(Phase phase : listOfPhases){
            phaseNames.add(phase.getPhaseName());
            int phase_id = phase.getPhaseId();
            List<Defect> phaseInyectionDefects = defectDAO.searchByUserAndInyPhase(phase_id, username);
            List<Defect> phaseRemotionDefects = defectDAO.searchByUserAndRemPhase(phase_id, username);
            inyectionDefects.add(phaseInyectionDefects.size());
            remotionDefects.add(phaseRemotionDefects.size());
        }

        xmlData= "<chart caption='Defectos inyectados y removidos por fase para el usuario " +  fullName + "'" + " xAxisName='Fases' yAxisName='Cantidad' bgAlpha='0,0'>";
        categories= "<categories>";
        inyectedDefects= "<dataset seriesName='Inyected'>";
        removedDefects= "<dataset seriesName='Removed'>";

        for(int i = 0; i < phaseNames.size(); i++){
            categories+="<category name='" + phaseNames.get(i) + "' />";
            inyectedDefects+="<set value='" + inyectionDefects.get(i) + "' />";
            removedDefects+="<set value='" + remotionDefects.get(i) + "' />";
        }

        categories+="</categories>";
        inyectedDefects+="</dataset>";
        removedDefects+="</dataset>";
        xmlData+= categories + inyectedDefects + removedDefects + "</chart>";

        return xmlData;
    }

    private String getReportByProject(List<Phase> listOfPhases, String projectName){
        String xmlData = null;
        String categories = null;
        String inyectedDefects = null;
        String removedDefects = null;
        ArrayList<String> phaseNames = new ArrayList<String>();
        ArrayList<Integer> inyectionDefects = new ArrayList<Integer>();
        ArrayList<Integer> remotionDefects = new ArrayList<Integer>();

        for(Phase phase : listOfPhases){
            phaseNames.add(phase.getPhaseName());
            int phase_id = phase.getPhaseId();
            List<Defect> phaseInyectionDefects = defectDAO.searchByInyectionPhase(phase_id);
            List<Defect> phaseRemotionDefects = defectDAO.searchByRemotionPhase(phase_id);
            inyectionDefects.add(phaseInyectionDefects.size());
            remotionDefects.add(phaseRemotionDefects.size());
        }

        xmlData= "<chart caption='Defectos inyectados y removidos por fase para el proyecto " +  projectName + "'" + " xAxisName='Fases' yAxisName='Cantidad' bgAlpha='0,0'>";
        categories= "<categories>";
        inyectedDefects= "<dataset seriesName='Inyected'>";
        removedDefects= "<dataset seriesName='Removed'>";

        for(int i = 0; i < phaseNames.size(); i++){
            categories+="<category name='" + phaseNames.get(i) + "' />";
            inyectedDefects+="<set value='" + inyectionDefects.get(i) + "' />";
            removedDefects+="<set value='" + remotionDefects.get(i) + "' />";
        }

        categories+="</categories>";
        inyectedDefects+="</dataset>";
        removedDefects+="</dataset>";
        xmlData+= categories + inyectedDefects + removedDefects + "</chart>";

        return xmlData;
    }

    private String getReportByCompany(List<Phase> listOfPhases){
        String xmlData = null;
        String categories = null;
        String inyectedDefects = null;
        String removedDefects = null;
        ArrayList<String> phaseNames = new ArrayList<String>();
        ArrayList<Integer> inyectionDefects = new ArrayList<Integer>();
        ArrayList<Integer> remotionDefects = new ArrayList<Integer>();

        for(Phase phase : listOfPhases){
            phaseNames.add(phase.getPhaseName());
            int phase_id = phase.getPhaseId();
            List<Defect> phaseInyectionDefects = defectDAO.searchByInyectionPhase(phase_id);
            List<Defect> phaseRemotionDefects = defectDAO.searchByRemotionPhase(phase_id);
            inyectionDefects.add(phaseInyectionDefects.size());
            remotionDefects.add(phaseRemotionDefects.size());
        }

        xmlData= "<chart caption='Defectos inyectados y removidos por fase para la empresa' xAxisName='Fases' yAxisName='Cantidad' bgAlpha='0,0'>";
        categories= "<categories>";
        inyectedDefects= "<dataset seriesName='Inyected'>";
        removedDefects= "<dataset seriesName='Removed'>";

        for(int i = 0; i < phaseNames.size(); i++){
            categories+="<category name='" + phaseNames.get(i) + "' />";
            inyectedDefects+="<set value='" + inyectionDefects.get(i) + "' />";
            removedDefects+="<set value='" + remotionDefects.get(i) + "' />";
        }

        categories+="</categories>";
        inyectedDefects+="</dataset>";
        removedDefects+="</dataset>";
        xmlData+= categories + inyectedDefects + removedDefects + "</chart>";

        return xmlData;
    }
}
