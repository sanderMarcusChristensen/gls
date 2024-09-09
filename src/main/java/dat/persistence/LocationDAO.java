package dat.persistence;

import dat.entities.Location;

import jakarta.persistence.*;

import java.util.Set;
import java.util.stream.Collectors;

public class LocationDAO implements GenericDAO<Location> {

    private static LocationDAO instance;
    private static EntityManagerFactory emf;

    public LocationDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public Location findById(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Location.class, id);
        }
    }


    @Override
    public Set<Location> getAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l", Location.class);    //nameQ in Location
            return query.getResultStream().collect(Collectors.toSet());
        }
    }

    @Override
    public Location create(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            // Check if the location already exists by latitude and longitude
            TypedQuery<Location> query = em.createQuery("SELECT l FROM Location l WHERE l.latitude = :latitude AND l.longitude = :longitude", Location.class);
            query.setParameter("latitude", location.getLatitude());
            query.setParameter("longitude", location.getLongitude());

            try {
                // If a matching location is found, use the existing one
                Location foundLocation = query.getSingleResult();
                em.getTransaction().commit();
                return foundLocation;
            } catch (NoResultException e) {
                // If no matching location exists, persist the new one
                em.persist(location);
                em.getTransaction().commit();
                return location;
            }
        }
    }

    @Override
    public Location update(Location location) {
        try (EntityManager em = emf.createEntityManager()) {
            Location locationFound = em.find(Location.class, location.getId());
            if (locationFound == null) {
                throw new EntityNotFoundException("No location to update");

            }
            em.getTransaction().begin();
            if (location.getLatitude() != null) {
                locationFound.setLatitude(location.getLatitude());
            }
            if (location.getLongitude() != null) {
                locationFound.setLongitude(location.getLongitude());
            }
            if (location.getAddress() != null) {
                locationFound.setAddress(location.getAddress());
            }

            em.getTransaction().commit();
            return locationFound;
        }
    }

    @Override
    public void delete(Location location) {
        try(EntityManager em = emf.createEntityManager()){
            em.getTransaction().begin();
            em.remove(location);
            em.getTransaction().commit();
        }
    }
}






