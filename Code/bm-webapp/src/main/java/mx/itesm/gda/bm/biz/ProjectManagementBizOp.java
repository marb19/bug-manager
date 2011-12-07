/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ProjectManagementBizOp.java 222 2010-10-14 04:35:34Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-13 23:35:34 -0500 (Wed, 13 Oct 2010) $
 * Last Version      : $Revision: 222 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 222 $
 */
public interface ProjectManagementBizOp extends BizOp {

    public List<Map<String, ?>> retrieveProjects(String userName);

    public int createProject(String projectName, String projectDescription,
            Date projectDueDate, Date projectPlannedDate);

    public void updateProject(int projectId, String projectName,
            String projectDescription, Date projectDueDate,
            Date projectPlannedDate);

    public void deleteProject(int project_id);

    public Map<String, ?> getProject(int project_id);
    
    public List<Map<String, ?>> getUsers(int projectId);

}
