package pp.project.vmm.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="orderData")
public class Order {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name="date")
    private Date date;

    @OneToMany(mappedBy="order")
    private List<Holds> holds;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Holds> getHolds() {
        return holds;
    }

    public void setHolds(List<Holds> holds) {
        this.holds = holds;
    }

    // Constructors
    public Order(Date date) {
        this.date = date;
    }

    // toString
    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", date=" + date +
                '}';
    }
}
