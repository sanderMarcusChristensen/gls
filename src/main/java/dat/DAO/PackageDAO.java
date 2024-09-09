package dat.DAO;


import dat.entities.Package;
import dat.enums.HibernateConfigState;
import dat.persistence.HibernateConfig;
import jakarta.persistence.*;

public class PackageDAO implements iDAO<Package> {

    private static PackageDAO instance;
    private static EntityManagerFactory emf;

    private PackageDAO(){
    }

    public static PackageDAO getInstance(HibernateConfigState state){
        if(instance == null){
            emf = HibernateConfig.getEntityManagerFactoryConfig(state, "gls");
            instance = new PackageDAO();
        }
        return instance;
    }

    public static EntityManagerFactory getEmf(){
        return emf;
    }

    public static void close(){
        if(emf != null && emf.isOpen()){
            emf.close();
        }
    }

    @Override
    public Package create(Package aPackage) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(aPackage);
            em.getTransaction().commit();
        }
        return aPackage;
    }

    @Override
    public Package findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return  em.find(Package.class, id);
        }
    }

    @Override
    public Package findByTrackingNumber(String trackingNumber) {
       // use a typedquery
        try (EntityManager em = emf.createEntityManager()){
            TypedQuery<Package> query = em.createQuery("SELECT p FROM Package p WHERE p.trackingNumber = :trackingNumber", Package.class);
            query.setParameter("trackingNumber", trackingNumber);
            query.setMaxResults(1);
            return query.getSingleResult();
        }
    }

    @Override
    public Package update(Package aPackage) {
        Package updatedPackage = null;
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            updatedPackage = em.merge(aPackage);
            em.getTransaction().commit();
        }
        return updatedPackage;
    }

    @Override
    public boolean delete(Package aPackage) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.remove(aPackage);
            em.getTransaction().commit();
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}

