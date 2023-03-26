package pp.project.vmm.entity;

import jakarta.persistence.*;

@Entity
@Table(name="contains")
public class Contains {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="itemPrice")
    private float itemPrice;

    @Column(name="itemAmount")
    private int itemAmount;

    @Column(name="dispenserNumber")
    private int dispenserNumber;

    @ManyToOne
    @JoinColumn(name="vendingMachineId", nullable = false)
    private VendingMachine vendingMachine;

    @ManyToOne
    @JoinColumn(name="itemId", nullable = false)
    private Item item;

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(float itemPrice) {
        this.itemPrice = itemPrice;
    }

    public int getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(int itemAmount) {
        this.itemAmount = itemAmount;
    }

    public int getDispenserNumber() {
        return dispenserNumber;
    }

    public void setDispenserNumber(int dispenserNumber) {
        this.dispenserNumber = dispenserNumber;
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
    public Contains(float itemPrice, int itemAmount, int dispenserNumber, VendingMachine vendingMachine, Item item) {
        this.itemPrice = itemPrice;
        this.itemAmount = itemAmount;
        this.dispenserNumber = dispenserNumber;
        this.vendingMachine = vendingMachine;
        this.item = item;
    }

    // toString
    @Override
    public String toString() {
        return "Contains{" +
                "id=" + id +
                ", itemPrice=" + itemPrice +
                ", itemAmount=" + itemAmount +
                ", dispenserNumber=" + dispenserNumber +
                ", vendingMachine=" + vendingMachine +
                ", item=" + item +
                '}';
    }
}
