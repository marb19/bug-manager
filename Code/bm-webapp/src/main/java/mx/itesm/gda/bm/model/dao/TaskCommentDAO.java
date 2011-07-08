/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskCommentDAO.java 113 2010-09-21 05:24:13Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-09-21 00:24:13 -0500 (Tue, 21 Sep 2010) $
 * Last Version      : $Revision: 113 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao;

import java.util.List;
import mx.itesm.gda.bm.model.TaskComment;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 113 $
 */
public interface TaskCommentDAO extends BaseItemDAO<TaskComment> {

    public TaskComment findById(int task_comment_id);

    public List<TaskComment> searchByCommentText(String comment_text);

}
