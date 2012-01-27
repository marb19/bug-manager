/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskManagementBizOp.java 315 2010-11-12 03:19:45Z marco.rangel@gmail.com $
 * Last Revised By   : $Author: marco.rangel@gmail.com $
 * Last Checked In   : $Date: 2010-11-11 21:19:45 -0600 (Thu, 11 Nov 2010) $
 * Last Version      : $Revision: 315 $
 *
 * Original Author   : Marco Antonio Rangel Bocardo
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.biz;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 *
 * @author $Author: marco.rangel@gmail.com $
 * @version $Revision: 315 $
 */
public interface TaskManagementBizOp extends BizOp {

    public int createTask(String taskName, int project, String description,
            String assignedUser, int estimatedHours, Date startDate, Date endDate, String taskType, int phase);

    public int modifyTask(int taskID, String taskName, String description, String assignedUser, String status, int estimatedHours, int investedHours, int remainingHours, Date startDate, Date endDate, String taskType, int phaseID);

    public Map<String, ?> getTask(Integer taskID);

    public void deleteTask(Integer taskID);

    public List<Map<String, ?>> retrieveTasks(int projectID);
    
    public List<Map<String, ?>> retrieveTasks(int projectID, int phaseID);

    public int addComment(int taskID, String commentAuthor, String newComment);

    public List<Map<String, ?>> retrieveComments(int taskID);

    public List<String> getTypes();

}
