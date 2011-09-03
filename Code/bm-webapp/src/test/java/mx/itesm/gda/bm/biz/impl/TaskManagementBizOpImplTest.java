/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.itesm.gda.bm.biz.impl;

import junit.framework.Assert;
import java.util.HashMap;
import java.util.ArrayList;
import org.easymock.EasyMock;
import mx.itesm.gda.bm.model.User;
import mx.itesm.gda.bm.model.dao.UserDAO;
import java.util.Calendar;
import org.springframework.transaction.annotation.Transactional;
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import mx.itesm.gda.bm.biz.TaskManagementBizOp;
import mx.itesm.gda.bm.model.TaskComment;
import mx.itesm.gda.bm.model.TaskCompletionReport;
import mx.itesm.gda.bm.model.TaskState;
import mx.itesm.gda.bm.model.dao.TaskCommentDAO;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.test.utils.TestUtils;
import org.easymock.Capture;
import org.junit.Test;

/**
 *
 * @author Marco Rangel
 */
@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ContextConfiguration(locations = {"/testBizOpContext.xml"})
public class TaskManagementBizOpImplTest {

    private static final Log LOGGER = LogFactory.getLog(
            TaskManagementBizOpImplTest.class);
    @Autowired
    private TaskManagementBizOp taskManagementBizOp;
    @Autowired
    private ProjectDAO projectDAOMock;
    @Autowired
    private UserDAO userDAOMock;
    @Autowired
    private TaskDAO taskDAOMock;
    @Autowired
    private TaskCommentDAO taskCommentDAOMock;

    private static User createUser(String userName, String fullName,
            String email, String password, int permissions) {
        User u = new User();
        u.setUserName(userName);
        u.setFullName(fullName);
        u.setEmail(email);
        u.setPassword(password);
        u.setPermissions(permissions);
        return u;
    }


    private static Project createProject(Integer projectId, String projectName,
            List<Task> tasks, String projectDescription, Date projectDueDate,
            Date projectPlannedDate) {
        Project p = new Project();
        p.setProjectId(projectId);
        p.setProjectName(projectName);
        p.setProjectDescription(projectDescription);
        p.setProjectDueDate(projectDueDate);
        p.setProjectPlannedDate(projectPlannedDate);
        p.setTasks(tasks);
        return p;
    }

    private static Task createTask(String taskName, String description,
            User u, int estimatedHours, Date startDate, Date endDate,
            int taskID, Project p, TaskState status, int remainingHours,
            List<TaskComment> taskComments, List<TaskCompletionReport> reports,
            Date completionDate, int investedHours){

        Task t = new Task();
        t.setTaskId(taskID);
        t.setTaskName(taskName);
        t.setProject(p);
        t.setTaskDescription(description);
        t.setAssignedUser(u);
        t.setEstimatedHours(estimatedHours);
        t.setRemainingHours(remainingHours);
        t.setInvestedHours(investedHours);
        t.setStartDate(startDate);
        t.setEndDate(endDate);
        t.setTaskState(status);
        t.setTaskComments(taskComments);
        t.setTaskCompletionReports(reports);
        t.setCompletionDate(completionDate);

        return t;
    }

    private static TaskComment createTaskComment(int id, Task t, User u, Date d, String text){

        TaskComment c = new TaskComment();
        c.setTaskCommentId(id);
        c.setTask(t);
        c.setAuthor(u);
        c.setCommentDate(d);
        c.setCommentText(text);

        return c;
    }

    private static Date createDate(int day, int month, int year) {
        Calendar that_day = new GregorianCalendar(year, month - 1, day);
        return that_day.getTime();
    }

    private Map<String, ?> mapUser(User user) {
        Map<String, Object> u = new HashMap<String, Object>();
        u.put("userName", user.getUserName());
        u.put("fullName", user.getFullName());
        u.put("email", user.getEmail());
        return u;
    }

    private Map<String, ?> mapProject(Project project) {
        Map<String, Object> p = new HashMap<String, Object>();
        p.put("projectId", project.getProjectId());
        p.put("projectName", project.getProjectName());
        p.put("projectDescription", project.getProjectDescription());
        p.put("projectDueDate", project.getProjectDueDate());
        p.put("projectPlannedDate", project.getProjectPlannedDate());
        p.put("empty", project.getTasks().isEmpty());
        return p;
    }

