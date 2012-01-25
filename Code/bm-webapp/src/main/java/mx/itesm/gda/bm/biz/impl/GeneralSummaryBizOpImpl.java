/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.itesm.gda.bm.biz.impl;

import mx.itesm.gda.bm.biz.GeneralSummaryBizOp;
import java.text.DecimalFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.model.PhaseType;

/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class GeneralSummaryBizOpImpl extends AbstractBizOp implements GeneralSummaryBizOp{

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private DefectDAO defectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getProjectName(int project_id){
        Project project = projectDAO.findById(project_id);
        return project.getProjectName();
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public List<Map<String, ?>> getSummaryReport(int project_id){
        List<Map<String, ?>> summary = new ArrayList<Map<String, ?>>();        
        Map<String, Object> totalRow = new HashMap<String, Object>();
        long totalInyDefects = 0, totalRemDefects = 0, totalTime = 0,
                totalAppraisalCost = 0, totalFailureCost = 0;
        int acumInyected = 0, acumRemoved = 0, escaped = 0, goal = 0, remDefects = 0, timePhase = 0;
        double yield = 0, afr = 0, testingEfficiency = 0;

        Project project = projectDAO.findById(project_id);
        List<Phase> allPhases = project.getPhases();        

        // Getting testing efficiency
        for (Phase singlePhase : allPhases){
            if (singlePhase.getType() == PhaseType.TESTING){
                List<Defect> removedDefects = defectDAO.searchByRemPhaseProject(singlePhase.getPhaseId(), project_id);
                remDefects = remDefects + removedDefects.size();
                List<Task> tasksByPhase = singlePhase.getTasks();
                for (Task singleTask : tasksByPhase){
                    timePhase = timePhase + singleTask.getInvestedHours();
                }
            }
        }

        if (timePhase == 0){
            testingEfficiency = 0;
        }
        else {
            testingEfficiency = remDefects / timePhase;
        }        

        // Getting the rest of the metrics
        for (Phase singlePhase : allPhases){
            double efficiency = 0, leverage = 0;
            // Getting the inyected and removed defects by phase, and their totals
            Map<String, Object> row = new HashMap<String, Object>();
            List<Defect> inyectedDefects = defectDAO.searchByInyPhaseProject(singlePhase.getPhaseId(), project_id);
            List<Defect> removedDefects = defectDAO.searchByRemPhaseProject(singlePhase.getPhaseId(), project_id);
            totalInyDefects = totalInyDefects + inyectedDefects.size();
            totalRemDefects = totalRemDefects + removedDefects.size();

            // Getting time by phase and total time
            List<Task> tasksByPhase = singlePhase.getTasks();
            long timeByPhase = 0;
            for (Task singleTask : tasksByPhase){
                timeByPhase = timeByPhase + singleTask.getInvestedHours();
            }
            totalTime = totalTime + timeByPhase;

            // Getting speed by phase (if phase type is correct) and appraisal cost
            String speedByPhase = "N/A";
            int appraisalCostByPhase = 0;
            if (singlePhase.getType() == PhaseType.REVIEW){
                speedByPhase = getSpeedByPhase(singlePhase);
                appraisalCostByPhase = getCost(singlePhase);
                if (timeByPhase == 0){
                    efficiency = 0;
                }
                else {
                    efficiency = removedDefects.size() / timeByPhase;
                }
                if (testingEfficiency == 0){
                    leverage = 0;
                }
                else {
                    leverage = efficiency / testingEfficiency;
                }
            }
            totalAppraisalCost = totalAppraisalCost + appraisalCostByPhase;

            // Getting failure cost by phase
            int failureCostByPhase = 0;
            if (singlePhase.getType() == PhaseType.TESTING){
                failureCostByPhase = getCost(singlePhase);
                if (timeByPhase == 0){
                    efficiency = 0;
                }
                else {
                    efficiency = removedDefects.size() / timeByPhase;
                }
            }
            totalFailureCost = totalFailureCost + failureCostByPhase;

            // Getting yield by phase
            List<Defect> detectionDefects = defectDAO.searchByInyPhaseProject(singlePhase.getPhaseId(), project_id);
            List<Defect> remotionDefects = defectDAO.searchByRemPhaseProject(singlePhase.getPhaseId(), project_id);
            acumInyected = acumInyected + detectionDefects.size();
            acumRemoved = acumRemoved + remotionDefects.size();
            escaped = acumInyected - acumRemoved;
            goal = remotionDefects.size() + escaped;
            if (goal == 0){
                yield = 0;
            }
            else {
                yield = roundNumber(100*((double)remotionDefects.size()/(double)goal));
            }

            // Putting all values in the map and adding the map to final array
            row.put("phase", singlePhase.getPhaseName());
            row.put("inyectedDefects", inyectedDefects.size());
            row.put("removedDefects", removedDefects.size());
            row.put("yield", yield);
            row.put("speed", speedByPhase);
            row.put("time", timeByPhase);
            if (appraisalCostByPhase == 0){
                row.put("appraisalCost", "N/A");
            }
            else {
                row.put("appraisalCost", appraisalCostByPhase);
            }
            if (failureCostByPhase == 0){
                row.put("failureCost", "N/A");
            }
            else {
                row.put("failureCost", failureCostByPhase);
            }

            row.put("afr", "N/A");
            row.put("defectEfficiency", efficiency);
            row.put("defectLeverage", leverage);

            summary.add(row);
        }

        if (totalFailureCost == 0){
            afr = 0;
        }
        else {
            afr = totalAppraisalCost / totalFailureCost;
        }
        // Putting all the total values in the map and adding the map to the final array
        totalRow.put("phase", "Total:");
        totalRow.put("inyectedDefects", totalInyDefects);
        totalRow.put("removedDefects", totalRemDefects);
        totalRow.put("yield", "N/A");
        totalRow.put("speed", "N/A");
        totalRow.put("time", totalTime);
        totalRow.put("appraisalCost", "N/A");
        totalRow.put("failureCost", "N/A");
        totalRow.put("afr", afr);
        totalRow.put("defectEfficiency", "N/A");
        totalRow.put("defectLeverage", "N/A");
        summary.add(totalRow);
        return summary;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public Map<String, ?> getDefectSummary(int project_id){
        Map<String, Object> row = new HashMap<String, Object>();
        int totalInyDefects = 0;
        long totalSize = 0;

        Project singleProject = projectDAO.findById(project_id);
        List<Defect> allDefects = singleProject.getDefects();
        for (Defect singleDefect : allDefects){
            if (singleDefect.getDefectState() == DefectState.ACCEPTED || singleDefect.getDefectState() == DefectState.FIXED){
                totalInyDefects++;
            }
        }
        List<Task> allTasks = singleProject.getTasks();
        for (Task singleTask : allTasks){
            if (singleTask.getTaskType() == TaskType.DEVELOPMENT &&
                    (singleTask.getTaskState() == TaskState.STARTED || singleTask.getTaskState() == TaskState.COMPLETED)){
                totalSize = totalSize + singleTask.getSize();
            }
        }

        if(totalSize == 0){
            row.put("density", 0);
        }
        else {
            row.put("density", roundNumber(1000 * (totalInyDefects/totalSize)));
        }
        row.put("defects", totalInyDefects);
        row.put("LOC", totalSize);

        return row;
    }

    private String getSpeedByPhase(Phase singlePhase){
        String speed = null;
        List<Task> allTasks = singlePhase.getTasks();
        long size = 0, time = 0, speedByPhase;

        for (Task singleTask : allTasks){
            size = size + singleTask.getSize();
            time = time + singleTask.getInvestedHours();
        }
        if (time == 0){
            speedByPhase = 0;
        }
        else {
            speedByPhase = size / time;
        }
        speed = String.valueOf(speedByPhase);
        return speed;
    }

    private int getCost(Phase singlePhase){
        int cost = 0;

        List<Task> allTasks = singlePhase.getTasks();
        for (Task singleTask : allTasks){
            cost = cost + singleTask.getInvestedHours();
        }
        return cost;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
