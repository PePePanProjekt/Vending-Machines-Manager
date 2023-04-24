package pp.project.vmm.endpoint.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="vending_machine")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Data
public class VendingMachine {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="location")
    @NonNull
    private String location;

    @Column(name="name")
    @NonNull
    private String name;

    @Column(name="dispenser_amount")
    @NonNull
    private Integer dispenserAmount;

    @Column(name="dispenser_depth")
    @NonNull
    private Integer dispenserDepth;

    @OneToMany(mappedBy="vendingMachine")
    private List<Sale> profits;

    @OneToMany(mappedBy="vendingMachine")
    private List<Contains> contains;
}