    @Test
    @Transactional
    public void testRetrieveTasks() {
        LOGGER.info("retrieveTasks");

        // Se simulan los datos
        List<TaskComment> tc = new ArrayList<TaskComment>();
        List<TaskCompletionReport> tcr = new ArrayList<TaskCompletionReport>();
        List<Task> t = new ArrayList<Task>();
        User u = createUser("user1", "User1", "user1@bipolar.com", "user1", 10);
        Project p = createProject(11, "Proyecto1", t, "Desc1", 
                createDate(11, 2, 2010), createDate(13, 2, 2010));
        t.add(createTask("task1", "Es la tarea 1", u, 20,
                createDate(9, 2, 2010), createDate(11, 2, 2010), 1, p,
                TaskState.STARTED, 10, tc, tcr, null, 10));
        t.add(createTask("task2", "Es la tarea 2", u, 20,
                createDate(9, 2, 2010), createDate(11, 2, 2010), 2, p,
                TaskState.NOT_STARTED, 20, tc, tcr, null, 0));

        //Se inicializa el mock
        EasyMock.expect(projectDAOMock.findById(11)).andReturn(p);
        EasyMock.replay(projectDAOMock);

        //Se llama el método
        List<Map<String, ?>> tasks = taskManagementBizOp.retrieveTasks(11);

        //Se comprueba que las tareas sean correctas
        Assert.assertEquals(tasks.get(0).get("taskName"), "task1");
        Assert.assertEquals(tasks.get(0).get("taskID"), 1);
        Assert.assertEquals(tasks.get(0).get("project"), mapProject(p));
        Assert.assertEquals(tasks.get(0).get("assignedUser"), mapUser(u));
        Assert.assertEquals(tasks.get(0).get("startDate"), createDate(9, 2, 2010));
        Assert.assertEquals(tasks.get(0).get("endDate"), createDate(11, 2, 2010));
        Assert.assertEquals(tasks.get(0).get("investedHours"), 10);
        Assert.assertEquals(tasks.get(0).get("estimatedHours"), 20);
        Assert.assertEquals(tasks.get(0).get("remainingHours"), 10);
        Assert.assertEquals(tasks.get(0).get("completionDate"), null);
        Assert.assertEquals(tasks.get(0).get("status"), "STARTED");
        Assert.assertEquals(tasks.get(0).get("commentsNumber"), 0);

        Assert.assertEquals(tasks.get(1).get("taskName"), "task2");
        Assert.assertEquals(tasks.get(1).get("taskID"), 2);
        Assert.assertEquals(tasks.get(1).get("project"), mapProject(p));
        Assert.assertEquals(tasks.get(1).get("assignedUser"), mapUser(u));
        Assert.assertEquals(tasks.get(1).get("startDate"), createDate(9, 2, 2010));
        Assert.assertEquals(tasks.get(1).get("endDate"), createDate(11, 2, 2010));
        Assert.assertEquals(tasks.get(1).get("investedHours"), 0);
        Assert.assertEquals(tasks.get(1).get("estimatedHours"), 20);
        Assert.assertEquals(tasks.get(1).get("remainingHours"), 20);
        Assert.assertEquals(tasks.get(1).get("completionDate"), null);
        Assert.assertEquals(tasks.get(1).get("status"), "NOT_STARTED");
        Assert.assertEquals(tasks.get(1).get("commentsNumber"), 0);

        EasyMock.reset(projectDAOMock);
    }

