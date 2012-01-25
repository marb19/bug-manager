/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.itesm.gda.bm.biz.impl;

import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import mx.itesm.gda.bm.biz.ReviewsYieldBizOp;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskType;
/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class ReviewsYieldBizOpImpl extends AbstractBizOp implements ReviewsYieldBizOp{

    @Autowired
    private DefectDAO defectDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getReviewsYieldReport(int project_id){
        String xmlData = null, chartTitle = null;
        int perReviewDefects = 0, peerReviewDefects = 0, walkDefects = 0, inspectionDefects = 0;
        List<Defect> allDefects, finalListDefects;
        ArrayList<String> reviewsNames = new ArrayList<String>();
        ArrayList<Integer> reviewsDefects = new ArrayList<Integer>();
        ArrayList<Double> reviewsYields = new ArrayList<Double>();

        reviewsNames.add("Revisión Personal");
        reviewsNames.add("Revisión de Colegas");
        reviewsNames.add("Caminata");
        reviewsNames.add("Inspección");

        finalListDefects = new ArrayList<Defect>();

        if (project_id == 0){
            allDefects = defectDAO.getAll();
            chartTitle = "<chart caption='Yield por técnica para la empresa' xAxisName='Técnica de detección' "
                    + "yAxisName='Porcentaje de defectos' bgAlpha='0,0'>";
        }
        else
        {
            Project project = projectDAO.findById(project_id);
            String projectName = project.getProjectName();
            allDefects = project.getDefects();
            chartTitle = "<chart caption='Yield por técnica para el proyecto " +  projectName + "'"
                    + "xAxisName='Técnica de detección' yAxisName='Porcentaje de defectos' bgAlpha='0,0'>";
        }

        int numOfDefects = 0;
        for (Defect singleDefect : allDefects){
            if (singleDefect.getDefectState() == DefectState.ACCEPTED || singleDefect.getDefectState() == DefectState.FIXED){
                finalListDefects.add(singleDefect);
            }
        }
        if (finalListDefects != null){
            numOfDefects = finalListDefects.size();

            for(Defect singleDefect : finalListDefects){
                Task detectionTask = singleDefect.getDetectionTask();
                TaskType taskType = detectionTask.getTaskType();
                if (taskType == TaskType.PERSONAL_REVIEW){
                    perReviewDefects++;
                }
                else if (taskType == TaskType.PEER_REVIEW){
                    peerReviewDefects++;
                }
                else if (taskType == TaskType.WALKTHROUGH){
                    walkDefects++;
                }
                else if (taskType == TaskType.INSPECTION){
                    inspectionDefects++;
                }
            }
        }

        reviewsDefects.add(perReviewDefects);
        reviewsDefects.add(peerReviewDefects);
        reviewsDefects.add(walkDefects);
        reviewsDefects.add(inspectionDefects);

        for(int i = 0; i < reviewsNames.size(); i++){
            if (numOfDefects == 0){
                reviewsYields.add(0.0);
            }
            else{
                reviewsYields.add(roundNumber(100 * ((double)reviewsDefects.get(i)/(double)numOfDefects)));
            }
        }

        xmlData += chartTitle;

        for(int i = 0; i < reviewsNames.size(); i++){
            xmlData += "<set label='" + reviewsNames.get(i) + "' value='" + reviewsYields.get(i) + "' />";
        }

        xmlData+= "</chart>";

        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
