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
import java.util.ArrayList;
import java.util.List;
import mx.itesm.gda.bm.biz.ComposedProdBizOp;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.DefectState;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
/**
 *
 * @author Administrator
 */
@Scope("prototype")
@Component
public class ComposedProdBizOpImpl extends AbstractBizOp implements ComposedProdBizOp{

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private DefectDAO defectDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getComposedProdReport(int level){
        String xmlData = null;

        switch(level){
            case 1:
                xmlData = getReportByUser();
                break;
            case 2:
                xmlData = getReportByProject();
                break;
            case 3:
                xmlData = getReportByCompany();
                break;
            default:
                xmlData = getReportByCompany();
                break;
        }

        return xmlData;
    }

    private String getReportByUser(){
        String xmlData = null;

        return xmlData;
    }

    private String getReportByProject(){
        String xmlData = null;
        String category, productivity, composedProductivity;
        ArrayList<String> projectsNames = new ArrayList<String>();
        ArrayList<Double> prods = new ArrayList<Double>();
        ArrayList<Double> composedProds = new ArrayList<Double>();

        List<Project> allProjects = projectDAO.getAll();

        for(Project singleProject : allProjects){
            long LOC = 0, effort = 0, correctionEffort = 0;
            double prod = 0, composedProd = 0;

            int project_id = singleProject.getProjectId();
            projectsNames.add(singleProject.getProjectName());

            List<Task> allTasks = taskDAO.getTasksByTypeStateProject(TaskType.DEVELOPMENT,
                    TaskState.COMPLETED, project_id);
            List<Defect> allDefects = defectDAO.searchByStateAndProject(DefectState.FIXED, project_id);

            for(Task singleTask : allTasks){
                LOC = LOC + singleTask.getSize();
                effort = effort + singleTask.getInvestedHours();
            }

            for (Defect singleDefect : allDefects){
                correctionEffort = correctionEffort + singleDefect.getInvestedHours();
            }

            if (effort == 0){
                prods.add(0.0);
            }
            else
            {
                prods.add(roundNumber((double)LOC / (double)effort));
            }
            if (effort == 0 && correctionEffort == 0){
                composedProds.add(0.0);
            }
            else
            {
                composedProds.add(roundNumber(((double)LOC) / ((double)effort + (double)correctionEffort)));
            }
        }

        xmlData= "<chart caption='Productividad y productividad compuesta por proyecto(s)' xAxisName='' yAxisName='LOC' bgAlpha='0,0'>";
        category= "<categories>";
        productivity= "<dataset seriesName='Productividad'>";
        composedProductivity= "<dataset seriesName='Productividad Compuesta'>";

        for(int i = 0; i < projectsNames.size(); i++){
            category+="<category name='" + projectsNames.get(i) + "' />";
            productivity+="<set value='" + prods.get(i) + "' />";
            composedProductivity+="<set value='" + composedProds.get(i) + "' />";
        }

        category+="</categories>";
        productivity+="</dataset>";
        composedProductivity+="</dataset>";
        xmlData+= category + productivity + composedProductivity + "</chart>";
        
        return xmlData;
    }

    private String getReportByCompany(){
        String xmlData = null;
        long LOC = 0, effort = 0, correctionEffort = 0;
        double prod = 0, composedProd = 0;
        String category, productivity, composedProductivity;

        List<Task> allTasks = taskDAO.getTasksByTypeAndState(TaskType.DEVELOPMENT, TaskState.COMPLETED);
        List<Defect> allDefects = defectDAO.searchByState(DefectState.FIXED);

        for(Task singleTask : allTasks){
            LOC = LOC + singleTask.getSize();
            effort = effort + singleTask.getInvestedHours();
        }

        for (Defect singleDefect : allDefects){
            correctionEffort = correctionEffort + singleDefect.getInvestedHours();
        }

        if (effort == 0){
            prod = 0;
        }
        else
        {
            prod = roundNumber((double)LOC / (double)effort);
        }
        if (effort == 0 && correctionEffort == 0){
            composedProd = 0;
        }
        else
        {
            composedProd = roundNumber(((double)LOC) / ((double)effort + (double)correctionEffort));
        }

        xmlData= "<chart caption='Productividad y productividad compuesta para la empresa' xAxisName='' yAxisName='LOC' bgAlpha='0,0'>";
        category= "<categories>";
        productivity= "<dataset seriesName='Productividad'>";
        composedProductivity= "<dataset seriesName='Productividad Compuesta'>";
        category+="<category name='' />";
        productivity+="<set value='" + prod + "' />";
        composedProductivity+="<set value='" + composedProd + "' />";
        category+="</categories>";
        productivity+="</dataset>";
        composedProductivity+="</dataset>";
        xmlData+= category + productivity + composedProductivity + "</chart>";

        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
