package pp.project.vmm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="item")
@Data
public class Item {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Column(name="name")
    @NonNull
    private String name;

    @Column(name="amountAvailable")
    @NonNull
    private int amountAvailable;

    @OneToMany(mappedBy = "item")
    @NonNull
    private List<Profits> profits;

    @OneToMany(mappedBy="item")
    private List<Contains> contains;

    @OneToMany(mappedBy="item")
    private List<Holds> holds;
}
