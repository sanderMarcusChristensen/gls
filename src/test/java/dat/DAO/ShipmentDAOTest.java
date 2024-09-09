package dat.DAO;

import dat.entities.Location;
import dat.entities.Package;
import dat.entities.Shipment;
import dat.enums.DeliveryStatus;
import dat.persistence.HibernateConfig;
import dat.persistence.HibernateConfigThomas;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ShipmentDAOTest {

    static ShipmentDAO dao;
    static EntityManagerFactory emf;
    private Shipment s1, s2;
    private Package p1, p2;
    private Location l1, l2, l3;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        emf = HibernateConfigThomas.getEntityManagerFactoryForTest();
        dao = new ShipmentDAO(emf);
    }


    @BeforeEach
    void setUp() {
        try (EntityManager em = emf.createEntityManager()) {

            p1 = Package.builder().trackingNumber("11111").sender("Otto").receiver("Sander").deliveryStatus(DeliveryStatus.PENDING).build();
            p2 = Package.builder().trackingNumber("22222").sender("Victor").receiver("Nanna").deliveryStatus(DeliveryStatus.DELIVERED).build();

            l1 = Location.builder().latitude(1.1).longitude(1.1).address("Supervej").build();
            l2 = Location.builder().latitude(2.1).longitude(2.1).address("NoobVej").build();
            l3 = Location.builder().latitude(3.1).longitude(3.1).address("HeyHeyVej").build();

            s1 = Shipment.builder().pkg(p1).source_location(l1).destination_location(l2).date_time(LocalDateTime.of(2024, 2, 19, 11, 15)).build();
            s2 = Shipment.builder().pkg(p2).source_location(l3).destination_location(l2).date_time(LocalDateTime.of(2024, 4, 12, 15, 24)).build();

            p1.getShipment().add(s1);
            p2.getShipment().add(s2);

            em.getTransaction().begin();

            em.createQuery("DELETE FROM Shipment").executeUpdate();
            em.createQuery("DELETE FROM Package").executeUpdate();
            em.createQuery("DELETE FROM Location").executeUpdate();

            em.persist(p1);
            em.persist(p2);
            em.persist(l1);
            em.persist(l2);
            em.persist(l3);
            em.persist(s1);
            em.persist(s2);
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
        Shipment s = dao.findById(s1.getId());

        Long ex = s.getId();
        Long a = s1.getId();
        assertEquals(ex, a);
    }

    @Test
    void getAll() {

        int a = 2;
        int ex = dao.getAll().size();
        assertEquals(a, ex);
    }

    @Test
    void create() {

        Shipment s3 = Shipment.builder()
                .destination_location(l2)
                .source_location(l3)
                .pkg(p2)
                .date_time(LocalDateTime.now())
                .build();

        dao.create(s3);

        int ex = 3;
        int a = dao.getAll().size();
        assertEquals(ex, a);

    }

    @Test
    void update() {

        Shipment updateShippy = s1;

        updateShippy.setSource_location(l2);
        Shipment a = dao.update(updateShippy);
        Shipment ex = s1;

        assertEquals(ex.getId(), a.getId());
    }

    @Test
    void delete() {
        Shipment shippy = dao.findById(s1.getId());

        Long ex = shippy.getId();
        Long a = s1.getId();
        assertEquals(ex, a);
    }
}