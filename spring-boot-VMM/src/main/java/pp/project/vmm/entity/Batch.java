package pp.project.vmm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="batch")
@Data
public class Batch {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="id")
    private UUID id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="date")
    @NonNull
    private Date date;

    @OneToMany(mappedBy="batch")
    private List<Holds> holds;
}
