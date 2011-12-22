/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskDAO.java 113 2010-09-21 05:24:13Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-09-21 00:24:13 -0500 (Tue, 21 Sep 2010) $
 * Last Version      : $Revision: 113 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao;

import java.util.List;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskType;
import mx.itesm.gda.bm.model.TaskState;
/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 113 $
 */
public interface TaskDAO extends BaseItemDAO<Task> {

    public Task findById(int task_id);

    public List<Task> searchByTaskName(String task_name);

    public List<Task> getTasksByType(TaskType type);

    public List<Task> getTasksByState(TaskState state);

    public List<Task> getTasksByTypeAndState(TaskType type, TaskState state);

    public List<Task> getTasksByTypeStateProject(TaskType type,
            TaskState state, int project_id);
    
}
