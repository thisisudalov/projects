import javax.persistence.*;
import java.util.Set;

@Entity
public class ClassOfAuto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nameOfClass;
    @OneToMany(mappedBy = "drivers")
    Set<Driver> drivers;
}
