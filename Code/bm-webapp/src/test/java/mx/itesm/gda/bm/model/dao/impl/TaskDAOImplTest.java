/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskDAOImplTest.java 269 2010-10-23 19:59:04Z alex.vc@gmail.com $
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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import mx.itesm.gda.bm.model.Task;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
import mx.itesm.gda.bm.model.dao.TaskDAO;
import mx.itesm.gda.bm.model.dao.UserDAO;
import mx.itesm.gda.bm.test.data.DAODataTester;
import mx.itesm.gda.bm.test.utils.TestUtils;
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
public class TaskDAOImplTest extends DAODataTester {

    private static final Log LOGGER = LogFactory.getLog(
            ProjectDAOImplTest.class);

    @Autowired
    UserDAO userDAO;

    @Autowired
    ProjectDAO projectDAO;

    @Autowired
    TaskDAO taskDAO;

    /**
     * Test of getAll method, of class TaskDAOImpl.
     */
    @Test
    @Transactional
    public void testGetAll() {
        LOGGER.info("getAll");
        BitSet bitset = new BitSet();
        bitset.set(0, task_taskid.length);
        List<Task> tasks = taskDAO.getAll();
        OUTER:
        for(Task t : tasks) {
            for(int i = bitset.nextSetBit(0); i >= 0;
                    i = bitset.nextSetBit(i + 1)) {
                if(task_taskid[i] == t.getTaskId()) {
                    bitset.clear(i);

                    Assert.assertEquals(Integer.valueOf(t.getTaskId()),
                            task_taskid[i]);
                    Assert.assertEquals(t.getTaskName(),
                            task_taskname[i]);
                    Assert.assertEquals(t.getTaskDescription(),
                            task_taskdescription[i]);
                    Assert.assertEquals(t.getTaskState(), task_taskstate[i]);
                    Assert.assertEquals(t.getStartDate(), task_startdate[i]);
                    Assert.assertEquals(t.getEndDate(), task_enddate[i]);
                    Assert.assertEquals(t.getCompletionDate(),
                            task_completiondate[i]);
                    Assert.assertEquals(Integer.valueOf(t.getEstimatedHours()),
                            task_estimatedhours[i]);
                    Assert.assertEquals(Integer.valueOf(t.getInvestedHours()),
                            task_investedhours[i]);

                    Assert.assertEquals(t.getAssignedUser(), userDAO.
                            findByUserName(task_assigneduser_username[i]));
                    Assert.assertEquals(t.getProject(), projectDAO.findById(
                            task_projec_projecttid[i]));

                    continue OUTER;
                }
            }

            Assert.fail();
        }

        Assert.assertTrue(bitset.isEmpty());
    }

    /**
     * Test of findById method, of class TaskDAOImpl.
     */
    @Test
    @Transactional
    public void testFindById() {
        LOGGER.info("findById");

        Set<Integer> s = new HashSet<Integer>(Arrays.asList(task_taskid));
        Random random = new SecureRandom();

        for(int i = 0; i < task_taskid.length; i++) {
            Task t = taskDAO.findById(task_taskid[i]);
            Assert.assertNotNull(t);
            Assert.assertEquals(Integer.valueOf(t.getTaskId()), task_taskid[i]);
            Assert.assertEquals(t.getTaskName(), task_taskname[i]);
            Assert.assertEquals(t.getTaskDescription(),
                    task_taskdescription[i]);
            Assert.assertEquals(t.getTaskState(), task_taskstate[i]);
            Assert.assertEquals(t.getStartDate(), task_startdate[i]);
            Assert.assertEquals(t.getEndDate(), task_enddate[i]);
            Assert.assertEquals(t.getCompletionDate(), task_completiondate[i]);
            Assert.assertEquals(Integer.valueOf(t.getEstimatedHours()),
                    task_estimatedhours[i]);
            Assert.assertEquals(Integer.valueOf(t.getInvestedHours()),
                    task_investedhours[i]);
            Assert.assertEquals(t.getAssignedUser(), userDAO.findByUserName(
                    task_assigneduser_username[i]));
            Assert.assertEquals(t.getProject(), projectDAO.findById(
                    task_projec_projecttid[i]));
        }

        for(int i = 0; i < task_taskid.length; i++) {
            int task_id;
            do {
                task_id = random.nextInt();
            } while(s.contains(task_id));

            Task t = taskDAO.findById(task_id);
            Assert.assertNull(t);
        }

        Task t = taskDAO.findById(0);
        if(s.contains(0)) {
            Assert.assertNotNull(t);
        } else {
            Assert.assertNull(t);
        }

        t = taskDAO.findById(-1);
        Assert.assertNull(t);
    }

    /**
     * Test of searchByTaskName method, of class TaskDAOImpl.
     */
    @Test
    @Transactional
    public void testSearchByTaskName() {
        LOGGER.info("searchByTaskName");
        Map<String, BitSet> word_map = new HashMap<String, BitSet>();

        for(int i = 0; i < task_taskid.length; i++) {
            String[] words = task_taskname[i].split("\\s+");
            for(String w : words) {
                BitSet index_set;
                if(word_map.containsKey(w)) {
                    index_set = word_map.get(w);
                } else {
                    index_set = new BitSet();
                    word_map.put(w, index_set);
                }
                index_set.set(i);
            }
        }

        for(Map.Entry<String, BitSet> entry : word_map.entrySet()) {
            String w = entry.getKey();
            BitSet index_set = (BitSet)entry.getValue().clone();

            List<Task> tasks = taskDAO.searchByTaskName(w);
            OUTER:
            for(Task t : tasks) {
                Assert.assertNotNull(t);
                for(int i = index_set.nextSetBit(0); i >= 0;
                        i = index_set.nextSetBit(i + 1)) {
                    if(task_taskid[i] == t.getTaskId()) {
                        index_set.clear(i);

                        Assert.assertEquals(Integer.valueOf(t.getTaskId()),
                                task_taskid[i]);
                        Assert.assertEquals(t.getTaskName(), task_taskname[i]);
                        Assert.assertEquals(t.getTaskDescription(),
                                task_taskdescription[i]);
                        Assert.assertEquals(t.getTaskState(),
                                task_taskstate[i]);
                        Assert.assertEquals(t.getStartDate(),
                                task_startdate[i]);
                        Assert.assertEquals(t.getEndDate(), task_enddate[i]);
                        Assert.assertEquals(t.getCompletionDate(),
                                task_completiondate[i]);
                        Assert.assertEquals(Integer.valueOf(
                                t.getEstimatedHours()),
                                task_estimatedhours[i]);
                        Assert.assertEquals(
                                Integer.valueOf(t.getInvestedHours()),
                                task_investedhours[i]);
                        Assert.assertEquals(t.getAssignedUser(), userDAO.
                                findByUserName(task_assigneduser_username[i]));
                        Assert.assertEquals(t.getProject(),
                                projectDAO.findById(task_projec_projecttid[i]));

                        continue OUTER;
                    }
                }
            }

            Assert.assertTrue(index_set.isEmpty());
        }

        for(String key : word_map.keySet()) {
            String w = TestUtils.reverse(key);
            if(!word_map.containsKey(w)) {
                List<Task> tasks = taskDAO.searchByTaskName(w);
                Assert.assertNotNull(tasks);
                Assert.assertTrue(tasks.isEmpty());
            }
        }
    }

}
