package pp.project.vmm.endpoint.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Table(name="contains")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class Contains {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="item_price")
    @NonNull
    private Float itemPrice;

    @Column(name="item_amount")
    @NonNull
    private Integer itemAmount;

    @Column(name="dispenser_number")
    @NonNull
    private Integer dispenserNumber;

    @ManyToOne
    @JoinColumn(name="vending_machine_id", nullable = false)
    @NonNull
    private VendingMachine vendingMachine;

    @ManyToOne
    @JoinColumn(name="item_id", nullable = false)
    @NonNull
    private Item item;
}
