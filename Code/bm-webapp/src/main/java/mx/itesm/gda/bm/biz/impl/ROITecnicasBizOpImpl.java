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
import mx.itesm.gda.bm.biz.ROITecnicasBizOp;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class ROITecnicasBizOpImpl extends AbstractBizOp implements ROITecnicasBizOp{

    @Autowired
    private DefectDAO defectDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getROITecnicasReport(int project_id){
        String xmlData = null, categories = null, ROI = null, Effort = null;
        long perReviewEffort = 0, peerReviewEffort = 0, walkEffort = 0, inspectionEffort = 0, testingEffort = 0;
        long taskPerReviewEffort = 0, taskPeerReviewEffort = 0, taskWalkEffort = 0, taskInspectionEffort = 0;
        long taskTestingEffort = 0;
        List<Defect> perReviewDefects, peerReviewDefects, walkDefects, inspectionDefects, testingDefects;
        List<Task> perReviewTasks, peerReviewTasks, walkTasks, inspectionTasks, testingTasks;
        double roiPerReview = 0, roiPeerReview = 0, roiWalk = 0, roiInspection = 0, roiTesting = 0;

        if (project_id == 0){

            perReviewDefects = defectDAO.searchByStateAndTaskType(DefectState.FIXED, TaskType.PERSONAL_REVIEW);
            peerReviewDefects = defectDAO.searchByStateAndTaskType(DefectState.FIXED, TaskType.PEER_REVIEW);
            walkDefects = defectDAO.searchByStateAndTaskType(DefectState.FIXED, TaskType.WALKTHROUGH);
            inspectionDefects = defectDAO.searchByStateAndTaskType(DefectState.FIXED, TaskType.INSPECTION);
            testingDefects = defectDAO.searchByStateAndTaskType(DefectState.FIXED, TaskType.TESTING);

            perReviewTasks = taskDAO.getTasksByTypeAndState(TaskType.PERSONAL_REVIEW, TaskState.COMPLETED);
            peerReviewTasks = taskDAO.getTasksByTypeAndState(TaskType.PEER_REVIEW, TaskState.COMPLETED);
            walkTasks = taskDAO.getTasksByTypeAndState(TaskType.WALKTHROUGH, TaskState.COMPLETED);
            inspectionTasks = taskDAO.getTasksByTypeAndState(TaskType.INSPECTION, TaskState.COMPLETED);
            testingTasks = taskDAO.getTasksByTypeAndState(TaskType.TESTING, TaskState.COMPLETED);

            xmlData+= "<chart caption='ROI de Técnicas para la Empresa' xAxisName='Técnica' "
                    + "yAxisName='ROI y Esfuerzo' bgAlpha='0,0'>";
        }
        else {
            Project project = projectDAO.findById(project_id);
            String projectName = project.getProjectName();

            perReviewDefects = defectDAO.searchByStateTaskTypeProject(DefectState.FIXED, TaskType.PERSONAL_REVIEW, project_id);
            peerReviewDefects = defectDAO.searchByStateTaskTypeProject(DefectState.FIXED, TaskType.PEER_REVIEW, project_id);
            walkDefects = defectDAO.searchByStateTaskTypeProject(DefectState.FIXED, TaskType.WALKTHROUGH, project_id);
            inspectionDefects = defectDAO.searchByStateTaskTypeProject(DefectState.FIXED, TaskType.INSPECTION, project_id);
            testingDefects = defectDAO.searchByStateTaskTypeProject(DefectState.FIXED, TaskType.TESTING, project_id);

            perReviewTasks = taskDAO.getTasksByTypeStateProject(TaskType.PERSONAL_REVIEW, TaskState.COMPLETED, project_id);
            peerReviewTasks = taskDAO.getTasksByTypeStateProject(TaskType.PEER_REVIEW, TaskState.COMPLETED, project_id);
            walkTasks = taskDAO.getTasksByTypeStateProject(TaskType.WALKTHROUGH, TaskState.COMPLETED, project_id);
            inspectionTasks = taskDAO.getTasksByTypeStateProject(TaskType.INSPECTION, TaskState.COMPLETED, project_id);
            testingTasks = taskDAO.getTasksByTypeStateProject(TaskType.TESTING, TaskState.COMPLETED, project_id);

            xmlData+= "<chart caption='ROI de Técnicas para el proyecto " + projectName + "' "
                    + "xAxisName='Técnica' yAxisName='ROI y Esfuerzo' bgAlpha='0,0'>";
        }

        for (Defect singleDefect : perReviewDefects){
            perReviewEffort = perReviewEffort + singleDefect.getInvestedHours();
        }
        for (Defect singleDefect : peerReviewDefects){
            peerReviewEffort = peerReviewEffort + singleDefect.getInvestedHours();
        }
        for (Defect singleDefect : walkDefects){
            walkEffort = walkEffort + singleDefect.getInvestedHours();
        }
        for (Defect singleDefect : inspectionDefects){
            inspectionEffort = inspectionEffort + singleDefect.getInvestedHours();
        }
        for (Defect singleDefect : testingDefects){
            testingEffort = testingEffort + singleDefect.getInvestedHours();
        }

        for (Task singleTask : perReviewTasks){
            taskPerReviewEffort = taskPerReviewEffort + singleTask.getInvestedHours();
        }
        for (Task singleTask : peerReviewTasks){
            taskPeerReviewEffort = taskPeerReviewEffort + singleTask.getInvestedHours();
        }
        for (Task singleTask : walkTasks){
            taskWalkEffort = taskWalkEffort + singleTask.getInvestedHours();
        }
        for (Task singleTask : inspectionTasks){
            taskInspectionEffort = taskInspectionEffort + singleTask.getInvestedHours();
        }
        for (Task singleTask : testingTasks){
            taskTestingEffort = taskTestingEffort + singleTask.getInvestedHours();
        }

        if (taskPerReviewEffort == 0){
            roiPerReview = 0.0;
        }
        else
        {
            roiPerReview = roundNumber(((99 * perReviewEffort) - taskPerReviewEffort) / taskPerReviewEffort);
        }

        if (taskPeerReviewEffort == 0){
            roiPeerReview = 0.0;
        }
        else
        {
            roiPeerReview = roundNumber(((99 * peerReviewEffort) - taskPeerReviewEffort) / taskPeerReviewEffort);
        }

        if (taskWalkEffort == 0){
            roiWalk = 0.0;
        }
        else
        {
            roiWalk = roundNumber(((99 * walkEffort) - taskWalkEffort) / taskWalkEffort);
        }

        if (taskInspectionEffort == 0){
            roiInspection = 0.0;
        }
        else
        {
            roiInspection = roundNumber(((99 * inspectionEffort) - taskInspectionEffort) / taskInspectionEffort);
        }

        if (taskTestingEffort == 0){
            roiTesting = 0.0;
        }
        else
        {
            roiTesting = roundNumber(((9 * testingEffort) - taskTestingEffort) / taskTestingEffort);
        }

        categories= "<categories>";
        ROI= "<dataset seriesName='ROI'>";
        Effort= "<dataset seriesName='Esfuerzo'>";

        categories+="<category name='Revisión Personal' />";
        ROI+="<set value='" + roiPerReview + "' />";
        Effort+="<set value='" + taskPerReviewEffort + "' />";

        categories+="<category name='Revisión Colegas' />";
        ROI+="<set value='" + roiPeerReview + "' />";
        Effort+="<set value='" + taskPeerReviewEffort + "' />";

        categories+="<category name='Caminata' />";
        ROI+="<set value='" + roiWalk + "' />";
        Effort+="<set value='" + taskWalkEffort + "' />";

        categories+="<category name='Inspección' />";
        ROI+="<set value='" + roiInspection + "' />";
        Effort+="<set value='" + taskInspectionEffort + "' />";

        categories+="<category name='Pruebas' />";
        ROI+="<set value='" + roiTesting + "' />";
        Effort+="<set value='" + taskTestingEffort + "' />";

        categories+="</categories>";
        ROI+="</dataset>";
        Effort+="</dataset>";
        xmlData+= categories + ROI + Effort + "</chart>";

        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
