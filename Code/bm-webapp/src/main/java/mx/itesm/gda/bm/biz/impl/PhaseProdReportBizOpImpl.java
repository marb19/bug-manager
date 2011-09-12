/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: PhaseProdReportBizOpImpl.java 279 2011-09-09 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2011-09-09 10:43:29 -0500 (Fri, 09 Sep 2011) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.text.DecimalFormat;
import mx.itesm.gda.bm.biz.PhaseProdReportBizOp;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.model.Task;
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
public class PhaseProdReportBizOpImpl extends AbstractBizOp implements PhaseProdReportBizOp{

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getPhaseProdReport(int project_id){
        String xmlData = null;
        ArrayList<String> phaseNames = new ArrayList<String>();
        ArrayList<Integer> phaseTimes = new ArrayList<Integer>();
        ArrayList<Integer> phaseSizes = new ArrayList<Integer>();
        ArrayList<Double> phaseProd = new ArrayList<Double>();

        Project project = projectDAO.findById(project_id);

        for(Phase phase : project.getPhases()){
            Integer phaseHours = 0, phaseSize = 0;
            for (Task task : phase.getTasks()){
                phaseHours += task.getInvestedHours();
                phaseSize += task.getSize();
            }
            phaseNames.add(phase.getPhaseName());
            phaseTimes.add(phaseHours);
            phaseSizes.add(phaseSize);
        }

        for(int i = 0; i < phaseTimes.size(); i++){
            if (phaseTimes.get(i) != 0){
                phaseProd.add(roundNumber(60 * ((double)phaseSizes.get(i)/(double)phaseTimes.get(i))));
            }
            else {
                phaseProd.add(0.0);
            }
        }

        xmlData += "<chart caption='Productividad de fase' xAxisName='Fase' yAxisName='Paginas/LOC por hora' bgAlpha='0,0'>";

        for(int i = 0; i < phaseNames.size(); i++){
            xmlData += "<set label='" + phaseNames.get(i) + "' value='" + phaseProd.get(i) + "' />";
        }

        xmlData+= "</chart>";
        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
