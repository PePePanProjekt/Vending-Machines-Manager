package pp.project.vmm.endpoint.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name="item")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
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

    @Column(name="amount_available")
    @NonNull
    private Integer amountAvailable;

    @OneToMany(mappedBy = "item")
    private List<Sales> profits;

    @OneToMany(mappedBy="item")
    private List<Contains> contains;

    @OneToMany(mappedBy="item")
    private List<Holds> holds;
}
