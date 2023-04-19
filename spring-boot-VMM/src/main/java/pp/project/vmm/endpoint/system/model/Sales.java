package pp.project.vmm.endpoint.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name="sales")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class Sales {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="sale_time")
    @NonNull
    private Date saleTime;

    @Column(name="price")
    @NonNull
    private Float price;

    @ManyToOne
    @JoinColumn(name="vending_machine_id", nullable = false)
    private VendingMachine vendingMachine;

    @ManyToOne
    @JoinColumn(name="item_id", nullable = false)
    private Item item;
}
