package dat.entities;

import dat.enums.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "package", indexes = @Index(name = "idx_tracking_number", columnList = "tracking_number"))
public class Package {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Include
    @Column(name = "tracking_number", nullable = false, unique = true, length = 100)
    private String trackingNumber;

    @Column(name = "sender", nullable = false, length = 100)
    private String sender;

    @Column(name = "receiver", nullable = false, length = 100)
    private String receiver;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status", nullable = false)
    private DeliveryStatus deliveryStatus;

    @OneToMany(mappedBy = "pkg")
    private List<Shipment> shipment;

    @Column(name = "created_date_time", nullable = false, updatable = false)
    private LocalDateTime createdDateTime;

    @ToString.Exclude
    @Column(name = "updated_date_time", nullable = false)
    private LocalDateTime updatedDateTime;


    public Package(String trackingNumber, String sender, String receiver, DeliveryStatus deliveryStatus) {
        this.trackingNumber = trackingNumber;
        this.sender = sender;
        this.receiver = receiver;
        this.deliveryStatus = deliveryStatus;
    }

    @PrePersist
    public void prePersist() {
        if (createdDateTime == null) {
            createdDateTime = LocalDateTime.now();
        }
        if (updatedDateTime == null) {
            updatedDateTime = LocalDateTime.now();
        }
    }

    @PreUpdate
    public void preUpdate() {
        updatedDateTime = LocalDateTime.now();
    }
}
