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
import mx.itesm.gda.bm.biz.ReviewsEfficiencyBizOp;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.TaskState;
/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class ReviewsEfficiencyBizOpImpl extends AbstractBizOp implements ReviewsEfficiencyBizOp{

    @Autowired
    private DefectDAO defectDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getReviewsEfficiencyReport(int project_id){
        String xmlData = null, chartTitle = null;
        int perReviewDefects = 0, peerReviewDefects = 0, walkDefects = 0, inspectionDefects = 0;
        double taskEffort = 0;
        double perReviewEffort = 0, peerReviewEffort = 0, walkEffort = 0, inspectionEffort = 0;
        List<Defect> allDefects, finalListDefects;
        List<Task> allTasks, finalListTasks;
        ArrayList<String> reviewsNames = new ArrayList<String>();
        ArrayList<Integer> reviewsDefects = new ArrayList<Integer>();
        ArrayList<Double> reviewsEfforts = new ArrayList<Double>();
        ArrayList<Double> reviewsEfficiencies = new ArrayList<Double>();

        reviewsNames.add("Revisión Personal");
        reviewsNames.add("Revisión de Colegas");
        reviewsNames.add("Caminata");
        reviewsNames.add("Inspección");

        finalListTasks = new ArrayList<Task>();
        finalListDefects = new ArrayList<Defect>();

        if (project_id == 0){
            allDefects = defectDAO.getAll();
            allTasks = taskDAO.getAll();
            chartTitle = "<chart caption='Eficiencia por técnica para la empresa' xAxisName='Técnica de detección' "
                    + "yAxisName='Número de defectos detectados por hora' bgAlpha='0,0'>";
        }
        else
        {
            Project project = projectDAO.findById(project_id);
            String projectName = project.getProjectName();
            allDefects = project.getDefects();
            allTasks = project.getTasks();
            chartTitle = "<chart caption='Eficiencia por técnica para el proyecto " +  projectName + "'"
                    + "xAxisName='Número de defectos detectados por hora' yAxisName='Número de defectos detectados por hora' bgAlpha='0,0'>";
        }

        for (Defect singleDefect : allDefects){
            if (singleDefect.getDefectState() == DefectState.ACCEPTED || singleDefect.getDefectState() == DefectState.FIXED){
                finalListDefects.add(singleDefect);
            }
        }

        if(finalListDefects != null){
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

        for (Task singleTask : allTasks){
            if (singleTask.getTaskState() == TaskState.STARTED || singleTask.getTaskState() == TaskState.COMPLETED){
                finalListTasks.add(singleTask);
            }
        }

        if(finalListTasks != null){
            for(Task singleTask : finalListTasks){
                taskEffort = singleTask.getInvestedHours();
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
        }

        reviewsDefects.add(perReviewDefects);
        reviewsDefects.add(peerReviewDefects);
        reviewsDefects.add(walkDefects);
        reviewsDefects.add(inspectionDefects);

        reviewsEfforts.add(perReviewEffort);
        reviewsEfforts.add(peerReviewEffort);
        reviewsEfforts.add(walkEffort);
        reviewsEfforts.add(inspectionEffort);

        for(int i = 0; i < reviewsDefects.size(); i++){
            if (reviewsEfforts.get(i) == 0){
                reviewsEfficiencies.add(0.0);
            }
            else{
                reviewsEfficiencies.add(roundNumber((double)reviewsDefects.get(i)/reviewsEfforts.get(i)));
            }
        }

        xmlData += chartTitle;

        for(int i = 0; i < reviewsNames.size(); i++){
            xmlData += "<set label='" + reviewsNames.get(i) + "' value='" + reviewsEfficiencies.get(i) + "' />";
        }

        xmlData+= "</chart>";

        return xmlData;
    }

     private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
