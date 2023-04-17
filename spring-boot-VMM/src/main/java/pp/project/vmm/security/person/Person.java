package pp.project.vmm.security.person;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import static jakarta.persistence.GenerationType.SEQUENCE;

@Entity
@Data
public class Person {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Integer id;

    private String name;

}
