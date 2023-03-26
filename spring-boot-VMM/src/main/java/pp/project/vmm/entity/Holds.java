package pp.project.vmm.entity;

import jakarta.persistence.*;

@Entity
@Table(name="holds")
public class Holds {

    // Fields
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="itemPrice")
    private float itemPrice;

    @Column(name="itemAmount")
    private int itemAmount;

    @ManyToOne
    @JoinColumn(name="orderId", nullable = false)
    private Order order;

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

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    // Constructors
    public Holds(float itemPrice, int itemAmount, Order order, Item item) {
        this.itemPrice = itemPrice;
        this.itemAmount = itemAmount;
        this.order = order;
        this.item = item;
    }

    // toString
    @Override
    public String toString() {
        return "Holds{" +
                "id=" + id +
                ", itemPrice=" + itemPrice +
                ", itemAmount=" + itemAmount +
                ", order=" + order +
                ", item=" + item +
                '}';
    }
}
