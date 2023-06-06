package pp.project.vmm.endpoint.system.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="batch")
@NoArgsConstructor(force = true)
@RequiredArgsConstructor
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

    @Column(name="name")
    private String name;

    @Column(name = "archived")
    @NonNull
    private Boolean archived;

    @OneToMany(mappedBy="batch")
    private List<Holds> holds;

    public Batch(Date date, String name, boolean archived) {
        this.date = date;
        this.name = name;
        this.archived = archived;
    }
}
