package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
@Entity
@Table(name = "shipment")

public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_Id", nullable = false)
    private Package pkg;

    @ManyToOne
    @JoinColumn(name = "source_location", nullable = false)
    private Location source_location;

    @ManyToOne
    @JoinColumn(name = "destiation_location", nullable = false)
    private Location destination_location;

    @Column(name = "shipment_date_time", nullable = false)
    private LocalDateTime date_time;


}
