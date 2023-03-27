package pp.project.vmm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.Type;

import java.util.UUID;

@Entity
@Table(name="holds")
@Data
public class Holds {

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

    @ManyToOne
    @JoinColumn(name="orderId", nullable = false)
    @NonNull
    private Order order;

    @ManyToOne
    @JoinColumn(name="itemId", nullable = false)
    @NonNull
    private Item item;
}
