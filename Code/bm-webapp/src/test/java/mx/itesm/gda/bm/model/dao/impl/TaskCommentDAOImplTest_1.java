/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskCommentDAOImplTest.java 269 2010-10-23 19:59:04Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-23 14:59:04 -0500 (Sat, 23 Oct 2010) $
 * Last Version      : $Revision: 269 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import mx.itesm.gda.bm.model.TaskComment;
import mx.itesm.gda.bm.model.dao.TaskCommentDAO;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.test.data.DAODataTester;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 269 $
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/testDAOContext.xml")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TaskCommentDAOImplTest_1 extends DAODataTester {

    private static final Log LOGGER = LogFactory.getLog(
            TaskCommentDAOImplTest.class);

    @Autowired
    private TaskCommentDAO taskCommentDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * Test of getAll method, of class TaskCommentDAOImpl.
     */
    @Test
    @Transactional
    public void testGetAll() {
        LOGGER.info("getAll");
        BitSet bitset = new BitSet();
        bitset.set(0, taskcomment_taskcommentid.length);
        List<TaskComment> task_comment = taskCommentDAO.getAll();
        OUTER:
        for(TaskComment t : task_comment) {
            for(int i = bitset.nextSetBit(0); i >= 0;
                    i = bitset.nextSetBit(i + 1)) {
                if(taskcomment_taskcommentid[i] == t.getTaskCommentId()) {
                    bitset.clear(i);

                    Assert.assertEquals(Integer.valueOf(t.getTaskCommentId()),
                            taskcomment_taskcommentid[i]);
                    Assert.assertEquals(t.getCommentDate(),
                            taskcomment_commentdate[i]);
                    Assert.assertEquals(t.getCommentText(),
                            taskcomment_commenttext[i]);

                    Assert.assertEquals(t.getAuthor(), userDAO.findByUserName(
                            taskcomment_author_username[i]));
                    Assert.assertEquals(t.getTask(), taskDAO.findById(
                            taskcomment_task_taskid[i]));

                    continue OUTER;
                }
            }

            Assert.fail();
        }

        Assert.assertTrue(bitset.isEmpty());
    }

    /**
     * Test of findById method, of class TaskCommentDAOImpl.
     */
    @Test
    @Transactional
    public void testFindById() {
        LOGGER.info("findById");

        Set<Integer> s = new HashSet<Integer>(Arrays.asList(
                taskcomment_taskcommentid));
        Random random = new SecureRandom();

        for(int i = 0; i < taskcomment_taskcommentid.length; i++) {
            TaskComment t =
                    taskCommentDAO.findById(taskcomment_taskcommentid[i]);
            Assert.assertNotNull(t);
            Assert.assertEquals(Integer.valueOf(t.getTaskCommentId()),
                    taskcomment_taskcommentid[i]);
            Assert.assertEquals(t.getCommentDate(),
                    taskcomment_commentdate[i]);
            Assert.assertEquals(t.getCommentText(),
                    taskcomment_commenttext[i]);

            Assert.assertEquals(t.getAuthor(), userDAO.findByUserName(
                    taskcomment_author_username[i]));
            Assert.assertEquals(t.getTask(), taskDAO.findById(
                    taskcomment_task_taskid[i]));
        }

        for(int i = 0; i < taskcomment_taskcommentid.length; i++) {
            int task_comment_id;
            do {
                task_comment_id = random.nextInt();
            } while(s.contains(task_comment_id));

            TaskComment t = taskCommentDAO.findById(task_comment_id);
            Assert.assertNull(t);
        }

        TaskComment t = taskCommentDAO.findById(0);
        if(s.contains(0)) {
            Assert.assertNotNull(t);
        } else {
            Assert.assertNull(t);
        }

        t = taskCommentDAO.findById(-1);
        Assert.assertNull(t);
    }

    /**
     * Test of searchByCommentText method, of class TaskCommentDAOImpl.
     */
    @Test(expected = UnsupportedOperationException.class)
    @Transactional
    public void testSearchByCommentText() {
        LOGGER.info("searchByCommentText");
        taskCommentDAO.searchByCommentText(null);
    }

}
