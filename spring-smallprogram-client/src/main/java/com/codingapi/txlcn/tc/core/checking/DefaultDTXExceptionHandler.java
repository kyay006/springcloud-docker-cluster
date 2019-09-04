//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.codingapi.txlcn.tc.core.checking;

import com.codingapi.txlcn.common.exception.TransactionClearException;
import com.codingapi.txlcn.common.exception.TransactionException;
import com.codingapi.txlcn.common.exception.UserRollbackException;
import com.codingapi.txlcn.logger.TxLogger;
import com.codingapi.txlcn.tc.core.template.TransactionCleanTemplate;
import com.codingapi.txlcn.tc.txmsg.TMReporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DefaultDTXExceptionHandler implements DTXExceptionHandler {
    private static final Logger log = LoggerFactory.getLogger(DefaultDTXExceptionHandler.class);
    private static final TxLogger txLogger = TxLogger.newLogger(DefaultDTXExceptionHandler.class);
    private final TransactionCleanTemplate transactionCleanTemplate;
    private final TMReporter tmReporter;

    @Autowired
    public DefaultDTXExceptionHandler(TransactionCleanTemplate transactionCleanTemplate, TMReporter tmReporter) {
        this.transactionCleanTemplate = transactionCleanTemplate;
        this.tmReporter = tmReporter;
    }

    public void handleCreateGroupBusinessException(Object params, Throwable ex) throws TransactionException {
        throw new TransactionException(ex);
    }

    public void handleCreateGroupMessageException(Object params, Throwable ex) throws TransactionException {
        throw new TransactionException(ex);
    }

    public void handleJoinGroupBusinessException(Object params, Throwable ex) throws TransactionException {
        List paramList = (List)params;
        String groupId = (String)paramList.get(0);
        String unitId = (String)paramList.get(1);
        String unitType = (String)paramList.get(2);

        try {
            this.transactionCleanTemplate.clean(groupId, unitId, unitType, 0);
        } catch (TransactionClearException var8) {
            txLogger.error(groupId, unitId, "join group", "clean [{}]transaction fail.", new Object[]{unitType});
        }

        throw new TransactionException(ex);
    }

    public void handleJoinGroupMessageException(Object params, Throwable ex) throws TransactionException {
        throw new TransactionException(ex);
    }

    public void handleNotifyGroupBusinessException(Object params, Throwable ex) {
        List paramList = (List)params;
        String groupId = (String)paramList.get(0);
        int state = (Integer)paramList.get(1);
        String unitId = (String)paramList.get(2);
        String transactionType = (String)paramList.get(3);
        if (ex instanceof UserRollbackException) {
            state = 0;
        }

        if (ex != null && ex.getCause() != null && ex.getCause() instanceof UserRollbackException) {
            state = 0;
        }

        try {
            this.transactionCleanTemplate.clean(groupId, unitId, transactionType, state);
        } catch (TransactionClearException var9) {
            txLogger.error(groupId, unitId, "notify group", "{} > clean transaction error.", new Object[]{transactionType});
        }

    }

    public void handleNotifyGroupMessageException(Object params, Throwable ex) {
        List paramList = (List)params;
        String groupId = (String)paramList.get(0);
        int state = (Integer)paramList.get(1);
        if (state == 0) {
            this.handleNotifyGroupBusinessException(params, ex);
        } else {
            String unitId = (String)paramList.get(2);
            String transactionType = (String)paramList.get(3);

            try {
                this.transactionCleanTemplate.cleanWithoutAspectLog(groupId, unitId, transactionType, state);
            } catch (TransactionClearException var9) {
                txLogger.error(groupId, unitId, "notify group", "{} > cleanWithoutAspectLog transaction error.", new Object[]{transactionType});
            }

            this.tmReporter.reportTransactionState(groupId, (String)null, Short.valueOf((short)2), state);
        }
    }
}