    @Test
    @Transactional
    public void testRetrieveComments() {
        LOGGER.info("retrieveComments");

        List<TaskComment> tc = new ArrayList<TaskComment>();
        List<TaskCompletionReport> tcr = new ArrayList<TaskCompletionReport>();
        User u = createUser("user1", "User1", "user1@bipolar.com", "user1",10);
        Project p = createProject(11, "Proyecto1", new ArrayList<Task>(), "Desc1", 
                createDate(9, 2, 2010), createDate(13, 2, 2010));
        Task t = createTask("task1", "Es la tarea 1", u, 20,
                createDate(9, 2, 2010), createDate(11, 2, 2010), 1, p,
                TaskState.STARTED, 10, tc, tcr, null, 10);
        TaskComment c1 = createTaskComment(1, t, u, createDate(11, 2, 2010), "Es el primer comentario");
        TaskComment c2 = createTaskComment(2, t, u, createDate(11, 2, 2010), "Es el segundo comentario");
        tc.add(c1);
        tc.add(c2);

        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.replay(taskDAOMock);

        List<Map<String, ?>> comments = taskManagementBizOp.retrieveComments(1);

        Assert.assertEquals(comments.get(0).get("author"), u.getFullName());
        Assert.assertEquals(comments.get(0).get("date"), createDate(11, 2, 2010));
        Assert.assertEquals(comments.get(0).get("text"), "Es el primer comentario");

        Assert.assertEquals(comments.get(1).get("author"), u.getFullName());
        Assert.assertEquals(comments.get(1).get("date"), createDate(11, 2, 2010));
        Assert.assertEquals(comments.get(1).get("text"), "Es el segundo comentario");

        EasyMock.reset(taskDAOMock);
    }

    @Test
    @Transactional
    public void testGetTask() {
        LOGGER.info("getTask");

        List<TaskComment> tc = new ArrayList<TaskComment>();
        List<TaskCompletionReport> tcr = new ArrayList<TaskCompletionReport>();
        User u = createUser("user1", "User1", "user1@bipolar.com", "user1",10);
        Project p = createProject(11, "Proyecto1", new ArrayList<Task>(), "Desc1",
                createDate(9, 2, 2010), createDate(13, 2, 2010));
        Task t = createTask("task1", "Es la tarea 1", u, 20,
                createDate(9, 2, 2010), createDate(11, 2, 2010), 1, p,
                TaskState.STARTED, 10, tc, tcr, null, 10);

        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.replay(taskDAOMock);

        Map<String, ?> task = taskManagementBizOp.getTask(1);

        Assert.assertEquals(task.get("projectID"), 11);
        Assert.assertEquals(task.get("description"), "Es la tarea 1");
        Assert.assertEquals(task.get("taskName"), "task1");
        Assert.assertEquals(task.get("assignedUser"), mapUser(u));
        Assert.assertEquals(task.get("startDate"), createDate(9, 2, 2010));
        Assert.assertEquals(task.get("endDate"), createDate(11, 2, 2010));
        Assert.assertEquals(task.get("estimatedHours"), 20);
        Assert.assertEquals(task.get("investedHours"), 10);
        Assert.assertEquals(task.get("remainingHours"), 10);
        Assert.assertEquals(task.get("status"), "STARTED");

        EasyMock.reset(taskDAOMock);
    }

    @Test
    @Transactional
    public void testCreateTask() {
        LOGGER.info("createTask");

        User u = createUser("user1", "User1", "user1@bipolar.com", "user1",10);
        Project p = createProject(1, "Proyecto1", new ArrayList<Task>(), "Desc1",
                createDate(9, 2, 2010), createDate(13, 2, 2010));

        EasyMock.expect(userDAOMock.findByUserName("user1")).andReturn(u);
        EasyMock.expect(projectDAOMock.findById(1)).andReturn(p);
        
        Capture<Task> createdTask = new Capture<Task>();

        taskDAOMock.save(EasyMock.and(EasyMock.isA(Task.class),
                EasyMock.and(EasyMock.capture(createdTask),
                (Task) TestUtils.setId(30))));

        EasyMock.replay(userDAOMock, projectDAOMock, taskDAOMock);

        int taskID = taskManagementBizOp.createTask("task1", 1,
                "La primera tarea", "user1", 20, createDate(9, 2, 2010),
                createDate(13, 2, 2010));

        Task theTask = createdTask.getValue();
        Assert.assertTrue(taskID == theTask.getTaskId());

        EasyMock.reset(userDAOMock, projectDAOMock, taskDAOMock);
    }

