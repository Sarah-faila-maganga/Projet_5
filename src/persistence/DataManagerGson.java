package persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import models.User;
import models.Transaction;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistance JSON via Gson
 * Fichiers: users.json, transactions.json
 */
public class DataManagerGson {

    private static final String USERS_FILE = "users.json";
    private static final String TRANSACTIONS_FILE = "transactions.json";
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static List<User> loadUsers() {
        File f = new File(USERS_FILE);
        if (!f.exists()) return new ArrayList<>();
        try (Reader r = new FileReader(f)) {
            Type type = new TypeToken<List<User>>(){}.getType();
            List<User> list = GSON.fromJson(r, type);
            return list == null ? new ArrayList<>() : list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveUsers(List<User> users) {
        try (Writer w = new FileWriter(USERS_FILE)) {
            GSON.toJson(users, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Transaction> loadTransactions() {
        File f = new File(TRANSACTIONS_FILE);
        if (!f.exists()) return new ArrayList<>();
        try (Reader r = new FileReader(f)) {
            Type type = new TypeToken<List<Transaction>>(){}.getType();
            List<Transaction> list = GSON.fromJson(r, type);
            return list == null ? new ArrayList<>() : list;
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static void saveTransactions(List<Transaction> transactions) {
        try (Writer w = new FileWriter(TRANSACTIONS_FILE)) {
            GSON.toJson(transactions, w);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
