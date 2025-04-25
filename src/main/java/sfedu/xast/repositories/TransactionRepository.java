package sfedu.xast.repositories;

import sfedu.xast.models.Transaction;

public class TransactionRepository extends BaseRepo<Transaction, String> {

    protected TransactionRepository(Class<Transaction> entityClass) {
        super(entityClass);
    }

}
