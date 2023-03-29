package pp.project.vmm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="sales")
@Data
public class Sales {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="saleTime")
    @NonNull
    private Date saleTime;

    @Column(name="price")
    @NonNull
    private float price;

    @ManyToOne
    @JoinColumn(name="vendingMachineId", nullable = false)
    private VendingMachine vendingMachine;

    @ManyToOne
    @JoinColumn(name="itemID", nullable = false)
    private Item item;
}
