package sfedu.xast.repositories;

import org.hibernate.Session;
import sfedu.xast.models.Transaction;
import sfedu.xast.utils.Status;

import java.util.List;

public class TransactionRepository extends BaseRepo<Transaction, String> {

    protected TransactionRepository(Class<Transaction> entityClass) {
        super(entityClass);
    }

    public List<Transaction> findByStatus(Status status) {
        try (Session session = getSession()) {
            return session.createQuery(
                            "SELECT t FROM Transaction t WHERE t.status = :status", Transaction.class)
                    .setParameter("status", status)
                    .getResultList();
        }
    }

    public List<Transaction> findByUser(String userId) {
        try (Session session = getSession()) {
            return session.createQuery(
                            "SELECT t FROM Transaction t WHERE t.changeId.userOffering = :userId OR t.changeId.userRequesting = :userId",
                            Transaction.class)
                    .setParameter("userId", userId)
                    .getResultList();
        }
    }
}
