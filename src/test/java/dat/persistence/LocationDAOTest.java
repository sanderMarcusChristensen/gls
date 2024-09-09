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
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}