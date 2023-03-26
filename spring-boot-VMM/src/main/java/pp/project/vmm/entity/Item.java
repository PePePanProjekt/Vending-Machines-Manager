package pp.project.vmm.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="item")
public class Item {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="name")
    private String name;

    @Column(name="amountAvailable")
    private int amountAvailable;

    @OneToMany(mappedBy="item")
    private List<Profits> profits;

    @OneToMany(mappedBy="item")
    private List<Contains> contains;

    @OneToMany(mappedBy="item")
    private List<Holds> holds;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmountAvailable() {
        return amountAvailable;
    }

    public void setAmountAvailable(int amountAvailable) {
        this.amountAvailable = amountAvailable;
    }

    public List<Profits> getProfits() {
        return profits;
    }

    public void setProfits(List<Profits> profits) {
        this.profits = profits;
    }

    public List<Contains> getContains() {
        return contains;
    }

    public void setContains(List<Contains> contains) {
        this.contains = contains;
    }

    public List<Holds> getHolds() {
        return holds;
    }

    public void setHolds(List<Holds> holds) {
        this.holds = holds;
    }

    // Constructors
    public Item(String name, int amountAvailable) {
        this.name = name;
        this.amountAvailable = amountAvailable;
    }

    // toString
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", amountAvailable=" + amountAvailable +
                '}';
    }
}
