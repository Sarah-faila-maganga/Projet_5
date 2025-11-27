package services;

import models.User;
import persistence.DataManagerGson; // ou DataManagerTxt
import java.util.ArrayList;
import java.util.List;

/**
 * Service utilisateur avec persistance automatique après chaque changement
 */
public class UserService implements Iservice<User> {

    private List<User> users;

    public UserService() {
        // Charger depuis persistance (changer ici pour Txt si besoin)
        this.users = DataManagerGson.loadUsers();
        if (this.users == null) this.users = new ArrayList<>();
    }

    @Override
    public void create(User user) {
        users.add(user);
        save();
    }

    @Override
    public List<User> read() {
        return users;
    }

    @Override
    public void update(String id, User newData) {
        for (User u : users) {
            if (u.getId().equals(id)) {
                u.setNom(newData.getNom());
                u.setTelephone(newData.getTelephone());
                u.setSolde(newData.getSolde());
                save();
                break;
            }
        }
    }

    @Override
    public void delete(String id) {
        users.removeIf(u -> u.getId().equals(id));
        save();
    }

    public User getByPhone(String phone) {
        for (User u : users) {
            if (u.getTelephone() != null && u.getTelephone().equals(phone)) return u;
        }
        return null;
    }

    public User getById(String id) {
        for (User u : users) if (u.getId().equals(id)) return u;
        return null;
    }

    private void save() {
        DataManagerGson.saveUsers(users); // changer pour DataManagerTxt.saveUsers(users) si tu veux TXT
    }
}


