package services;

import java.util.List;

/**
 * Interface générique CRUD
 */
public interface Iservice<T> {
    void create(T data);
    List<T> read();
    void update(String id, T data);
    void delete(String id);
}


