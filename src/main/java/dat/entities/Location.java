package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Entity
@Table(name = "location")
@NamedQuery(name = "findAllLocations", query = "SELECT l FROM Location l")

public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    @Column( nullable = false)
    private String address;

    @Builder
    public Location(Double latitude, Double longitude, String address) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(id, location.id) && Objects.equals(latitude, location.latitude) && Objects.equals(longitude, location.longitude) && Objects.equals(address, location.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, latitude, longitude, address);
    }
}
