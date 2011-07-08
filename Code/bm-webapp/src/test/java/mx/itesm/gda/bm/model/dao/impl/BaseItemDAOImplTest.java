/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: BaseItemDAOImplTest.java 269 2010-10-23 19:59:04Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-23 14:59:04 -0500 (Sat, 23 Oct 2010) $
 * Last Version      : $Revision: 269 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.model.dao.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import mx.itesm.gda.bm.model.BaseItem;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class BaseItemDAOImplTest {

    private static final Log LOGGER = LogFactory.getLog(
            BaseItemDAOImplTest.class);

    private ConcreteItem item;

    private EntityManager entityManager;

    private MyInvocationHandler myInvocationHandler;

    @Before
    public void setUp() {
        item = new ConcreteItem();
        myInvocationHandler = new MyInvocationHandler();
        entityManager = (EntityManager)Proxy.newProxyInstance(getClass().
                getClassLoader(), new Class<?>[] { EntityManager.class },
                myInvocationHandler);
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of delete method, of class BaseItemDAOImpl.
     */
    @Test
    @Transactional
    public void testDelete() {
        LOGGER.info("delete");

        myInvocationHandler.setInvocations(Collections.singletonList(
                new SingleInvocation(entityManager, "remove",
                new Class<?>[] { Object.class }, new Object[] { item }, null)));

        ConcreteItemDAOImpl itemDAO = new ConcreteItemDAOImpl();
        itemDAO.setEntityManager(entityManager);
        itemDAO.delete(item);

        myInvocationHandler.assertAllInvocations();
    }

    /**
     * Test of save method, of class BaseItemDAOImpl.
     */
    @Test
    @Transactional
    public void testSave() {
        LOGGER.info("save");

        myInvocationHandler.setInvocations(Collections.singletonList(
                new SingleInvocation(entityManager, "persist",
                new Class<?>[] { Object.class }, new Object[] { item }, null)));

        ConcreteItemDAOImpl itemDAO = new ConcreteItemDAOImpl();
        itemDAO.setEntityManager(entityManager);
        itemDAO.save(item);

        myInvocationHandler.assertAllInvocations();
    }

    /**
     * Test of update method, of class BaseItemDAOImpl.
     */
    @Test
    @Transactional
    public void testUpdate() {
        LOGGER.info("update");

        myInvocationHandler.setInvocations(Collections.singletonList(
                new SingleInvocation(entityManager, "merge",
                new Class<?>[] { Object.class }, new Object[] { item }, null)));

        ConcreteItemDAOImpl itemDAO = new ConcreteItemDAOImpl();
        itemDAO.setEntityManager(entityManager);
        itemDAO.update(item);

        myInvocationHandler.assertAllInvocations();
    }

    /**
     * Test of lock method, of class BaseItemDAOImpl.
     */
    @Test
    @Transactional
    public void testLock() {
        LOGGER.info("lock");

        myInvocationHandler.setInvocations(Collections.singletonList(
                new SingleInvocation(entityManager, "lock",
                new Class<?>[] { Object.class, LockModeType.class },
                new Object[] { item, LockModeType.WRITE }, null)));

        ConcreteItemDAOImpl itemDAO = new ConcreteItemDAOImpl();
        itemDAO.setEntityManager(entityManager);
        itemDAO.lock(item);

        myInvocationHandler.assertAllInvocations();
    }

    /**
     * Test of refresh method, of class BaseItemDAOImpl.
     */
    @Test
    @Transactional
    public void testRefresh() {
        LOGGER.info("refresh");

        myInvocationHandler.setInvocations(Collections.singletonList(
                new SingleInvocation(entityManager, "refresh",
                new Class<?>[] { Object.class }, new Object[] { item }, null)));

        ConcreteItemDAOImpl itemDAO = new ConcreteItemDAOImpl();
        itemDAO.setEntityManager(entityManager);
        itemDAO.refresh(item);

        myInvocationHandler.assertAllInvocations();
    }

    private static class ConcreteItem implements BaseItem {
    }

    private static class ConcreteItemDAOImpl
            extends BaseItemDAOImpl<ConcreteItem> {

        @Override
        public List<ConcreteItem> getAll() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

    }

    private static class SingleInvocation {

        private final Object proxy;

        private final String methodName;

        private final Class<?>[] methodParamTypes;

        private final Object[] methodParameters;

        private final Object returnValue;

        private SingleInvocation(Object my_proxy, String method_name,
                Class<?>[] method_param_types, Object[] method_params,
                Object ret_value) {
            proxy = my_proxy;
            methodName = method_name;
            methodParamTypes = method_param_types;
            methodParameters = method_params;
            returnValue = ret_value;
        }

    }

    private static class MyInvocationHandler implements InvocationHandler {

        private Iterator<SingleInvocation> invocations;

        private void setInvocations(List<SingleInvocation> my_invocations) {
            invocations = my_invocations.iterator();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws
                Throwable {
            Assert.assertTrue(invocations.hasNext());
            SingleInvocation invocation = invocations.next();

            Assert.assertSame(invocation.proxy, proxy);
            Assert.assertEquals(invocation.methodName, method.getName());
            Assert.assertArrayEquals(invocation.methodParamTypes, method.
                    getParameterTypes());
            Assert.assertArrayEquals(invocation.methodParameters, args);

            return invocation.returnValue;
        }

        public void assertAllInvocations() {
            Assert.assertFalse(invocations.hasNext());
        }

    }

}
