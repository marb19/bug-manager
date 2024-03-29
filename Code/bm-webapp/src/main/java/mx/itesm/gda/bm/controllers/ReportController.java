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

import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.*;
import mx.itesm.gda.bm.utils.UserLogged;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
    private DefectDensityReportBizOp defectDensityReport;
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
    @Autowired
    private ComposedProdBizOp composedProd;
    @Autowired
    private ROIProjectBizOp roiProject;
    @Autowired
    private ROITecnicasBizOp roiTecnicas;
    @Autowired
    private QualityCostBizOp qualityCost;
    @Autowired
    private GeneralSummaryBizOp generalSummary;

    @RequestMapping(value = "/reportesGenerales",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createGeneralReports(ModelMap model,
            @RequestParam(value= "report", defaultValue = "0", required = true) int report,
            @RequestParam(value= "project_id", defaultValue = "0", required = true) int project_id){

        String reportXML = null, text = null;
        if (report == 1){            
            reportXML = phaseTimeReport.getPhaseTimeReport(project_id);
            text = "En la gráfica se muestra el porcentaje de tiempo invertido "
                    + "en cada fase del proyecto. La suma de todas las fases debe ser 100.";
        }
        else if (report == 2){
            reportXML = phaseProdReport.getPhaseProdReport(project_id);
            text = "La gráfica representa el número de productos que se realizan por hora. "
                    + "Para la etapa de diseño puede ser documentos, para la de pruebas "
                    + "casos de prueba y para la de codificación líneas de código.";
        }
        else if (report == 3){
            reportXML = phaseYieldReport.getPhaseYieldReport(project_id);
            text = "La gráfica muestra el porcentaje de defectos encontrados en "
                    + "cada fase del proyecto respecto al total de defectos encontrados en el mismo. ";
        }        

        model.put("text", text);
        model.put("reportXML", reportXML);
        return null;
    }

    @RequestMapping(value = "/resumenGeneral",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createSummaryReport(ModelMap model, 
            @RequestParam(value= "project_id", defaultValue = "0",
            required = true) int project_id){
        
        String text = "Las tablas representan un resumen general del proyecto por fases.";
        List<Map<String, ?>> summary = generalSummary.getSummaryReport(project_id);
        String projectName = generalSummary.getProjectName(project_id);
        Map<String, ?> defectSummary = generalSummary.getDefectSummary(project_id);

        model.put("text", text);
        model.put("projectName", projectName);
        model.put("summary", summary);
        model.put("defectSummary", defectSummary);
        return null;
    }

    @RequestMapping(value = "/densidadDefectos",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createDefectDensityReport(ModelMap model,
            @RequestParam(value= "level2") int level){

        String defectDensityXML = defectDensityReport.getDefectDensityReport(level);
        String text = "La cantidad de defectos que se cometen por cada 1000 líneas de código. ";

        model.put("text", text);
        model.put("defectDensityXML", defectDensityXML);
        return null;
    }

    @RequestMapping(value = "/totalDefectosTipo",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createTotalDefectsTypeReport(ModelMap model,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id,
            @RequestParam(value = "username", defaultValue = "", required = false) String username){                

        String totalDefectsXML = totalDefectsType.getTotalDefectsTypeReport(level, project_id, username);
        String text = "El total de defectos que se han removido y su esfuerzo de remoción, "
                + "organizados por tipo, "
                + "ya sea a nivel usuario, proyecto o empresa.";

        model.put("text", text);
        model.put("totalDefectsXML", totalDefectsXML);
        return null;
    }

    @RequestMapping(value = "/defectosInyectadosRemovidos",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createDefectsInyectedRemovedReport(ModelMap model,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id,
            @RequestParam(value = "username", defaultValue = "", required = false) String username){

        String defectsInyectedRemovedXML = defectsInyectedRemoved.getDefectsInyRemReport(level, project_id, username);
        String text = "Total de defectos inyectados y removidos por fase, "
                + "ya sea a nivel usuario, proyecto o empresa.";

        model.put("text", text);
        model.put("defectsInyectedRemovedXML", defectsInyectedRemovedXML);
        return null;
    }

    @RequestMapping(value = "/productividadCompuesta",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createProductividadCompuestaReport(ModelMap model,
            @RequestParam(value= "level2") int level){

        String productividadCompuestaXML = null;
        String text = "La gráfica compara la productividad de un programador "
                + "contra su productividad compuesta, o la productividad de un proyecto contra su productivdad compuesta. "
                + "La productividad compuesta está "
                + "ponderada con el tiempo que se tomó en corregir los defectos que inyectó al momento de programar. "
                + "Solo se consideran las actividades de desarrollo completadas, así como los defectos corregidos."
                + " La gráfica se muestra a nivel usuario, proyecto o empresa.";

        productividadCompuestaXML = composedProd.getComposedProdReport(level);

        model.put("text", text);
        model.put("productividadCompuestaXML", productividadCompuestaXML);
        return null;
    }

    @RequestMapping(value = "/roiProyecto",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createROIProyectoReport(ModelMap model,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id){

        String roiProyectoXML = null;
        String text = "Este reporte es un análisis del retorno de inversión de las actividades "
                + "de calidad del proyecto. Te ayuda a saber que tantos recursos se ahorraron "
                + "gracias a la implementación de estas técnicas de calidad. Te "
                + "ayuda a comparar en el caso que no se hubieran "
                + "utilizado las actividades de calidad y los defectos se hubieran encontrado "
                + "en pruebas o en producción. Solo se consideran actividades completadas que pertenezcan a "
                + "fases de revisión y defectos removidos.";

        if (level == 2){
            roiProyectoXML = roiProject.getROIProjectReport(project_id);
        }
        else {
            roiProyectoXML = roiProject.getROIProjectReport(0);
        }

        model.put("text", text);
        model.put("roiProyectoXML", roiProyectoXML);
        return null;
    }

    @RequestMapping(value = "/roiTecnicas",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createROITecnicasReport(ModelMap model,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id){

        String roiTecnicasXML = null;
        String text = "Este reporte nos ayuda a comparar el esfuerzo que tomó cierta actividad "
                + "de calidad, contra el tiempo que ahorró en caso de que los errores encontrados "
                + "con la respectiva técnica hubieran pasado a producción (ya que se consideran las actividades de pruebas). "
                + "Solo se toman en cuenta las actividades de revisión y pruebas completadas, así como los defectos "
                + "corregidos por estas actividades.";

        if (level == 2){
            roiTecnicasXML = roiTecnicas.getROITecnicasReport(project_id);
        }
        else {
            roiTecnicasXML = roiTecnicas.getROITecnicasReport(0);
        }

        model.put("text", text);
        model.put("roiTecnicasXML", roiTecnicasXML);
        return null;
    }

    @RequestMapping(value = "/costoCalidad",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createQualityCostReport(ModelMap model,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id){

        String qualityCostXML = null;
        String text = "Esta gráfica nos ayuda a comparar los costos de la conformidad y NO conformidad. "
                + "En los de conformidad se consideran las actividades completadas en fases de revisión y sus defectos corregidos. "
                + "En los de NO conformidad se consideran las actividades completadas en fases de prueba y mantenimiento "
                + "y sus defectos corregidos en estas fases.";

        if (level == 2){
            qualityCostXML = qualityCost.getQualityCostReport(project_id);
        }
        else {
            qualityCostXML = qualityCost.getQualityCostReport(0);
        }

        model.put("text", text);
        model.put("qualityCostXML", qualityCostXML);
        return null;
    }

    @RequestMapping(value = "/tecnicasDeteccion",
    method = { RequestMethod.POST })
    @UserLogged
    @Transactional
    public String createReviewsReport(ModelMap model,
            @RequestParam(value= "report") int report,
            @RequestParam(value= "level") int level,
            @RequestParam(value= "project_id", defaultValue = "0", required = false) int project_id){

        String ReviewsXML = null, text = null;

        switch (report){
            case 9:
                if (level == 2){
                    ReviewsXML = reviewsYield.getReviewsYieldReport(project_id);
                    text = "La gráfica muestra el porcentaje de defectos encontrados "
                            + "por cada técnica de detección respecto al total de defectos del proyecto.";
                }
                else {
                    ReviewsXML = reviewsYield.getReviewsYieldReport(0);
                    text = "La gráfica muestra el porcentaje de defectos encontrados "
                            + "por cada técnica de detección respecto al total de defectos de la empresa.";
                }
                break;
            case 10:
                if (level == 2){
                    ReviewsXML = reviewsEffort.getReviewsEffortReport(project_id);
                    text = "La gráfica muestra el porcentaje de esfuerzo por cada "
                            + "técnica de detección respecto al total de esfuerzo del proyecto.";
                }
                else {
                    ReviewsXML = reviewsEffort.getReviewsEffortReport(0);
                    text = "La gráfica muestra el porcentaje de esfuerzo por cada "
                            + "técnica de detección respecto al total de esfuerzo de la empresa.";
                }
                break;
            case 11:
                if (level == 2){
                    ReviewsXML = reviewsEfficiency.getReviewsEfficiencyReport(project_id);
                    text = "La gráfica muestra el número de defectos removidos por hora "
                            + "por cada técnica de detección para el proyecto.";
                }
                else {
                    ReviewsXML = reviewsEfficiency.getReviewsEfficiencyReport(0);
                    text = "La gráfica muestra el número de defectos removidos por hora "
                            + "por cada técnica de detección para la empresa.";
                }
                break;
            case 12:
                if (level == 2){
                    ReviewsXML = reviewsSpeed.getReviewsSpeedReport(project_id);
                    text = "La gráfica muestra la velocidad de revisión "
                            + "de cada técnica de detección para el proyecto.";
                }
                else {
                    ReviewsXML = reviewsSpeed.getReviewsSpeedReport(0);
                    text = "La gráfica muestra la velocidad de revisión "
                            + "de cada técnica de detección para la empresa.";
                }
                break;
            case 13:
                if (level == 2){
                    ReviewsXML = reviewsPareto.getReviewsParetoReport(project_id);
                    text = "En la gráfica se muestra la cantidad de defectos removidos "
                            + "por cada técnica de detección (de mayor a menor) para el proyecto.";
                }
                else {
                    ReviewsXML = reviewsPareto.getReviewsParetoReport(0);
                    text = "En la gráfica se muestra la cantidad de defectos removidos "
                            + "por cada técnica de detección (de mayor a menor) para la empresa.";
                }
                break;
        }

        model.put("text", text);
        model.put("report", report);
        model.put("ReviewsXML", ReviewsXML);
        return null;
    }
}
