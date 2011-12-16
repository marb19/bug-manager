/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.itesm.gda.bm.biz.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import mx.itesm.gda.bm.biz.ReviewsParetoBizOp;
import mx.itesm.gda.bm.model.Defect;
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
public class ReviewsParetoBizOpImpl extends AbstractBizOp implements ReviewsParetoBizOp{

    @Autowired
    private DefectDAO defectDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getReviewsParetoReport(int project_id){
        String xmlData = null, chartTitle = null;
        int perReviewDefects = 0, peerReviewDefects = 0, walkDefects = 0, inspectionDefects = 0;
        List<Defect> allDefects;
        ArrayList<String> reviewsNames = new ArrayList<String>();
        ArrayList<Integer> reviewsDefects = new ArrayList<Integer>();
        ArrayList<Double> reviewsYields = new ArrayList<Double>();

        reviewsNames.add("Revisión Personal");
        reviewsNames.add("Revisión de Colegas");
        reviewsNames.add("Caminata");
        reviewsNames.add("Inspección");

        if (project_id == 0){
            allDefects = defectDAO.getAll();
            chartTitle = "<chart caption='Número de defectos por técnica para la empresa' xAxisName='Técnica de detección' "
                    + "PYAxisName='Cantidad de defectos' bgAlpha='0,0'>";
        }
        else
        {
            Project project = projectDAO.findById(project_id);
            String projectName = project.getProjectName();
            allDefects = project.getDefects();
            chartTitle = "<chart caption='Número de defectos por técnica para el proyecto " +  projectName + "'"
                    + "xAxisName='Técnica de detección' PYAxisName='Cantidad de defectos' bgAlpha='0,0'>";
        }

        for(Defect singleDefect : allDefects){
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

        reviewsDefects.add(perReviewDefects);
        reviewsDefects.add(peerReviewDefects);
        reviewsDefects.add(walkDefects);
        reviewsDefects.add(inspectionDefects);

        xmlData += chartTitle;

        for(int i = 0; i < reviewsNames.size(); i++){
            xmlData += "<set label='" + reviewsNames.get(i) + "' value='" + reviewsDefects.get(i) + "' />";
        }

        xmlData+= "</chart>";

        return xmlData;
    }
}
