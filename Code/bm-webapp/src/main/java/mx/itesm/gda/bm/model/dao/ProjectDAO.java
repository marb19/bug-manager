/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ProjectDAO.java 206 2010-10-12 02:56:43Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-11 21:56:43 -0500 (Mon, 11 Oct 2010) $
 * Last Version      : $Revision: 206 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao;

import java.util.List;
import mx.itesm.gda.bm.model.Project;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 206 $
 */
public interface ProjectDAO extends BaseItemDAO<Project> {

    public Project findById(int project_id);

    public List<Project> searchByProjectName(String project_name);

    public List<Project> searchByUserNameEnrolled(String user_name);

}
