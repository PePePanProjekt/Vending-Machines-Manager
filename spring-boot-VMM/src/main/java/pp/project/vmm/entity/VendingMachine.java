package pp.project.vmm.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="vendingMachine")
public class VendingMachine {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="location")
    private String location;

    @Column(name="name")
    private String name;

    @Column(name="dispenserAmount")
    private int dispenserAmount;

    @Column(name="dispenserDepth")
    private int dispenserDepth;

    @OneToMany(mappedBy="vendingMachine")
    private List<Profits> profits;

    @OneToMany(mappedBy="vendingMachine")
    private List<Contains> contains;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDispenserAmount() {
        return dispenserAmount;
    }

    public void setDispenserAmount(int dispenserAmount) {
        this.dispenserAmount = dispenserAmount;
    }

    public int getDispenserDepth() {
        return dispenserDepth;
    }

    public void setDispenserDepth(int dispenserDepth) {
        this.dispenserDepth = dispenserDepth;
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

    // Constructors
    public VendingMachine(String location, String name, int dispenserAmount, int dispenserDepth) {
        this.location = location;
        this.name = name;
        this.dispenserAmount = dispenserAmount;
        this.dispenserDepth = dispenserDepth;
    }

    // toString
    @Override
    public String toString() {
        return "VendingMachine{" +
                "id=" + id +
                ", location='" + location + '\'' +
                ", name='" + name + '\'' +
                ", dispenserAmount=" + dispenserAmount +
                ", dispenserDepth=" + dispenserDepth +
                '}';
    }
}
