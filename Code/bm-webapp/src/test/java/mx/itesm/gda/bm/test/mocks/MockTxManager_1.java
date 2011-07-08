/* ***************************************************************************
 *   Copyright (c) 2010 BIPOLAR
 *   Confidential and Proprietary
 *   All Rights Reserved
 *
 * @(#)$Id: MockTxManager.java 256 2010-10-19 03:49:45Z alex.vc@gmail.com $
 * Last Revised By   : $Author: alex.vc@gmail.com $
 * Last Checked In   : $Date: 2010-10-18 22:49:45 -0500 (Mon, 18 Oct 2010) $
 * Last Version      : $Revision: 256 $
 *
 * Original Author   : Alejandro Vazquez
 * Notes             :
 * ************************************************************************ */

package mx.itesm.gda.bm.test.mocks;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.DefaultTransactionStatus;

/**
 *
 * @author $Author: alex.vc@gmail.com $
 * @version $Revision: 256 $
 */
public class MockTxManager_1 extends AbstractPlatformTransactionManager {

    private static final Log LOGGER = LogFactory.getLog(MockTxManager.class);

    private static class MockTx {
    }

    private ThreadLocal<MockTx> txContext;

    public MockTxManager_1() {
        txContext = new ThreadLocal<MockTx>();
    }

    @Override
    protected Object doGetTransaction() throws TransactionException {

        MockTx mockTx = txContext.get();
        if(mockTx == null) {
            mockTx = new MockTx();
            LOGGER.debug("Creating Transaction " + mockTx.hashCode());
        } else {
            LOGGER.debug("Retrieving Transaction " + mockTx.hashCode());
        }
        return mockTx;
    }

    @Override
    protected void doBegin(Object transaction, TransactionDefinition definition)
            throws TransactionException {
        LOGGER.debug("Starting Transaction " + transaction.hashCode());
        txContext.set((MockTx)transaction);
    }

    @Override
    protected void doCommit(DefaultTransactionStatus status) throws
            TransactionException {
        MockTx mockTx = txContext.get();
        LOGGER.debug("Commiting Transaction " + mockTx.hashCode());
        txContext.set(null);
    }

    @Override
    protected void doRollback(DefaultTransactionStatus status) throws
            TransactionException {
        MockTx mockTx = txContext.get();
        LOGGER.debug("Rolling back Transaction " + mockTx.hashCode());
        txContext.set(null);
    }

    @Override
    protected boolean isExistingTransaction(Object transaction) throws
            TransactionException {
        return txContext.get() == transaction;
    }

    @Override
    protected void doSetRollbackOnly(DefaultTransactionStatus status) throws TransactionException {
        LOGGER.debug("Setting rollback only");
    }


}
