import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = "taxi", name = "passengers")
public class Passenger {
    @Id
    private long id;
    private String name;
    private long phoneNumber;
    /*@ManyToMany
    @JoinTable(name = "rides", joinColumns = @JoinColumn(name = "passenger_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "driver_id", referencedColumnName = "id"))
    private Set<Driver> drivers;
*/
    @OneToMany(mappedBy = "passengers")
    private Set<Ride> rides;

}
