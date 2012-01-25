/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: PhaseTimeReportBizOpImpl.java 279 2011-09-09 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2011-09-09 10:43:29 -0500 (Fri, 09 Sep 2011) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.text.DecimalFormat;
import mx.itesm.gda.bm.biz.PhaseTimeReportBizOp;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;

/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Component
public class PhaseTimeReportBizOpImpl extends AbstractBizOp implements PhaseTimeReportBizOp{

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getPhaseTimeReport(int project_id){
        String xmlData = null;
        ArrayList<String> phaseNames = new ArrayList<String>();
        ArrayList<Integer> phaseTimes = new ArrayList<Integer>();
        ArrayList<Double> phasePercents = new ArrayList<Double>();
        int totalInvestedHours = 0;

        Project project = projectDAO.findById(project_id);

        for(Phase phase : project.getPhases()){
            Integer phaseHours = 0;
            for (Task task : phase.getTasks()){
                if (task.getTaskState() == TaskState.STARTED || task.getTaskState() == TaskState.COMPLETED){
                    totalInvestedHours += task.getInvestedHours();
                    phaseHours += task.getInvestedHours();
                }
            }
            phaseNames.add(phase.getPhaseName());
            phaseTimes.add(phaseHours);
        }

        for(int i = 0; i < phaseTimes.size(); i++){
            if (totalInvestedHours != 0){
                phasePercents.add(roundNumber(100*((double)phaseTimes.get(i)/(double)totalInvestedHours)));
            }
            else {
                phasePercents.add(0.0);
            }
        }

        xmlData += "<chart caption='Tiempo de fase' xAxisName='Fase' yAxisName='Porcentaje' bgAlpha='0,0'>";

        for(int i = 0; i < phaseNames.size(); i++){
            xmlData += "<set label='" + phaseNames.get(i) + "' value='" + phasePercents.get(i) + "' />";
        }

        xmlData+= "</chart>";
        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
