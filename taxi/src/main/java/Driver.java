import javax.persistence.*;
import java.util.Set;

@Entity
@Table(schema = "taxi", name = "drivers")
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String carNumber;
    private int rating;
    private String experience;
    private long phoneNumber;
    /*@ManyToMany(mappedBy = "passengers")
    private Set<Passenger> passengers;*/
    @OneToMany(mappedBy = "drivers")
    private Set<Ride> rides;
    @ManyToOne
    @JoinColumn(name = "id")
    private ClassOfAuto classOfAuto;
}
