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

    private static final Map<String, String> REPORT_VIEW_MAP;

    static {
        Map<String, String> m = new HashMap<String, String>();
        m.put("earnedValue", "EV");
        m.put("tasksByProject", "avanceProyecto");
        m.put("tasksByUser", "avancePersona");
        REPORT_VIEW_MAP = Collections.unmodifiableMap(m);
    }

    @RequestMapping(value = "/report/{report}.{format}",
    method = { RequestMethod.POST, RequestMethod.GET })
    @UserLogged
    @Transactional
    public ModelAndView buildReport(
            @PathVariable String report, @PathVariable String format,
            @RequestParam int project_id,
            @RequestParam(required = false) String username) {

        Map<String, Object> model = new HashMap<String, Object>();
        model.put("format", format);
        model.put("project_id", project_id);
        model.put("username", username);

        String viewname = REPORT_VIEW_MAP.get(report);

        return new ModelAndView(viewname, model);
    }

    @RequestMapping(value = "/reportesGenerales",
    method = { RequestMethod.POST, RequestMethod.GET })
    @UserLogged
    @Transactional
    public String createGeneralReports(ModelMap model,
            @RequestParam int project_id){

        String phaseProdXML = phaseProdReport.getPhaseProdReport(project_id);
        String phaseTimeXML = phaseTimeReport.getPhaseTimeReport(project_id);
        String phaseYieldXML = phaseYieldReport.getPhaseYieldReport(project_id);     

        model.put("phaseTimeXML", phaseTimeXML);
        model.put("phaseProdXML", phaseProdXML);
        model.put("phaseYieldXML", phaseYieldXML);
        return null;
    }

    @RequestMapping(value = "/densidadDefectosUsuario",
    method = { RequestMethod.POST, RequestMethod.GET })
    @UserLogged
    @Transactional
    public String createDefectDensityReport(ModelMap model,
            @RequestParam String username){
        String defectDensityXML = null;

        model.put("defectDensityXML", defectDensityXML);
        return null;
    }
}
