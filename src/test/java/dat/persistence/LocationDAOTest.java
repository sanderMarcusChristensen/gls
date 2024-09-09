package dat.persistence;

import dat.entities.Location;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class LocationDAOTest {
    static LocationDAO dao;
    static EntityManagerFactory emf;
    Location l1, l2, l3;

    @BeforeAll
    static void beforeAll() {
        emf = HibernateConfigThomas.getEntityManagerFactoryForTest();
        dao = new LocationDAO(emf);
    }


    @BeforeEach
    void setUp() {

        try (EntityManager em = emf.createEntityManager()) {

            l1 = Location.builder().latitude(1.1).longitude(1.1).address("jojo").build();
            l2 = Location.builder().latitude(2.1).longitude(2.1).address("sup").build();
            l3 = Location.builder().latitude(1.1).longitude(1.1).address("ehhh").build();

            em.getTransaction().begin();
            em.createQuery("DELETE FROM Location ").executeUpdate();

            em.persist(l1);
            em.persist(l2);
            em.persist(l3);
            em.getTransaction().commit();
        }
    }

    @AfterAll
    static void tearDown() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
    @Test
    void findById() {
        Location a = dao.findById(l1.getId());
        assertEquals(l1, a);
    }

    @Test
    void getAll() {

        int ex = 3;
        int a = dao.getAll().size();
        assertEquals(a,ex);
    }

    @Test
    void create() {

        Location l4 = Location.builder()
                .latitude(4.1)
                .longitude(4.1)
                .address("hahahah").build();
        dao.create(l4);
        assertEquals(l4, dao.findById(l4.getId()));

    }

    @Test
    void update() {

        Location updatedLocation = l1;
        l1.setAddress("theAddressIsNowUpdated");
        Location a = dao.update(updatedLocation);
        String ex = "theAddressIsNowUpdated";
        Assertions.assertEquals(ex, a.getAddress());
    }

    @Test
    void delete() {

        int loca = dao.getAll().size();
        dao.delete(l1);
        int ex = loca - 1;
        int a = dao.getAll().size();
        assertEquals(ex,a);

    }
}