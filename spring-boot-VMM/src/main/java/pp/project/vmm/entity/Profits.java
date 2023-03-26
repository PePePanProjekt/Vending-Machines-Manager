package pp.project.vmm.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name="profits")
public class Profits {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Temporal(TemporalType.DATE)
    @Column(name="day")
    private Date day;

    @Column(name="price")
    private float price;

    @ManyToOne
    @JoinColumn(name="vendingMachineId", nullable = false)
    private VendingMachine vendingMachine;

    @ManyToOne
    @JoinColumn(name="itemID", nullable = false)
    private Item item;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public VendingMachine getVendingMachine() {
        return vendingMachine;
    }

    public void setVendingMachine(VendingMachine vendingMachine) {
        this.vendingMachine = vendingMachine;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    // Constructors
    public Profits(Date day, float price, VendingMachine vendingMachine, Item item) {
        this.day = day;
        this.price = price;
        this.vendingMachine = vendingMachine;
        this.item = item;
    }

    // toString

    @Override
    public String toString() {
        return "Profits{" +
                "id=" + id +
                ", day=" + day +
                ", price=" + price +
                ", vendingMachine=" + vendingMachine +
                ", item=" + item +
                '}';
    }
}
