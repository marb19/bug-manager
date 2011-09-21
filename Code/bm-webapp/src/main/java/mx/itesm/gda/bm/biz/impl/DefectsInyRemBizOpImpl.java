/* ***************************************************************************
 *   Copyright (c) 2010 BM
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DefectsInyRemBizOpImpl.java 279 2011-09-21 15:43:29Z lalo.campos@gmail.com $
 * Last Revised By   : $Author: lalo.campos@gmail.com $
 * Last Checked In   : $Date: 2011-09-09 10:43:29 -0500 (Wed, 21 Sep 2011) $
 * Last Version      : $Revision: 0 $
 *
 * Original Author   : Eduardo Campos
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz.impl;

import mx.itesm.gda.bm.biz.DefectsInyRemBizOp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import mx.itesm.gda.bm.model.Defect;
import mx.itesm.gda.bm.model.dao.DefectDAO;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.Phase;
import mx.itesm.gda.bm.model.dao.PhaseDAO;
/**
 *
 * @author $Author: lalo.campos@gmail.com $
 * @version $Revision: 0 $
 */
@Scope("prototype")
@Component
public class DefectsInyRemBizOpImpl extends AbstractBizOp implements DefectsInyRemBizOp{

    @Autowired
    private PhaseDAO phaseDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.MANDATORY)
    public String getDefectsInyRemReport(int level, int project_id, String username){
        String xmlData = null;
        List<Phase> phases = null;
        Project project = null;

        switch(level){
            case 1:
                phases = phaseDAO.getAll();
                xmlData = getReportByUser(phases, username);
                break;
            case 2:
                project = projectDAO.findById(project_id);
                phases = project.getPhases();
                xmlData = getReportByProject(phases, project_id);
                break;
            case 3:
                phases = phaseDAO.getAll();
                xmlData = getReportByCompany(phases);
                break;
            default:
                phases = phaseDAO.getAll();
                xmlData = getReportByCompany(phases);
                break;
        }

        return xmlData;
    }

     private String getReportByUser(List<Phase> listOfPhases, String username){
        String xmlData = null;

        return xmlData;
    }

    private String getReportByProject(List<Phase> listOfPhases, int project_id){
        String xmlData = null;

        return xmlData;
    }

    private String getReportByCompany(List<Phase> listOfPhases){
        String xmlData = null;

        return xmlData;
    }
}
