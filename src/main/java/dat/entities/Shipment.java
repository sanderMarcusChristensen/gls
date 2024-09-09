package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
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

    @Builder
    public Shipment(Package pkg, Location source_location, Location destination_location, LocalDateTime date_time) {
        this.pkg = pkg;
        this.source_location = source_location;
        this.destination_location = destination_location;
        this.date_time = date_time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shipment shipment = (Shipment) o;
        return Objects.equals(id, shipment.id) && Objects.equals(pkg, shipment.pkg) && Objects.equals(source_location, shipment.source_location) && Objects.equals(destination_location, shipment.destination_location) && Objects.equals(date_time, shipment.date_time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pkg, source_location, destination_location, date_time);
    }
}
