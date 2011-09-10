/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: PhaseYieldReportBizOpImpl.java 279 2011-09-09 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2011-09-09 10:43:29 -0500 (Fri, 09 Sep 2011) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.text.DecimalFormat;
import mx.itesm.gda.bm.biz.PhaseYieldReportBizOp;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.Phase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Component
public class PhaseYieldReportBizOpImpl extends AbstractBizOp implements PhaseYieldReportBizOp{
    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private DefectDAO defectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getPhaseYieldReport(int project_id){
        String xmlData = null;
        ArrayList<String> phaseNames = new ArrayList<String>();
        ArrayList<Integer> phaseDefectsNumber = new ArrayList<Integer>();
        ArrayList<Double> phaseYield = new ArrayList<Double>();

        Project project = projectDAO.findById(project_id);
        List<Defect> projectDefects = project.getDefects();
        int totalDefects = projectDefects.size();
        
        for(Phase phase : project.getPhases()){
            phaseNames.add(phase.getPhaseName());
            Integer phase_id = phase.getPhaseId();
            List<Defect> phaseDefects = defectDAO.searchByDetectionPhase(phase_id);
            phaseDefectsNumber.add(phaseDefects.size());        
        }        

        for(int i = 0; i < phaseDefectsNumber.size(); i++){
            if (totalDefects == 0){
                phaseYield.add(0.0);
            }
            else{
                phaseYield.add(roundNumber(100*((double)phaseDefectsNumber.get(i)/(double)totalDefects)));
            }
        }
        xmlData += "<chart caption='Yield de fase' xAxisName='Fase' yAxisName='Porcentaje' bgAlpha='0,0'>";

        for(int i = 0; i < phaseNames.size(); i++){
            xmlData += "<set label='" + phaseNames.get(i) + "' value='" + phaseYield.get(i) + "' />";
        }

        xmlData+= "</chart>";
        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}