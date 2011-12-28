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
import mx.itesm.gda.bm.biz.QualityCostBizOp;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.PhaseType;
/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class QualityCostBizOpImpl extends AbstractBizOp implements QualityCostBizOp{

    @Autowired
    private DefectDAO defectDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getQualityCostReport(int project_id){
        String xmlData = null, categories = null, defects = null, tasks = null;
        long requirementsEffort = 0, designEffort = 0, codingEffort = 0, reviewEffort = 0,
                testingEffort = 0, maintenanceEffort = 0, COQEffort = 0, CNQEffort = 0;
        long taskReqEffort = 0, taskDesignEffort = 0, taskCodingEffort = 0, taskReviewEffort = 0,
                taskTestingEffort = 0, taskMaintenanceEffort = 0, taskCOQEffort = 0, taskCNQEffort = 0;
        List<Defect> requirementsDefects, designDefects, codingDefects, reviewDefects,
                testingDefects, maintenanceDefects;
        List<Task> reqTasks, designTasks, codingTasks, reviewTasks, testingTasks, maintenaceTasks;

        if (project_id == 0){
            requirementsDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.REQUIREMENTS);
            designDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.DESIGN);
            codingDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.CODING);
            reviewDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.REVIEW);
            testingDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.TESTING);
            maintenanceDefects = defectDAO.searchByStateAndPhaseType(DefectState.FIXED, PhaseType.MAINTENANCE);

            reqTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.REQUIREMENTS);
            designTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.DESIGN);
            codingTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.CODING);
            reviewTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.REVIEW);
            testingTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.TESTING);
            maintenaceTasks = taskDAO.getQualityTasksByStatePhaseType(TaskState.COMPLETED, PhaseType.MAINTENANCE);

            xmlData+= "<chart caption='Costos de la Empresa' xAxisName='COQ vs CNQ' yAxisName='Esfuerzo' "
                    + "bgAlpha='0,0'>";
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
            testingDefects = defectDAO.searchByStatePhaseTypeProject(DefectState.FIXED,
                    PhaseType.TESTING, project_id);
            maintenanceDefects = defectDAO.searchByStatePhaseTypeProject(DefectState.FIXED,
                    PhaseType.MAINTENANCE, project_id);

            reqTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.REQUIREMENTS, project_id);
            designTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.DESIGN, project_id);
            codingTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.CODING, project_id);
            reviewTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.REVIEW, project_id);
            testingTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.TESTING, project_id);
            maintenaceTasks = taskDAO.getQualityTasksByStatePhaseTypeProject(TaskState.COMPLETED,
                    PhaseType.MAINTENANCE, project_id);

            xmlData+= "<chart caption='Costos del Proyecto " + projectName + "' xAxisName='COQ vs CNQ' yAxisName='Esfuerzo' "
                    + "bgAlpha='0,0'>";
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
        for (Defect singleDefect : testingDefects){
            testingEffort = testingEffort + singleDefect.getInvestedHours();
        }
        for (Defect singleDefect : maintenanceDefects){
            maintenanceEffort = maintenanceEffort + singleDefect.getInvestedHours();
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
        for (Task singleTask : testingTasks){
            taskTestingEffort = taskTestingEffort + singleTask.getInvestedHours();
        }
        for (Task singleTask : maintenaceTasks){
            taskMaintenanceEffort = taskMaintenanceEffort + singleTask.getInvestedHours();
        }

        COQEffort = requirementsEffort + designEffort + codingEffort + reviewEffort;

        taskCOQEffort = taskReqEffort + taskDesignEffort + taskCodingEffort + taskReviewEffort;

        CNQEffort = testingEffort + maintenanceEffort;

        taskCNQEffort = taskTestingEffort + taskMaintenanceEffort;

        categories= "<categories>";
        defects= "<dataset seriesName='Esfuerzo de defectos'>";
        tasks= "<dataset seriesName='Esfuerzo de tareas'>";

        categories+="<category name='COQ' />";
        defects+="<set value='" + COQEffort + "' />";
        tasks+="<set value='" + taskCOQEffort + "' />";

        categories+="<category name='CNQ' />";
        defects+="<set value='" + CNQEffort + "' />";
        tasks+="<set value='" + taskCNQEffort + "' />";

        categories+="</categories>";
        defects+="</dataset>";
        tasks+="</dataset>";
        xmlData+= categories + defects + tasks + "</chart>";

        return xmlData;
    }
}
