/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: TaskCompletionReportDAOImplTest.java 269 2010-10-23 19:59:04Z alex.vc@gmail.com $
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
import mx.itesm.gda.bm.model.TaskCompletionReport;
import mx.itesm.gda.bm.model.dao.TaskCompletionReportDAO;
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
public class TaskCompletionReportDAOImplTest extends DAODataTester {

    private static final Log LOGGER = LogFactory.getLog(
            TaskCompletionReportDAOImplTest.class);

    @Autowired
    private TaskCompletionReportDAO taskReportDAO;

    @Autowired
    private TaskDAO taskDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * Test of getAll method, of class TaskCompletionReportDAOImpl.
     */
    @Test
    @Transactional
    public void testGetAll() {
        LOGGER.info("getAll");
        BitSet bitset = new BitSet();
        bitset.set(0, taskreport_taskreportid.length);
        List<TaskCompletionReport> task_reports = taskReportDAO.getAll();
        OUTER:
        for(TaskCompletionReport t : task_reports) {
            for(int i = bitset.nextSetBit(0); i >= 0;
                    i = bitset.nextSetBit(i + 1)) {
                if(taskreport_taskreportid[i] == t.getTaskCompletionReportId()) {
                    bitset.clear(i);

                    Assert.assertEquals(Integer.valueOf(t.
                            getTaskCompletionReportId()),
                            taskreport_taskreportid[i]);
                    Assert.assertEquals(Integer.valueOf(
                            t.getInvestedHoursToDate()),
                            taskreport_investedhourstodate[i]);
                    Assert.assertEquals(t.getReportDate(),
                            taskreport_reportdate[i]);

                    Assert.assertEquals(t.getReportingUser(), userDAO.
                            findByUserName(
                            taskreport_reportinguser_username[i]));
                    Assert.assertEquals(t.getTask(), taskDAO.findById(
                            taskreport_task_taskid[i]));

                    continue OUTER;
                }
            }

            Assert.fail();
        }

        Assert.assertTrue(bitset.isEmpty());
    }

    /**
     * Test of findById method, of class TaskCompletionReportDAOImpl.
     */
    @Test
    @Transactional
    public void testFindById() {
        LOGGER.info("findById");

        Set<Integer> s = new HashSet<Integer>(Arrays.asList(
                taskreport_taskreportid));
        Random random = new SecureRandom();

        for(int i = 0; i < taskreport_taskreportid.length; i++) {
            TaskCompletionReport t =
                    taskReportDAO.findById(taskreport_taskreportid[i]);
            Assert.assertNotNull(t);
            Assert.assertEquals(Integer.valueOf(t.getTaskCompletionReportId()),
                    taskreport_taskreportid[i]);
            Assert.assertEquals(Integer.valueOf(t.getInvestedHoursToDate()),
                    taskreport_investedhourstodate[i]);
            Assert.assertEquals(t.getReportDate(), taskreport_reportdate[i]);

            Assert.assertEquals(t.getReportingUser(), userDAO.findByUserName(
                    taskreport_reportinguser_username[i]));
            Assert.assertEquals(t.getTask(), taskDAO.findById(
                    taskreport_task_taskid[i]));
        }

        for(int i = 0; i < taskreport_taskreportid.length; i++) {
            int task_report_id;
            do {
                task_report_id = random.nextInt();
            } while(s.contains(task_report_id));

            TaskCompletionReport t = taskReportDAO.findById(task_report_id);
            Assert.assertNull(t);
        }

        TaskCompletionReport t = taskReportDAO.findById(0);
        if(s.contains(0)) {
            Assert.assertNotNull(t);
        } else {
            Assert.assertNull(t);
        }

        t = taskReportDAO.findById(-1);
        Assert.assertNull(t);
    }

}
