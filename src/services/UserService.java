package services;

import models.User;
import java.util.ArrayList;
import java.util.List;

public class UserService implements IService<User> {

    private List<User> users = new ArrayList<>();

    @Override
    public void create(User user) {
        users.add(user);
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
                break;
            }
        }
    }

    @Override
    public void delete(String id) {
        users.removeIf(u -> u.getId().equals(id));
    }

    public User getByPhone(String phone) {
        for (User u : users) {
            if (u.getTelephone().equals(phone)) return u;
        }
        return null;
    }
}

