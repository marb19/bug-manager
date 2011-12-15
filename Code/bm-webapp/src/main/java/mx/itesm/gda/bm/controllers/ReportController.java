/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ReportController.java 349 2010-11-18 03:50:05Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-11-17 21:50:05 -0600 (Wed, 17 Nov 2010) $
 * Last Version      : $Revision: 349 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.controllers;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import mx.itesm.gda.bm.biz.PhaseProdReportBizOp;
import mx.itesm.gda.bm.biz.PhaseTimeReportBizOp;
import mx.itesm.gda.bm.biz.PhaseYieldReportBizOp;
import mx.itesm.gda.bm.biz.DefectDensityUserReportBizOp;
import mx.itesm.gda.bm.biz.TotalDefectsTypeReportBizOp;
import mx.itesm.gda.bm.biz.DefectsInyRemBizOp;
import mx.itesm.gda.bm.biz.ReviewsEfficiencyBizOp;
import mx.itesm.gda.bm.biz.ReviewsEffortBizOp;
import mx.itesm.gda.bm.biz.ReviewsParetoBizOp;
import mx.itesm.gda.bm.biz.ReviewsSpeedBizOp;
import mx.itesm.gda.bm.biz.ReviewsYieldBizOp;
import mx.itesm.gda.bm.utils.UserLogged;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 349 $
 */
@Controller
@Scope("request")
public class ReportController extends BaseController {

    @Autowired
    private PhaseProdReportBizOp phaseProdReport;
    @Autowired
    private PhaseTimeReportBizOp phaseTimeReport;
    @Autowired
    private PhaseYieldReportBizOp phaseYieldReport;
    @Autowired
    private DefectDensityUserReportBizOp defectDensityReport;
    @Autowired
    private TotalDefectsTypeReportBizOp totalDefectsType;
    @Autowired
    private DefectsInyRemBizOp defectsInyectedRemoved;
    @Autowired
    private ReviewsYieldBizOp reviewsYield;
    @Autowired
    private ReviewsEffortBizOp reviewsEffort;
    @Autowired
    private ReviewsEfficiencyBizOp reviewsEfficiency;
    @Autowired
    private ReviewsSpeedBizOp reviewsSpeed;
    @Autowired
    private ReviewsParetoBizOp reviewsPareto;

    @RequestMapping(value = "/reportesGenerales",
    method = { RequestMethod.POST, RequestMethod.GET })
    @UserLogged
    @Transactional
    public String createGeneralReports(ModelMap model,
            @RequestParam(value= "report", defaultValue = "0", required = true) int report,
            @RequestParam(value= "project_id", defaultValue = "0", required = true) int project_id){

        String reportXML;
        if (report == 1){            
            reportXML = phaseTimeReport.getPhaseTimeReport(project_id);
        }
        else if (report == 2){
            reportXML = phaseProdReport.getPhaseProdReport(project_id);
        }
        else if (report == 3){
            reportXML = phaseYieldReport.getPhaseYieldReport(project_id); 
        }
        else{
            reportXML = phaseTimeReport.getPhaseTimeReport(project_id);
        }

        model.put("reportXML", reportXML);
        return null;
    }

    @RequestMapping(value = "/densidadDefectosUsuario",
    method = { RequestMethod.POST, RequestMethod.GET})
    @UserLogged
    @Transactional
    public String createDefectDensityReport(ModelMap model){
        String defectDensityXML = defectDensityReport.getDefectDensityReport();

        model.put("defectDensityXML", defectDensityXML);
        return null;
    }

    @RequestMapping(value = "/totalDefectosTipo",
    method = { RequestMethod.POST, RequestMethod.GET })
    @UserLogged
    @Transactional
    public String createTotalDefectsTypeReport(ModelMap model,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id,
            @RequestParam(value = "username", defaultValue = "", required = false) String username){                

        String totalDefectsXML = totalDefectsType.getTotalDefectsTypeReport(level, project_id, username);

        model.put("totalDefectsXML", totalDefectsXML);
        return null;
    }

    @RequestMapping(value = "/defectosInyectadosRemovidos",
    method = { RequestMethod.POST, RequestMethod.GET })
    @UserLogged
    @Transactional
    public String createDefectsInyectedRemovedReport(ModelMap model,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id,
            @RequestParam(value = "username", defaultValue = "", required = false) String username){

        String defectsInyectedRemovedXML = defectsInyectedRemoved.getDefectsInyRemReport(level, project_id, username);
        
        model.put("defectsInyectedRemovedXML", defectsInyectedRemovedXML);
        return null;
    }

    @RequestMapping(value = "/productividadCompuesta",
    method = { RequestMethod.POST, RequestMethod.GET })
    @UserLogged
    @Transactional
    public String createProductividadCompuestaReport(ModelMap model,
            @RequestParam(value= "level") int level){

        String productividadCompuestaXML = null;

        model.put("productividadCompuestaXML", productividadCompuestaXML);
        return null;
    }

    @RequestMapping(value = "/tecnicasDeteccion",
    method = { RequestMethod.POST, RequestMethod.GET })
    @UserLogged
    @Transactional
    public String createReviewsReport(ModelMap model,
            @RequestParam(value= "report") int report,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id){

        String ReviewsXML = null;

        switch (report){
            case 9:
                if (level == 2){
                    ReviewsXML = reviewsYield.getReviewsYieldReport(project_id);
                }
                else {
                    ReviewsXML = reviewsYield.getReviewsYieldReport();
                }
                break;
            case 10:
                if (level == 2){
                    ReviewsXML = reviewsEffort.getReviewsEffortReport(project_id);
                }
                else {
                    ReviewsXML = reviewsEffort.getReviewsEffortReport();
                }
                break;
            case 11:
                if (level == 2){
                    ReviewsXML = reviewsEfficiency.getReviewsEfficiencyReport(project_id);
                }
                else {
                    ReviewsXML = reviewsEfficiency.getReviewsEfficiencyReport();
                }
                break;
            case 12:
                if (level == 2){
                    ReviewsXML = reviewsSpeed.getReviewsSpeedReport(project_id);
                }
                else {
                    ReviewsXML = reviewsSpeed.getReviewsSpeedReport();
                }
                break;
            case 13:
                if (level == 2){
                    ReviewsXML = reviewsPareto.getReviewsParetoReport(project_id);
                }
                else {
                    ReviewsXML = reviewsPareto.getReviewsParetoReport();
                }
                break;
        }

        model.put("ReviewsXML", ReviewsXML);
        return null;
    }
}
