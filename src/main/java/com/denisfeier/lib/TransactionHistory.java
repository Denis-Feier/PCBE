package com.denisfeier.lib;

import com.denisfeier.entity.Transaction;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class TransactionHistory {

    private static final Logger logger = LoggerFactory.getLogger(TransactionHistory.class);

    private static final TransactionHistory transactionHistory = new TransactionHistory();

    private List<Transaction> transactions = new ArrayList<>();

    private TransactionHistory() {}

    public static TransactionHistory shared() {
        return transactionHistory;
    }

    public static synchronized void makeTransaction(Transaction transaction) {
        logger.trace(String.format(
                        "User %d made a transaction with id %d of price %f",
                        transaction.getPersonID(),
                        transaction.getTransactedResourceID(),
                        transaction.getOfPrice()));
        shared().transactions.add(transaction);
    }

    public static synchronized List<Transaction> readTransactions() {
        return shared().transactions;
    }


}
