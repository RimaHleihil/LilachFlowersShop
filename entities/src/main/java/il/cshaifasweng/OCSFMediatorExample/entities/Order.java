package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "orders_c")
public class Order  implements Serializable{
    @Id
    @Column(name = "orderid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderID;
    private double Cost;
    private LocalDate LocDate;
    private boolean Shipping;
    private boolean Card;
    private LocalTime LocTime;
    private LocalDate ArriveDate;
    private LocalTime ArriveTime;
    @ManyToOne
    // @JoinColumn(columnDefinition = "client_id")
    private Client client;

    public LocalDate getDate() {
        return LocDate;
    }

    public void setDate(LocalDate date) {
        this.LocDate = date;
    }

    public Order(double cost, Client client, boolean card, boolean shipping) {
        Cost = cost;
        this.client = client;
        Card = card;
        this.LocDate = LocalDate.now();
        this.LocTime = LocalTime.now();
        Shipping = shipping;
    }

    public Order(double cost, Client client, boolean card, boolean shipping,LocalDate date,LocalTime time) {
        Cost = cost;
        this.client = client;
        Card = card;
        this.LocDate = LocalDate.now();
        this.LocTime = LocalTime.now();
        this.ArriveDate=date;
        this.ArriveTime=time;
        Shipping = shipping;
    }

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
    public LocalDate getArriveDate() {
        return ArriveDate;
    }

    public void setArriveDate(LocalDate arriveDate) {
        ArriveDate = arriveDate;
    }

    public LocalTime getArriveTime() {
        return ArriveTime;
    }

    public void setArriveTime(LocalTime arriveTime) {
        ArriveTime = arriveTime;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }



}
