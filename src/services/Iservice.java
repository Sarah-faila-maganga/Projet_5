package services;

import java.util.List;

public interface IService<T> {
    void create(T data);
    List<T> read();
    void update(String id, T data);
    void delete(String id);
}

