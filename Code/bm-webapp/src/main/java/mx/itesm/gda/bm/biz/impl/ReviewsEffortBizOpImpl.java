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
import mx.itesm.gda.bm.biz.ReviewsEffortBizOp;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.dao.TaskDAO;
/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class ReviewsEffortBizOpImpl extends AbstractBizOp implements ReviewsEffortBizOp{

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getReviewsEffortReport(int project_id){
        String xmlData = null, chartTitle = null;
        List<Task> allTasks;
        double taskEffort = 0;
        double totalEffort = 0, perReviewEffort = 0, peerReviewEffort = 0, walkEffort = 0, inspectionEffort = 0;
        ArrayList<String> reviewsNames = new ArrayList<String>();
        ArrayList<Double> reviewsEfforts = new ArrayList<Double>();
        ArrayList<Double> reviewsPercent = new ArrayList<Double>();
        
        reviewsNames.add("Revisión Personal");
        reviewsNames.add("Revisión de Colegas");
        reviewsNames.add("Caminata");
        reviewsNames.add("Inspección");

        if (project_id == 0){
            allTasks = taskDAO.getAll();
            chartTitle = "<chart caption='Esfuerzo por técnica para la empresa' xAxisName='Técnica de detección' "
                    + "yAxisName='Porcentaje de esfuerzo' bgAlpha='0,0'>";
        }
        else {
            Project project = projectDAO.findById(project_id);
            String projectName = project.getProjectName();
            allTasks = project.getTasks();
            chartTitle = "<chart caption='Esfuerzo por técnica para el proyecto " +  projectName + "'"
                    + "xAxisName='Técnica de detección' yAxisName='Porcentaje de esfuerzo' bgAlpha='0,0'>";
        }

        for(Task singleTask : allTasks){
            taskEffort = singleTask.getInvestedHours();
            totalEffort = totalEffort + taskEffort;
            TaskType taskType = singleTask.getTaskType();
            if (taskType == TaskType.PERSONAL_REVIEW){
                perReviewEffort = perReviewEffort + taskEffort;
            }
            else if (taskType == TaskType.PEER_REVIEW){
                peerReviewEffort = peerReviewEffort + taskEffort;
            }
            else if (taskType == TaskType.WALKTHROUGH){
                walkEffort = walkEffort + taskEffort;
            }
            else if (taskType == TaskType.INSPECTION){
                inspectionEffort = inspectionEffort + taskEffort;
            }
        }

        reviewsEfforts.add(perReviewEffort);
        reviewsEfforts.add(peerReviewEffort);
        reviewsEfforts.add(walkEffort);
        reviewsEfforts.add(inspectionEffort);

        for(int i = 0; i < reviewsNames.size(); i++){
            if (totalEffort == 0){
                reviewsPercent.add(0.0);
            }
            else{
                reviewsPercent.add(roundNumber(100 * (reviewsEfforts.get(i)/totalEffort)));
            }
        }

        xmlData += chartTitle;

        for(int i = 0; i < reviewsNames.size(); i++){
            xmlData += "<set label='" + reviewsNames.get(i) + "' value='" + reviewsPercent.get(i) + "' />";
        }

        xmlData+= "</chart>";

        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
