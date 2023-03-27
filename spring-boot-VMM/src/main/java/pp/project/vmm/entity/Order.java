package pp.project.vmm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="orderData")
@Data
public class Order {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Temporal(TemporalType.DATE)
    @Column(name="date")
    @NonNull
    private Date date;

    @OneToMany(mappedBy="order")
    private List<Holds> holds;
}
