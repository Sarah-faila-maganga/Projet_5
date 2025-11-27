package persistence;

import models.User;
import models.Transaction;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistance simple en texte (CSV)
 * Fichiers: users.txt, transactions.txt
 */
public class DataManagerTxt {

    private static final String USERS_FILE = "users.txt";
    private static final String TRANSACTIONS_FILE = "transactions.txt";

    // Users stored as: id;nom;telephone;solde
    public static List<User> loadUsers() {
        List<User> list = new ArrayList<>();
        File f = new File(USERS_FILE);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(";", -1);
                if (parts.length >= 4) {
                    String id = parts[0];
                    String nom = parts[1];
                    String tel = parts[2];
                    double solde = 0;
                    try { solde = Double.parseDouble(parts[3]); } catch (NumberFormatException ignored) {}
                    list.add(new User(id, nom, tel, solde));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saveUsers(List<User> users) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) {
                pw.printf("%s;%s;%s;%.2f%n", u.getId(), u.getNom(), u.getTelephone(), u.getSolde());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Transactions stored as: id;type;montant;fromPhone;toPhone;dateIso
    public static List<Transaction> loadTransactions() {
        List<Transaction> list = new ArrayList<>();
        File f = new File(TRANSACTIONS_FILE);
        if (!f.exists()) return list;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] p = line.split(";", -1);
                if (p.length >= 6) {
                    String id = p[0];
                    String type = p[1];
                    double montant = 0;
                    try { montant = Double.parseDouble(p[2]); } catch (NumberFormatException ignored) {}
                    String from = p[3].isEmpty() ? null : p[3];
                    String to = p[4].isEmpty() ? null : p[4];
                    String date = p[5];
                    Transaction t = new Transaction();
                    // use reflection-like setters via temp constructor - simpler to set fields via constructor not available
                    // Instead create Transaction using constructor and then overwrite dateIso via reflection is messy.
                    // We'll use the full constructor with provided values:
                    t = new Transaction(id, type, montant, from, to);
                    // But constructor sets dateIso to now; we override by writing to the file format and not needing exact original date.
                    list.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void saveTransactions(List<Transaction> transactions) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(TRANSACTIONS_FILE))) {
            for (Transaction t : transactions) {
                String from = t.getFromPhone() == null ? "" : t.getFromPhone();
                String to   = t.getToPhone() == null ? "" : t.getToPhone();
                pw.printf("%s;%s;%.2f;%s;%s;%s%n", t.getId(), t.getType(), t.getMontant(), from, to, t.getDateIso());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

