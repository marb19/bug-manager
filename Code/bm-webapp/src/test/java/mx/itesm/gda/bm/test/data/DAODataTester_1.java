/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: DAODataTester.java 226 2010-10-15 04:56:51Z inzunzo $
 * Last Revised By   : $Author: inzunzo $
 * Last Checked In   : $Date: 2010-10-14 23:56:51 -0500 (Thu, 14 Oct 2010) $
 * Last Version      : $Revision: 226 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.test.data;

import java.util.Calendar;
import java.util.Date;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.TaskComment;
import mx.itesm.gda.bm.model.TaskCompletionReport;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.dao.TaskCommentDAO;
import mx.itesm.gda.bm.model.dao.TaskCompletionReportDAO;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;
import org.junit.After;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: inzunzo $
 * @version $Revision: 226 $
 */
public abstract class DAODataTester_1 {

    protected static final String[] user_usernames = { "natalia", "gisela",
        "marco", "alex", "arturo" };

    protected static final String[] user_passwords = { "efrain", "hialita",
        "inzunza", "pacatelas", "rangel" };

    protected static final String[] user_fullnames = { "NataliaCZT",
        "GiselaHSS", "MarcoARB", "AlejandroVC", "ArturoI" };

    protected static final String[] user_emails = { "natalia@bipolar.mx",
        "gisela@bipolar.mx", "marco@bipolar.mx", "alex@bipolar.mx",
        "arturo@bipolar.mx" };

    protected static final Boolean[] user_administrators = { Boolean.TRUE,
        Boolean.TRUE, Boolean.FALSE, Boolean.FALSE, Boolean.FALSE };

    protected static final String[] user_prtickets = { "abcdef", null, "cdefgh",
        null, null };

    protected static final String[] project_projectname = { "Alan Parsons",
        "Manhatan", "Iridium", "Titanic", "Ariane 5" };

    protected static final String[] project_projectdescription = {
        "Eye in the Sky", "Blow up the sky", "Recurring theme", "Unsinkable",
        "Overflowing the sky" };

    protected static final String[] task_taskname = { "Sirius Task",
        "Play Games", "I Robot", "Enrich uranium", "Test Nuke", "Load Missiles",
        "Build Satelite", "Design Phone", "Launch Rocket", "Sell Tickets",
        "Find Iceberg", "Hire the Band", "Launch Rocket", "Blow up rocket",
        "Blame silly bug" };

    protected static final String[] task_taskdescription = { "Hit song",
        "Games people play", "Mr Roboto", "Call Iran",
        "Call India and Pakistan", "Get the soviets", "Get Dr. Space",
        "Get Steve Jobs", "Get the chinesse", "Get Ticketmaster", "Get GPS",
        "Get Craigslist", "Ditch the chinesse", "Get Chuck Nurris",
        "Get Bill Gates" };

    protected static final TaskState[] task_taskstate = { TaskState.NOT_STARTED,
        TaskState.NOT_STARTED, TaskState.NOT_STARTED, TaskState.NOT_STARTED,
        TaskState.NOT_STARTED, TaskState.NOT_STARTED, TaskState.NOT_STARTED,
        TaskState.NOT_STARTED, TaskState.NOT_STARTED, TaskState.NOT_STARTED,
        TaskState.NOT_STARTED, TaskState.NOT_STARTED, TaskState.NOT_STARTED,
        TaskState.NOT_STARTED, TaskState.NOT_STARTED };

    protected static final String[] task_assigneduser_username = { "natalia",
        "gisela", "marco", "alex", "arturo", "natalia", "gisela", "marco",
        "alex", "arturo", "natalia", "gisela", "marco", "alex", "arturo" };

    protected static final Integer[] task_project_projectindex = { 0, 0, 0, 1,
        1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };

    protected static final Integer[] task_estimatedhours = { 10, 10, 10, 10, 10,
        10, 10, 10, 10, 10, 10, 10, 10, 10, 10 };

    protected static final Integer[] task_investedhours = { 0, 0, 0, 3, 5, 0, 0,
        0, 0, 0, 0, 0, 0, 0, 0 };

    protected static final Integer[] taskcomment_task_taskindex = { 1, 1, 2 };

    protected static final String[] taskcomment_author_username = { "natalia",
        "gisela", "marco" };

    protected static final String[] taskcomment_commenttext = {
        "Esta muy cañón", "No es para tanto", "No inventes Natalia" };

    protected static final Integer[] taskreport_task_taskindex = { 2, 3 };

    protected static final String[] taskreport_reportinguser_username = {
        "alex", "arturo" };

    protected static final Integer[] taskreport_investedhourstodate = { 3, 5 };

    protected Date[] user_prtexpirations;

    protected Integer[] project_projectid;

    protected Date[] project_projectduedate;

    protected Date[] project_projectplanneddate;

    protected Integer[] task_taskid;

    protected Integer[] task_projec_projecttid;

    protected Date[] task_startdate;

    protected Date[] task_enddate;

    protected Date[] task_completiondate;

    protected Integer[] taskcomment_taskcommentid;

    protected Integer[] taskcomment_task_taskid;

    protected Date[] taskcomment_commentdate;

    protected Integer[] taskreport_taskreportid;

    protected Integer[] taskreport_task_taskid;

    protected Date[] taskreport_reportdate;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ProjectDAO projectDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private TaskCommentDAO taskCommentDAO;

    @Autowired
    private TaskCompletionReportDAO taskCompletionReportDAO;

