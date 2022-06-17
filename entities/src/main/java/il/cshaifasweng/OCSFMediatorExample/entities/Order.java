package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "orders_c")
public class Order  implements Serializable{
    @Id
    @Column(name = "orderid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;
    private double Cost;
    @ManyToOne
    @JoinColumn(columnDefinition = "client_id")
    private Client client;
    private boolean Card;
    private boolean Shipping;

    public boolean isShipping() {
        return Shipping;
    }

    public void setShipping(boolean shipping) {
        Shipping = shipping;
    }

    public boolean isCard() {
        return Card;
    }

    public void setCard(boolean card) {
        Card = card;
    }

    public Order(double cost) {
        Cost = cost;
    }

    public Order() {

    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }



}
