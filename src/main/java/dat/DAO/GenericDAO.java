package dat.DAO;

import java.util.Set;

public interface GenericDAO <T>{

    T findById(Long id);
    Set<T> getAll();
    T create(T t);
    T update(T t);
    void delete(T t);

}
