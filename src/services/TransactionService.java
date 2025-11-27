package services;

import models.Transaction;
import persistence.DataManagerGson; // ou DataManagerTxt
import java.util.ArrayList;
import java.util.List;

public class TransactionService implements Iservice<Transaction> {

    private List<Transaction> transactions;

    public TransactionService() {
        this.transactions = DataManagerGson.loadTransactions();
        if (this.transactions == null) this.transactions = new ArrayList<>();
    }

    @Override
    public void create(Transaction transaction) {
        transactions.add(transaction);
        save();
    }

    @Override
    public List<Transaction> read() {
        return transactions;
    }

    @Override
    public void update(String id, Transaction data) {
        // Optionnel : on peut implémenter si demandé
        for (int i = 0; i < transactions.size(); i++) {
            if (transactions.get(i).getId().equals(id)) {
                transactions.set(i, data);
                save();
                break;
            }
        }
    }

    @Override
    public void delete(String id) {
        transactions.removeIf(t -> t.getId().equals(id));
        save();
    }

    private void save() {
        DataManagerGson.saveTransactions(transactions); // ou DataManagerTxt.saveTransactions(...)
    }
}