    @Test
    @Transactional
    public void testDeleteTask() {
        LOGGER.info("deleteTask");

        //Caso de que intente borrar una tarea nula
        boolean exceptionThrown = false;
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(null);
        taskDAOMock.delete(new Task());
        EasyMock.expectLastCall();
        EasyMock.replay(taskDAOMock);

        try {
            taskManagementBizOp.deleteTask(1);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(taskDAOMock);
        }
        Assert.assertTrue(exceptionThrown);

        //caso de que intente borrrar una tarea vacía
        exceptionThrown = false;
        List<TaskComment> tc = new ArrayList<TaskComment>();
        List<TaskCompletionReport> tcr = new ArrayList<TaskCompletionReport>();
        User u = createUser("user1", "User1", "user1@bipolar.com", "user1",10);
        Project p = createProject(11, "Proyecto1", new ArrayList<Task>(), "Desc1",
                createDate(9, 2, 2010), createDate(13, 2, 2010));
        Task t = createTask("task1", "Es la tarea 1", u, 20,
                createDate(9, 2, 2010), createDate(11, 2, 2010), 1, p,
                TaskState.NOT_STARTED, 20, tc, tcr, null, 0);
        t.setTaskId(1);
        Capture<Task> capturedTaskMock = new Capture<Task>();
        taskDAOMock.delete(EasyMock.and(EasyMock.isA(Task.class),
                EasyMock.capture(capturedTaskMock)));
        EasyMock.expectLastCall();
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.replay(taskDAOMock);

        try {
            taskManagementBizOp.deleteTask(1);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(taskDAOMock);
        }
        Assert.assertFalse(exceptionThrown);
        Assert.assertEquals(t.getTaskState(), TaskState.NOT_STARTED);
        Assert.assertEquals(t.getInvestedHours(), 0);
        Assert.assertTrue(t.getTaskComments().isEmpty());

        //Caso de que intente borrar una tarea que esté iniciada
        t.setTaskState(TaskState.STARTED);
        t.setInvestedHours(2);
        exceptionThrown = false;
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        taskDAOMock.delete(new Task());
        EasyMock.expectLastCall();
        EasyMock.replay(taskDAOMock);
        try {
            taskManagementBizOp.deleteTask(1);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(taskDAOMock);
        }
        Assert.assertTrue(exceptionThrown);

        //Caso de que intente borrar una tarea que tenga comentarios
        exceptionThrown = false;
        t.setTaskState(TaskState.NOT_STARTED);
        t.setInvestedHours(0);
        TaskComment c1 = createTaskComment(1, t, u, createDate(11, 2, 2010), "Es el primer comentario");
        TaskComment c2 = createTaskComment(2, t, u, createDate(11, 2, 2010), "Es el segundo comentario");
        tc.add(c1);
        tc.add(c2);
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        taskDAOMock.delete(new Task());
        EasyMock.expectLastCall();
        EasyMock.replay(taskDAOMock);
        try {
            taskManagementBizOp.deleteTask(1);
        } catch (Exception e) {
            exceptionThrown = true;
        } finally {
            EasyMock.reset(taskDAOMock);
        }
        Assert.assertTrue(exceptionThrown);
    }

