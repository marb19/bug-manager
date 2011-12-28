/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.itesm.gda.bm.biz;

import java.util.List;
import java.util.Map;
/**
 *
 * @author Administrator
 */
public interface GeneralSummaryBizOp extends BizOp{
    public List<Map<String, ?>> getSummaryReport(int project_id);

    public String getProjectName(int project_id);
}
