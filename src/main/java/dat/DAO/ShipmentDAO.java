package dat.DAO;

import dat.entities.Location;
import dat.entities.Package;
import dat.entities.Shipment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceException;

import java.util.Set;
import java.util.stream.Collectors;

public class ShipmentDAO implements GenericDAO<Shipment> {

    private EntityManagerFactory emf;
    public ShipmentDAO(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public Shipment findById(Long id) {
        try(EntityManager em = emf.createEntityManager()){
            return em.find(Shipment.class, id);
        }
    }

    @Override
    public Set<Shipment> getAll() {
        try(EntityManager em = emf.createEntityManager()){
            return em.createQuery("SELECT s FROM Shipment s", Shipment.class)
                    .getResultStream()
                    .collect(Collectors.toSet());
        }
    }


    @Override
    public Shipment create(Shipment shipment) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            Package pFound = em.find(Package.class, shipment.getPkg().getId());
            Location destinationFound = em.find(Location.class, shipment.getDestination_location().getId());
            Location sourceLocationFound = em.find(Location.class, shipment.getSource_location().getId());

            if (pFound == null) {
                em.persist(shipment.getPkg());
            }
            if (destinationFound == null) {
                em.persist(shipment.getDestination_location());
            }
            if (sourceLocationFound == null) {
                em.persist(shipment.getSource_location());
            }

            em.persist(shipment);
            em.getTransaction().commit();

            return shipment;
        } catch (PersistenceException e){
            System.out.println("create shipment failed" + e);
            return null;
        }
    }

    @Override
    public Shipment update(Shipment shipment) {
        try(EntityManager em = emf.createEntityManager()){
            Shipment shipmentFound = em.find(Shipment.class, shipment.getId());
            em.getTransaction().begin();

            if(shipmentFound.getPkg() != null){
                shipmentFound.setPkg(shipment.getPkg());
            }
            if(shipmentFound.getDestination_location() != null){
                shipmentFound.setDestination_location(shipment.getDestination_location());
            }
            if(shipmentFound.getSource_location() != null){
                shipmentFound.setSource_location(shipment.getSource_location());
            }
            em.getTransaction().commit();
            return shipmentFound;
        }
    }

    @Override
    public void delete(Shipment shipment) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(shipment);
            em.getTransaction().commit();
        }

    }
}
