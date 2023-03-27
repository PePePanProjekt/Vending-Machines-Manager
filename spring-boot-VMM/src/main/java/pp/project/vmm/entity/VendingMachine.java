package pp.project.vmm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="vendingMachine")
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

    @Column(name="dispenserAmount")
    @NonNull
    private int dispenserAmount;

    @Column(name="dispenserDepth")
    @NonNull
    private int dispenserDepth;

    @OneToMany(mappedBy="vendingMachine")
    private List<Profits> profits;

    @OneToMany(mappedBy="vendingMachine")
    private List<Contains> contains;
}