    protected DAODataTester_1() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR_OF_DAY, 12);
        Date tomorrow = c.getTime();
        c.add(Calendar.DAY_OF_MONTH, -1);
        Date yesterday = c.getTime();
        c.add(Calendar.WEEK_OF_YEAR, 1);
        Date before_one_week = c.getTime();

        user_prtexpirations =
                new Date[] { tomorrow, null, yesterday, null, null };
        project_projectplanneddate = new Date[] { before_one_week, tomorrow,
                    yesterday, before_one_week, before_one_week };
        project_projectduedate = new Date[] { yesterday, tomorrow,
                    before_one_week, before_one_week, before_one_week };
        task_startdate = new Date[] { yesterday, yesterday, yesterday,
                    yesterday, yesterday, yesterday, yesterday, yesterday,
                    yesterday, yesterday, yesterday, yesterday, yesterday,
                    yesterday, yesterday };
        task_enddate = new Date[] { tomorrow, tomorrow, tomorrow, tomorrow,
                    tomorrow, tomorrow, tomorrow, tomorrow, tomorrow, tomorrow,
                    tomorrow, tomorrow, tomorrow, tomorrow, tomorrow };
        task_completiondate = new Date[] { null, null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null };
        taskcomment_commentdate = new Date[] { yesterday, yesterday,
                    yesterday };
        taskreport_reportdate = new Date[] { yesterday, yesterday };
    }

    @Before
    @Transactional
    public void setUp() {
        for(int i = 0; i < user_usernames.length; i++) {
            User u = new User();
            u.setUserName(user_usernames[i]);
            u.setPassword(user_passwords[i]);
            u.setFullName(user_fullnames[i]);
            u.setAdministrator(user_administrators[i]);
            u.setEmail(user_emails[i]);
            u.setPasswordRecoveryTicket(user_prtickets[i]);
            u.setPasswordRecoveryExpiration(user_prtexpirations[i]);
            userDAO.save(u);
        }

        project_projectid = new Integer[project_projectname.length];
        for(int i = 0; i < project_projectname.length; i++) {
            Project p = new Project();
            p.setProjectName(project_projectname[i]);
            p.setProjectDescription(project_projectdescription[i]);
            p.setProjectDueDate(project_projectduedate[i]);
            p.setProjectPlannedDate(project_projectplanneddate[i]);
            projectDAO.save(p);
            project_projectid[i] = p.getProjectId();
        }

        task_taskid = new Integer[task_taskname.length];
        task_projec_projecttid = new Integer[task_taskname.length];
        for(int i = 0; i < task_taskname.length; i++) {
            Task t = new Task();
            t.setTaskName(task_taskname[i]);
            t.setTaskDescription(task_taskdescription[i]);
            t.setTaskState(task_taskstate[i]);
            t.setStartDate(task_startdate[i]);
            t.setEndDate(task_enddate[i]);
            t.setCompletionDate(task_completiondate[i]);
            t.setEstimatedHours(task_estimatedhours[i]);
            t.setInvestedHours(task_investedhours[i]);

            t.setAssignedUser(userDAO.findByUserName(
                    task_assigneduser_username[i]));

            task_projec_projecttid[i] =
                    project_projectid[task_project_projectindex[i]];
            t.setProject(projectDAO.findById(task_projec_projecttid[i]));
            taskDAO.save(t);
            task_taskid[i] = t.getTaskId();
        }

        taskcomment_taskcommentid =
                new Integer[taskcomment_task_taskindex.length];
        taskcomment_task_taskid = new Integer[taskcomment_task_taskindex.length];
        for(int i = 0; i < taskcomment_task_taskindex.length; i++) {
            TaskComment t = new TaskComment();
            t.setCommentDate(taskcomment_commentdate[i]);
            t.setCommentText(taskcomment_commenttext[i]);

            t.setAuthor(userDAO.findByUserName(taskcomment_author_username[i]));

            taskcomment_task_taskid[i] =
                    task_taskid[taskcomment_task_taskindex[i]];
            t.setTask(taskDAO.findById(taskcomment_task_taskid[i]));
            taskCommentDAO.save(t);
            taskcomment_taskcommentid[i] = t.getTaskCommentId();
        }

        taskreport_taskreportid =
                new Integer[taskreport_task_taskindex.length];
        taskreport_task_taskid = new Integer[taskreport_task_taskindex.length];
        for(int i = 0; i < taskreport_task_taskindex.length; i++) {
            TaskCompletionReport t = new TaskCompletionReport();
            t.setReportDate(taskreport_reportdate[i]);
            t.setInvestedHoursToDate(taskreport_investedhourstodate[i]);

            t.setReportingUser(userDAO.findByUserName(
                    taskreport_reportinguser_username[i]));

            taskreport_task_taskid[i] =
                    task_taskid[taskreport_task_taskindex[i]];
            t.setTask(taskDAO.findById(taskreport_task_taskid[i]));
            taskCompletionReportDAO.save(t);
            taskreport_taskreportid[i] = t.getTaskCompletionReportId();
        }
    }

    @After
    @Transactional
    public void tearDown() {
        for(int i = 0; i < taskreport_taskreportid.length; i++) {
            TaskCompletionReport t = taskCompletionReportDAO.findById(
                    taskreport_taskreportid[i]);
            taskCompletionReportDAO.delete(t);
        }

        for(int i = 0; i < taskcomment_taskcommentid.length; i++) {
            TaskComment t =
                    taskCommentDAO.findById(taskcomment_taskcommentid[i]);
            taskCommentDAO.delete(t);
        }

        for(int i = 0; i < task_taskid.length; i++) {
            Task t = taskDAO.findById(task_taskid[i]);
            taskDAO.delete(t);
        }

        for(int i = 0; i < project_projectid.length; i++) {
            Project p = projectDAO.findById(project_projectid[i]);
            projectDAO.delete(p);
        }

        for(int i = 0; i < user_usernames.length; i++) {
            User u = userDAO.findByUserName(user_usernames[i]);
            userDAO.delete(u);
        }
    }

}
