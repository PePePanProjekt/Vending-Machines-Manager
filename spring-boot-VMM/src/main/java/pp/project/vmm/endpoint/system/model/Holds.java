package pp.project.vmm.endpoint.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Entity
@Data
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
@Table(name="holds")
public class Holds {

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

    @ManyToOne
    @JoinColumn(name="batch_id", nullable = false)
    @NonNull
    private Batch batch;

    @ManyToOne
    @JoinColumn(name="item_id", nullable = false)
    @NonNull
    private Item item;
}
