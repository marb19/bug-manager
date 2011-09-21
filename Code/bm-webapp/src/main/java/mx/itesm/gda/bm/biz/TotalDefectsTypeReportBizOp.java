/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.itesm.gda.bm.biz;

/**
 *
 * @author Administrator
 */
public interface TotalDefectsTypeReportBizOp extends BizOp{

    public String getTotalDefectsTypeReport(int level, int project_id, String username);
    
}