    @Test
    @Transactional
    public void testModifyTask() {
        LOGGER.info("modifyTask");

        List<TaskComment> tc = new ArrayList<TaskComment>();
        List<TaskCompletionReport> tcr = new ArrayList<TaskCompletionReport>();
        User u = createUser("user1", "User1", "user1@bipolar.com", "user1",10);
        User u2 = createUser("user2", "User1", "user1@bipolar.com", "user1",10);
        Project p = createProject(11, "Proyecto1", new ArrayList<Task>(), "Desc1",
                createDate(9, 2, 2010), createDate(13, 2, 2010));
        Task t = createTask("task1", "Es la tarea 1", u, 20,
                createDate(9, 2, 2010), createDate(11, 2, 2010), 1, p,
                TaskState.NOT_STARTED, 20, tc, tcr, null, 0);
        t.setTaskId(1);

        //Caso de que la tarea no se agreguen investedHours
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.expect(userDAOMock.findByUserName("user1")).andReturn(u);
        EasyMock.expect(userDAOMock.findByUserName("user2")).andReturn(u2);
        EasyMock.replay(taskDAOMock, userDAOMock);
        taskManagementBizOp.modifyTask(1, "Tarea1", "Test Desc", u2.getUserName(), "NOT_STARTED", 20, 0, 20, createDate(8, 2, 2010), createDate(12, 2, 2010));
        Assert.assertEquals(t.getTaskName(), "Tarea1");
        Assert.assertEquals(t.getAssignedUser(), u2);
        Assert.assertEquals(t.getTaskDescription(), "Test Desc");
        Assert.assertEquals(t.getTaskState(), TaskState.NOT_STARTED);
        Assert.assertEquals(t.getEstimatedHours(), 20);
        Assert.assertEquals(t.getInvestedHours(), 0);
        Assert.assertEquals(t.getRemainingHours(), 20);
        Assert.assertEquals(t.getStartDate(), createDate(8, 2, 2010));
        Assert.assertEquals(t.getEndDate(), createDate(12, 2, 2010));
        EasyMock.reset(taskDAOMock, userDAOMock);

        //Caso de que la tarea esté no iniciada y se agregue investedHours
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.expect(userDAOMock.findByUserName("user1")).andReturn(u);
        EasyMock.expect(userDAOMock.findByUserName("user2")).andReturn(u2);
        EasyMock.replay(taskDAOMock, userDAOMock);
        taskManagementBizOp.modifyTask(1, "Tarea1", "Test Desc", u2.getUserName(), "NOT_STARTED", 20, 5, 15, createDate(8, 2, 2010), createDate(12, 2, 2010));
        Assert.assertEquals(t.getTaskName(), "Tarea1");
        Assert.assertEquals(t.getAssignedUser(), u2);
        Assert.assertEquals(t.getTaskDescription(), "Test Desc");
        Assert.assertEquals(t.getTaskState(), TaskState.STARTED);
        Assert.assertEquals(t.getEstimatedHours(), 20);
        Assert.assertEquals(t.getInvestedHours(), 5);
        Assert.assertEquals(t.getRemainingHours(), 15);
        Assert.assertEquals(t.getStartDate(), createDate(8, 2, 2010));
        Assert.assertEquals(t.getEndDate(), createDate(12, 2, 2010));
        EasyMock.reset(taskDAOMock, userDAOMock);

        //Caso de que la tarea tenga remainingHours 0 y los status no sean CANCELADO o COMPLETADO
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.expect(userDAOMock.findByUserName("user1")).andReturn(u);
        EasyMock.expect(userDAOMock.findByUserName("user2")).andReturn(u2);
        EasyMock.replay(taskDAOMock, userDAOMock);
        taskManagementBizOp.modifyTask(1, "Tarea1", "Test Desc", u2.getUserName(), "STARTED", 20, 15, 0, createDate(8, 2, 2010), createDate(12, 2, 2010));
        Assert.assertEquals(t.getTaskName(), "Tarea1");
        Assert.assertEquals(t.getAssignedUser(), u2);
        Assert.assertEquals(t.getTaskDescription(), "Test Desc");
        Assert.assertEquals(t.getTaskState(), TaskState.COMPLETED);
        Assert.assertEquals(t.getEstimatedHours(), 20);
        Assert.assertEquals(t.getInvestedHours(), 20);
        Assert.assertEquals(t.getRemainingHours(), 0);
        Assert.assertEquals(t.getStartDate(), createDate(8, 2, 2010));
        Assert.assertEquals(t.getEndDate(), createDate(12, 2, 2010));
        EasyMock.reset(taskDAOMock, userDAOMock);

        //Caso de que la tarea este COMPLETADA y sin fecha de terminación
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.expect(userDAOMock.findByUserName("user1")).andReturn(u);
        EasyMock.expect(userDAOMock.findByUserName("user2")).andReturn(u2);
        EasyMock.replay(taskDAOMock, userDAOMock);
        taskManagementBizOp.modifyTask(1, "Tarea1", "Test Desc", u2.getUserName(), "COMPLETED", 20, 0, 0, createDate(8, 2, 2010), createDate(12, 2, 2010));
        Assert.assertEquals(t.getTaskName(), "Tarea1");
        Assert.assertEquals(t.getAssignedUser(), u2);
        Assert.assertEquals(t.getTaskDescription(), "Test Desc");
        Assert.assertEquals(t.getTaskState(), TaskState.COMPLETED);
        Assert.assertEquals(t.getEstimatedHours(), 20);
        Assert.assertEquals(t.getInvestedHours(), 20);
        Assert.assertEquals(t.getRemainingHours(), 0);
        Assert.assertEquals(t.getStartDate(), createDate(8, 2, 2010));
        Assert.assertEquals(t.getEndDate(), createDate(12, 2, 2010));
        EasyMock.reset(taskDAOMock, userDAOMock);

        //Caso de que la tarea este COMPLETADA
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.expect(userDAOMock.findByUserName("user1")).andReturn(u);
        EasyMock.expect(userDAOMock.findByUserName("user2")).andReturn(u2);
        EasyMock.replay(taskDAOMock, userDAOMock);
        taskManagementBizOp.modifyTask(1, "Tarea1", "Test Desc", u2.getUserName(), "COMPLETED", 20, 0, 0, createDate(8, 2, 2010), createDate(12, 2, 2010));
        Assert.assertEquals(t.getTaskName(), "Tarea1");
        Assert.assertEquals(t.getAssignedUser(), u2);
        Assert.assertEquals(t.getTaskDescription(), "Test Desc");
        Assert.assertEquals(t.getTaskState(), TaskState.COMPLETED);
        Assert.assertEquals(t.getEstimatedHours(), 20);
        Assert.assertEquals(t.getInvestedHours(), 20);
        Assert.assertEquals(t.getRemainingHours(), 0);
        Assert.assertEquals(t.getStartDate(), createDate(8, 2, 2010));
        Assert.assertEquals(t.getEndDate(), createDate(12, 2, 2010));
        EasyMock.reset(taskDAOMock, userDAOMock);

        //Caso de que la tarea este CANCELADA
        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.expect(userDAOMock.findByUserName("user1")).andReturn(u);
        EasyMock.expect(userDAOMock.findByUserName("user2")).andReturn(u2);
        EasyMock.replay(taskDAOMock, userDAOMock);
        taskManagementBizOp.modifyTask(1, "Tarea1", "Test Desc", u2.getUserName(), "CANCELED", 20, 0, 0, createDate(8, 2, 2010), createDate(12, 2, 2010));
        Assert.assertEquals(t.getTaskName(), "Tarea1");
        Assert.assertEquals(t.getAssignedUser(), u2);
        Assert.assertEquals(t.getTaskDescription(), "Test Desc");
        Assert.assertEquals(t.getTaskState(), TaskState.CANCELED);
        Assert.assertEquals(t.getEstimatedHours(), 20);
        Assert.assertEquals(t.getInvestedHours(), 20);
        Assert.assertEquals(t.getRemainingHours(), 0);
        Assert.assertEquals(t.getStartDate(), createDate(8, 2, 2010));
        Assert.assertEquals(t.getEndDate(), createDate(12, 2, 2010));
        EasyMock.reset(taskDAOMock, userDAOMock);
    }

