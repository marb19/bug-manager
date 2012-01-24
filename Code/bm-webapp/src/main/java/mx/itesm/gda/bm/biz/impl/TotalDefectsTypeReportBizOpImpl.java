/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TotalDefectsTypeReportBizOpImpl.java 279 2011-09-20 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2011-09-09 10:43:29 -0500 (Tue, 20 Sep 2011) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import mx.itesm.gda.bm.biz.TotalDefectsTypeReportBizOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.DefectType;
import mx.itesm.gda.bm.model.dao.DefectTypeDAO;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Component
public class TotalDefectsTypeReportBizOpImpl extends AbstractBizOp implements TotalDefectsTypeReportBizOp{

    @Autowired
    private DefectTypeDAO defectTypeDAO;

    @Autowired
    private DefectDAO defectByUserDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getTotalDefectsTypeReport(int level, int project_id, String username){
        String xmlData = null;

        List<DefectType> allDefectTypes = defectTypeDAO.getAll();
        switch(level){
            case 1:
                xmlData = getReportByUser(allDefectTypes, username);
                break;
            case 2:
                xmlData = getReportByProject(allDefectTypes, project_id);
                break;
            case 3:
                xmlData = getReportByCompany(allDefectTypes);
                break;
            default:
                xmlData = getReportByCompany(allDefectTypes);
                break;
        }

        return xmlData;
    }
    
    private String getReportByUser(List<DefectType> listOfTypes, String username){
        String xmlData = null;
        String categories = null, totalDefects = null, totalEffort = null;
        ArrayList<Integer> totalDefectsByType = new ArrayList<Integer>();
        ArrayList<String> defectTypes = new ArrayList<String>();
        ArrayList<Long> totalEffortByType = new ArrayList<Long>();

        User user = userDAO.findByUserName(username);
        String completeName = user.getFullName();
        for(DefectType dType : listOfTypes){
            defectTypes.add(dType.getDefectTypeName());
            int defectType = dType.getDefectTypeId();
            List<Defect> defectsByTypeAndUser = defectByUserDAO.searchByTypeAndUser(defectType, username);
            totalDefectsByType.add(defectsByTypeAndUser.size());
            long effortByType = 0;
            for (Defect singleDefect : defectsByTypeAndUser){
                effortByType = effortByType + singleDefect.getInvestedHours();
            }
            totalEffortByType.add(effortByType);
        }

        xmlData="<chart caption='Total de defectos por tipo para el usuario " +  completeName + "'" + " xAxisName='Tipo de defecto' yAxisName='Cantidad y Esfuerzo' bgAlpha='0,0'>";
        categories= "<categories>";
        totalDefects= "<dataset seriesName='Cantidad de Defectos'>";
        totalEffort= "<dataset seriesName='Esfuerzo de Remoción'>";

        for(int i = 0; i < defectTypes.size(); i++){
            categories+="<category name='" + defectTypes.get(i) + "' />";
            totalDefects+="<set value='" + totalDefectsByType.get(i) + "' />";
            totalEffort+="<set value='" + totalEffortByType.get(i) + "' />";
        }

        categories+="</categories>";
        totalDefects+="</dataset>";
        totalEffort+="</dataset>";
        xmlData+= categories  + totalDefects + totalEffort + "</chart>";
        return xmlData;
    }

    private String getReportByProject(List<DefectType> listOfTypes, int project_id){
        String xmlData = null;
        String categories = null, totalDefects = null, totalEffort = null;
        ArrayList<Long> totalEffortByType = new ArrayList<Long>();
        ArrayList<Integer> totalDefectsByType = new ArrayList<Integer>();
        ArrayList<String> defectTypes = new ArrayList<String>();

        Project project = projectDAO.findById(project_id);
        String projectName = project.getProjectName();
        for(DefectType dType : listOfTypes){
            defectTypes.add(dType.getDefectTypeName());
            int defectType = dType.getDefectTypeId();
            List<Defect> defectsByTypeAndProject = defectByUserDAO.searchByTypeAndProject(defectType, project_id);
            totalDefectsByType.add(defectsByTypeAndProject.size());
            long effortByType = 0;
            for (Defect singleDefect : defectsByTypeAndProject){
                effortByType = effortByType + singleDefect.getInvestedHours();
            }
            totalEffortByType.add(effortByType);
        }

        xmlData = "<chart caption='Total de defectos por tipo para el proyecto " +  projectName + "'" + " xAxisName='Tipo de defecto' yAxisName='Cantidad y Esfuerzo' bgAlpha='0,0'>";
        categories = "<categories>";
        totalDefects = "<dataset seriesName='Cantidad de Defectos'>";
        totalEffort = "<dataset seriesName='Esfuerzo de Remoción'>";

        for(int i = 0; i < defectTypes.size(); i++){
            categories+="<category name='" + defectTypes.get(i) + "' />";
            totalDefects+="<set value='" + totalDefectsByType.get(i) + "' />";
            totalEffort+="<set value='" + totalEffortByType.get(i) + "' />";
        }

        categories+="</categories>";
        totalDefects+="</dataset>";
        totalEffort+="</dataset>";
        xmlData+= categories  + totalDefects + totalEffort + "</chart>";        

        return xmlData;
    }

    private String getReportByCompany(List<DefectType> listOfTypes){
        String xmlData = null;
        String categories = null, totalDefects = null, totalEffort = null;
        ArrayList<Integer> totalDefectsByType = new ArrayList<Integer>();
        ArrayList<String> defectTypes = new ArrayList<String>();
        ArrayList<Long> totalEffortByType = new ArrayList<Long>();

        for(DefectType dType : listOfTypes){
            defectTypes.add(dType.getDefectTypeName());
            int defectType = dType.getDefectTypeId();
            List<Defect> defectsByType = defectByUserDAO.searchByType(defectType);
            totalDefectsByType.add(defectsByType.size());
            long effortByType = 0;
            for (Defect singleDefect : defectsByType){
                effortByType = effortByType + singleDefect.getInvestedHours();
            }
            totalEffortByType.add(effortByType);
        }

        xmlData += "<chart caption='Total de defectos por tipo para la empresa' xAxisName='Tipo de defecto' yAxisName='Cantidad y Esfuerzo' bgAlpha='0,0'>";
        categories = "<categories>";
        totalDefects = "<dataset seriesName='Cantidad de Defectos'>";
        totalEffort = "<dataset seriesName='Esfuerzo de Remoción'>";

        for(int i = 0; i < defectTypes.size(); i++){
            categories+="<category name='" + defectTypes.get(i) + "' />";
            totalDefects+="<set value='" + totalDefectsByType.get(i) + "' />";
            totalEffort+="<set value='" + totalEffortByType.get(i) + "' />";
        }

        categories+="</categories>";
        totalDefects+="</dataset>";
        totalEffort+="</dataset>";
        xmlData+= categories  + totalDefects + totalEffort + "</chart>";        

        return xmlData;
    }
}
