/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectDensityUserReportBizOpImpl.java 279 2011-09-09 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2011-09-09 10:43:29 -0500 (Fri, 09 Sep 2011) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import java.text.DecimalFormat;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import mx.itesm.gda.bm.biz.DefectDensityUserReportBizOp;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.DefectState;

/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Component
public class DefectDensityUserReportBizOpImpl extends AbstractBizOp implements DefectDensityUserReportBizOp{

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getDefectDensityReport(){
        String xmlData = null;
        ArrayList<String> usersNames = new ArrayList<String>();
        ArrayList<Double> defectDensityByUsername = new ArrayList<Double>();

        List<User> allDevelopers = userDAO.getAllDevelopers();
        for(User user : allDevelopers){
            usersNames.add(user.getFullName());
            List<Defect> defectsByUser = user.getAssignedDefects();
            int totalDefects = 0;
            for (Defect singleDefect : defectsByUser){
                if (singleDefect.getDefectState() == DefectState.ACCEPTED
                        || singleDefect.getDefectState() == DefectState.FIXED){
                    totalDefects++;
                }
            }
            List<Task> tasksByUser = user.getAssignedTasks();
            int totalLOC = 0;
            for (Task singleTask : tasksByUser){
                if ((singleTask.getTaskType() == TaskType.DEVELOPMENT) &&
                        (singleTask.getTaskState() == TaskState.STARTED
                        || singleTask.getTaskState() == TaskState.COMPLETED)){
                    totalLOC += singleTask.getSize();
                }
            }
            if (totalLOC == 0){
                defectDensityByUsername.add(0.0);
            } else {
                defectDensityByUsername.add(roundNumber(1000 * ((double)totalDefects / (double)totalLOC)));
            }
        }
        xmlData += "<chart caption='Densidad de defectos por Usuario' xAxisName='Usuario' yAxisName='Densidad de defectos' bgAlpha='0,0'>";

        for(int i = 0; i < usersNames.size(); i++){
            xmlData += "<set label='" + usersNames.get(i) + "' value='" + defectDensityByUsername.get(i) + "' />";
        }

        xmlData+= "</chart>";
        return xmlData;
    }

    private double roundNumber(double d){
        DecimalFormat twoDecimals = new DecimalFormat("#.##");
        return Double.valueOf(twoDecimals.format(d));
    }
}
