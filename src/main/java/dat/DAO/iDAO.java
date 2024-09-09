package dat.DAO;

public interface iDAO<T> {
    T create(T t);
    T findById(Long id);
    T findByTrackingNumber(String trackingNumber);
    T update(T t);
    boolean delete(T t);
}
