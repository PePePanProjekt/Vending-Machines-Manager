package pp.project.vmm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="profits")
@Data
public class Profits {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Temporal(TemporalType.DATE)
    @Column(name="day")
    @NonNull
    private Date day;

    @Column(name="price")
    @NonNull
    private float price;

    @ManyToOne
    @JoinColumn(name="vendingMachineId", nullable = false)
    @NonNull
    private VendingMachine vendingMachine;

    @ManyToOne
    @JoinColumn(name="itemID", nullable = false)
    @NonNull
    private Item item;
}
