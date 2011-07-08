/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: ProjectDAOImplTest.java 269 2010-10-23 19:59:04Z alex.vc@gmail.com $
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
import mx.itesm.gda.bm.model.Project;
import mx.itesm.gda.bm.model.dao.ProjectDAO;
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
public class ProjectDAOImplTest_1 extends DAODataTester {

    private static final Log LOGGER = LogFactory.getLog(
            ProjectDAOImplTest.class);

    @Autowired
    ProjectDAO projectDAO;

    /**
     * Test of getAll method, of class ProjectDAOImpl.
     */
    @Test
    @Transactional
    public void testGetAll() {
        LOGGER.info("getAll");
        BitSet bitset = new BitSet();
        bitset.set(0, project_projectid.length);
        List<Project> projects = projectDAO.getAll();
        OUTER:
        for(Project p : projects) {
            for(int i = bitset.nextSetBit(0); i >= 0;
                    i = bitset.nextSetBit(i + 1)) {
                if(project_projectid[i] == p.getProjectId()) {
                    bitset.clear(i);

                    Assert.assertEquals(Integer.valueOf(p.getProjectId()),
                            project_projectid[i]);
                    Assert.assertEquals(p.getProjectName(),
                            project_projectname[i]);
                    Assert.assertEquals(p.getProjectDescription(),
                            project_projectdescription[i]);
                    Assert.assertEquals(p.getProjectPlannedDate(),
                            project_projectplanneddate[i]);
                    Assert.assertEquals(p.getProjectDueDate(),
                            project_projectduedate[i]);

                    continue OUTER;
                }
            }

            Assert.fail();
        }

        Assert.assertTrue(bitset.isEmpty());
    }

    /**
     * Test of findById method, of class ProjectDAOImpl.
     */
    @Test
    @Transactional
    public void testFindById() {
        LOGGER.info("findById");

        Set<Integer> s = new HashSet<Integer>(Arrays.asList(project_projectid));
        Random random = new SecureRandom();

        for(int i = 0; i < project_projectid.length; i++) {
            Project p = projectDAO.findById(project_projectid[i]);
            Assert.assertNotNull(p);
            Assert.assertEquals(Integer.valueOf(p.getProjectId()),
                    project_projectid[i]);
            Assert.assertEquals(p.getProjectName(), project_projectname[i]);
            Assert.assertEquals(p.getProjectDescription(),
                    project_projectdescription[i]);
            Assert.assertEquals(p.getProjectPlannedDate(),
                    project_projectplanneddate[i]);
            Assert.assertEquals(p.getProjectDueDate(),
                    project_projectduedate[i]);
        }

        for(int i = 0; i < project_projectid.length; i++) {
            int project_id;
            do {
                project_id = random.nextInt();
            } while(s.contains(project_id));

            Project p = projectDAO.findById(project_id);
            Assert.assertNull(p);
        }

        Project p = projectDAO.findById(0);
        if(s.contains(0)) {
            Assert.assertNotNull(p);
        } else {
            Assert.assertNull(p);
        }

        p = projectDAO.findById(-1);
        Assert.assertNull(p);

    }

    /**
     * Test of searchByProjectName method, of class ProjectDAOImpl.
     */
    @Test
    @Transactional
    public void testSearchByProjectName() {
        LOGGER.info("searchByProjectName");
        Map<String, BitSet> word_map = new HashMap<String, BitSet>();

        for(int i = 0; i < project_projectid.length; i++) {
            String[] words = project_projectname[i].split("\\s+");
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

            List<Project> projects = projectDAO.searchByProjectName(w);
            OUTER:
            for(Project p : projects) {
                Assert.assertNotNull(p);
                for(int i = index_set.nextSetBit(0); i >= 0;
                        i = index_set.nextSetBit(i + 1)) {
                    if(project_projectid[i] == p.getProjectId()) {
                        index_set.clear(i);

                        Assert.assertEquals(Integer.valueOf(p.getProjectId()),
                                project_projectid[i]);
                        Assert.assertEquals(p.getProjectName(),
                                project_projectname[i]);
                        Assert.assertEquals(p.getProjectDescription(),
                                project_projectdescription[i]);
                        Assert.assertEquals(p.getProjectPlannedDate(),
                                project_projectplanneddate[i]);
                        Assert.assertEquals(p.getProjectDueDate(),
                                project_projectduedate[i]);

                        continue OUTER;
                    }
                }
            }

            Assert.assertTrue(index_set.isEmpty());
        }

        for(String key : word_map.keySet()) {
            String w = TestUtils.reverse(key);
            if(!word_map.containsKey(w)) {
                List<Project> projects = projectDAO.searchByProjectName(w);
                Assert.assertNotNull(projects);
                Assert.assertTrue(projects.isEmpty());
            }
        }
    }

    /**
     * Test of getAll method, of class ProjectDAOImpl.
     */
    @Test
    @Transactional
    public void testSearchByUserNameEnrolled() {
        LOGGER.info("searchByUserNameEnrolled");

        for(int k = 0; k < user_usernames.length; k++) {
            BitSet bitset = new BitSet();
            bitset.set(0, project_projectid.length);
            List<Project> projects = projectDAO.searchByUserNameEnrolled(
                    user_usernames[k]);
            OUTER:
            for(Project p : projects) {
                for(int i = bitset.nextSetBit(0); i >= 0;
                        i = bitset.nextSetBit(i + 1)) {
                    if(project_projectid[i] == p.getProjectId()) {
                        bitset.clear(i);

                        Assert.assertEquals(Integer.valueOf(p.getProjectId()),
                                project_projectid[i]);
                        Assert.assertEquals(p.getProjectName(),
                                project_projectname[i]);
                        Assert.assertEquals(p.getProjectDescription(),
                                project_projectdescription[i]);
                        Assert.assertEquals(p.getProjectPlannedDate(),
                                project_projectplanneddate[i]);
                        Assert.assertEquals(p.getProjectDueDate(),
                                project_projectduedate[i]);

                        continue OUTER;
                    }
                }

                Assert.fail();
            }

        }
    }

}
