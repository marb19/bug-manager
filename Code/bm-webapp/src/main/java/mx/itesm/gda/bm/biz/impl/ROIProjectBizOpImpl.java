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
import mx.itesm.gda.bm.biz.ROIProjectBizOp;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.PhaseType;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class ROIProjectBizOpImpl extends AbstractBizOp implements ROIProjectBizOp{

    @Autowired
    private DefectDAO defectDAO;
    
    @Autowired
    private TaskDAO taskDAO;
    
    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getROIProjectReport(int project_id){
        String xmlData = null;
        long requirementsEffort = 0, designEffort = 0, codingEffort = 0,
                reviewEffort = 0, totalEffort = 0;
        long taskReqEffort = 0, taskDesignEffort = 0, taskCodingEffort = 0,
                taskReviewEffort = 0, taskTotalEffort = 0;
        List<Defect> requirementsDefects, designDefects, codingDefects, reviewDefects;
        List<Task> reqTasks, designTasks, codingTasks, reviewTasks;
        double roiMaintenance = 0, roiTesting = 0;

        if (project_id == 0){
            requirementsDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.REQUIREMENTS);
            designDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.DESIGN);
            codingDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.CODING);
            reviewDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.REVIEW);

            reqTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.REQUIREMENTS);
            designTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.DESIGN);
            codingTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.CODING);
            reviewTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.REVIEW);

            xmlData+= "<chart caption='ROI de la Empresa' xAxisName='Fase' yAxisName='ROI' "
                    + "showValues='0' formatNumberScale='0' labelDisplay='Rotate' bgAlpha='0,0'>";
        }
        else {
            Project project = projectDAO.findById(project_id);
            String projectName = project.getProjectName();

            requirementsDefects = defectDAO.searchByStatePhaseTypeProject(DefectState.FIXED,
                PhaseType.REQUIREMENTS, project_id);
            designDefects = defectDAO.searchByStatePhaseTypeProject(DefectState.FIXED,
                PhaseType.DESIGN, project_id);
            codingDefects = defectDAO.searchByStatePhaseTypeProject(DefectState.FIXED,
                PhaseType.CODING, project_id);
            reviewDefects = defectDAO.searchByStatePhaseTypeProject(DefectState.FIXED,
                    PhaseType.REVIEW, project_id);

            reqTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.REQUIREMENTS, project_id);
            designTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.DESIGN, project_id);
            codingTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.CODING, project_id);
            reviewTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.REVIEW, project_id);

            xmlData+= "<chart caption='ROI del Proyecto " + projectName + "' xAxisName='Fase' yAxisName='ROI' "
                    + "showValues='0' formatNumberScale='0' labelDisplay='Rotate' bgAlpha='0,0'>";
        }

        for (Defect singleDefect : requirementsDefects){
            requirementsEffort = requirementsEffort + singleDefect.getInvestedHours();
        }
        for (Defect singleDefect : designDefects){
            designEffort = designEffort + singleDefect.getInvestedHours();
        }
        for (Defect singleDefect : codingDefects){
            codingEffort = codingEffort + singleDefect.getInvestedHours();
        }
        for (Defect singleDefect : reviewDefects){
            reviewEffort = reviewEffort + singleDefect.getInvestedHours();
        }

        for (Task singleTask : reqTasks){
            taskReqEffort = taskReqEffort + singleTask.getInvestedHours();
        }
        for (Task singleTask : designTasks){
            taskDesignEffort = taskDesignEffort + singleTask.getInvestedHours();
        }
        for (Task singleTask : codingTasks){
            taskCodingEffort = taskCodingEffort + singleTask.getInvestedHours();
        }
        for (Task singleTask : reviewTasks){
            taskReviewEffort = taskReviewEffort + singleTask.getInvestedHours();
        }

        totalEffort = requirementsEffort + designEffort + codingEffort + reviewEffort;

        taskTotalEffort = taskReqEffort + taskDesignEffort + taskCodingEffort + taskReviewEffort;

        if (taskTotalEffort == 0){
            roiMaintenance = 0.0;
            roiTesting = 0.0;
        }
        else
        {
            roiMaintenance = roundNumber(((99 * totalEffort) - taskTotalEffort) / taskTotalEffort);
            roiTesting = roundNumber(((9 * totalEffort) - taskTotalEffort) / taskTotalEffort);
        }

        xmlData+= "<set label='ROI respecto a Producción ' value='" + roiMaintenance + "' />";
        xmlData+= "<set label='ROI respecto a pruebas' value='" + roiTesting + "' />";

        xmlData+= "</chart>";

        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
