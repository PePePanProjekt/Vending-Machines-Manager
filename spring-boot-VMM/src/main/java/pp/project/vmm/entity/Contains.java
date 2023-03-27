package pp.project.vmm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.UUID;

@Entity
@Table(name="contains")
@Data
public class Contains {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="itemPrice")
    @NonNull
    private float itemPrice;

    @Column(name="itemAmount")
    @NonNull
    private int itemAmount;

    @Column(name="dispenserNumber")
    @NonNull
    private int dispenserNumber;

    @ManyToOne
    @JoinColumn(name="vendingMachineId", nullable = false)
    @NonNull
    private VendingMachine vendingMachine;

    @ManyToOne
    @JoinColumn(name="itemId", nullable = false)
    @NonNull
    private Item item;
}
