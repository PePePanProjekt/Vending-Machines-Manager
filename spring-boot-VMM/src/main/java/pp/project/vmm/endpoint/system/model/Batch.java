package pp.project.vmm.endpoint.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="batch")
@NoArgsConstructor
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