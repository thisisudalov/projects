import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(schema = "taxi")
public class Ride {
    @Id
    private long id;
    @ManyToOne
    @MapsId("id")
    private Driver driver;
    @ManyToOne
    @MapsId("id")
    private Passenger passenger;
    private LocalDateTime dateTime;
    private LocalTime duration;
    private BigDecimal cost;
}