    @Test
    @Transactional
    public void testAddComment() {
        LOGGER.info("addComment");

        List<TaskComment> tc = new ArrayList<TaskComment>();
        List<TaskCompletionReport> tcr = new ArrayList<TaskCompletionReport>();
        User u = createUser("user1", "User1", "user1@bipolar.com", "user1",10);
        Project p = createProject(11, "Proyecto1", new ArrayList<Task>(), "Desc1",
                createDate(9, 2, 2010), createDate(13, 2, 2010));
        Task t = createTask("task1", "Es la tarea 1", u, 20,
                createDate(9, 2, 2010), createDate(11, 2, 2010), 1, p,
                TaskState.NOT_STARTED, 20, tc, tcr, null, 0);
        t.setTaskId(1);

        EasyMock.expect(taskDAOMock.findById(1)).andReturn(t);
        EasyMock.expect(userDAOMock.findByUserName("user1")).andReturn(u);

        Capture<TaskComment> createdTaskComment = new Capture<TaskComment>();

        taskCommentDAOMock.save(EasyMock.and(EasyMock.isA(TaskComment.class),
                EasyMock.and(EasyMock.capture(createdTaskComment),
                (TaskComment) TestUtils.setId(30))));

        EasyMock.replay(taskDAOMock, userDAOMock, taskCommentDAOMock);

        int taskCommentID = taskManagementBizOp.addComment(1, "user1", "Comment");

        TaskComment theTaskComment = createdTaskComment.getValue();
        Assert.assertTrue(taskCommentID == theTaskComment.getTaskCommentId());

        EasyMock.reset(taskDAOMock, userDAOMock, taskCommentDAOMock);
    }
}
