package services;

import models.Transaction;
import java.util.ArrayList;
import java.util.List;

public class TransactionService implements IService<Transaction> {

    private List<Transaction> transactions = new ArrayList<>();

    @Override
    public void create(Transaction transaction) {
        transactions.add(transaction);
    }

    @Override
    public List<Transaction> read() {
        return transactions;
    }

    @Override
    public void update(String id, Transaction data) {
        // Pas utilisé dans ce projet
    }

    @Override
    public void delete(String id) {
        transactions.removeIf(t -> t.getId().equals(id));
    }
}

