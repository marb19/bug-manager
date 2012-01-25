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
import mx.itesm.gda.bm.biz.ReviewsSpeedBizOp;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.dao.TaskDAO;
/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class ReviewsSpeedBizOpImpl extends AbstractBizOp implements ReviewsSpeedBizOp{

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getReviewsSpeedReport(int project_id){
        String xmlData = null, chartTitle = null;        
        double taskEffort = 0, perReviewEffort = 0, peerReviewEffort = 0, walkEffort = 0, inspectionEffort = 0;
        long taskSize = 0, perReviewSize = 0, peerReviewSize = 0, walkSize = 0, inspectionSize = 0;
        List<Task> allTasks, finalListTasks;
        ArrayList<String> reviewsNames = new ArrayList<String>();
        ArrayList<Double> reviewsEfforts = new ArrayList<Double>();
        ArrayList<Long> reviewsSizes = new ArrayList<Long>();
        ArrayList<Double> reviewsSpeed = new ArrayList<Double>();

        reviewsNames.add("Revisión Personal");
        reviewsNames.add("Revisión de Colegas");
        reviewsNames.add("Caminata");
        reviewsNames.add("Inspección");

        finalListTasks = new ArrayList<Task>();

        if (project_id == 0){
            allTasks = taskDAO.getAll();
            chartTitle = "<chart caption='Razón de revisión por técnica para la empresa' xAxisName='Técnica de detección' "
                    + "yAxisName='LOC por hora' bgAlpha='0,0'>";
        }
        else {
            Project project = projectDAO.findById(project_id);
            String projectName = project.getProjectName();
            allTasks = project.getTasks();
            chartTitle = "<chart caption='Razón de revisión por técnica para el proyecto " +  projectName + "'"
                    + "xAxisName='Técnica de detección' yAxisName='LOC por hora' bgAlpha='0,0'>";
        }

        for (Task singleTask : allTasks){
            if (singleTask.getTaskState() == TaskState.STARTED || singleTask.getTaskState() == TaskState.COMPLETED){
                finalListTasks.add(singleTask);
            }
        }

        if(finalListTasks != null){
            for(Task singleTask : finalListTasks){
                taskEffort = singleTask.getInvestedHours();
                taskSize = (long)singleTask.getSize();
                TaskType taskType = singleTask.getTaskType();
                if (taskType == TaskType.PERSONAL_REVIEW){
                    perReviewEffort = perReviewEffort + taskEffort;
                    perReviewSize = perReviewSize + taskSize;
                }
                else if (taskType == TaskType.PEER_REVIEW){
                    peerReviewEffort = peerReviewEffort + taskEffort;
                    peerReviewSize = peerReviewSize + taskSize;
                }
                else if (taskType == TaskType.WALKTHROUGH){
                    walkEffort = walkEffort + taskEffort;
                    walkSize = walkSize + taskSize;
                }
                else if (taskType == TaskType.INSPECTION){
                    inspectionEffort = inspectionEffort + taskEffort;
                    inspectionSize = inspectionSize + taskSize;
                }
            }
        }
        
        reviewsSizes.add(perReviewSize);
        reviewsSizes.add(peerReviewSize);
        reviewsSizes.add(walkSize);
        reviewsSizes.add(inspectionSize);

        reviewsEfforts.add(perReviewEffort);
        reviewsEfforts.add(peerReviewEffort);
        reviewsEfforts.add(walkEffort);
        reviewsEfforts.add(inspectionEffort);

        for(int i = 0; i < reviewsSizes.size(); i++){
            if (reviewsEfforts.get(i) == 0){
                reviewsSpeed.add(0.0);
            }
            else{
                reviewsSpeed.add(roundNumber((double)reviewsSizes.get(i)/reviewsEfforts.get(i)));
            }
        }

        xmlData += chartTitle;

        for(int i = 0; i < reviewsNames.size(); i++){
            xmlData += "<set label='" + reviewsNames.get(i) + "' value='" + reviewsSpeed.get(i) + "' />";
        }

        xmlData+= "</chart>";

        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
