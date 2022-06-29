package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="Clients")
public class Client extends User implements Serializable {
    private String ID;
    private String CardNum;
    private String cvv;
    private String AccountType;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Item> itemList= Collections.synchronizedList(new ArrayList<>());
    @OneToMany
    private List<Order> orderList=new ArrayList<>();

    public Client(String id, String username, String password,String AT,String CardNum,String cvv) {
        super(username, password);
        this.ID=id;
        this.AccountType=AT;
        this.cvv=cvv;
        this.CardNum=CardNum;
    }
    public Client(String id, String username, String password, ArrayList itemList,String AT,String CardNum,String cvv) {
        super(username, password);
        this.ID=id;
        this.itemList=itemList;
        this.AccountType=AT;
        this.cvv=cvv;
        this.CardNum=CardNum;
    }
    public Client(String id, String username, String password, ArrayList itemList, ArrayList orderList,String AT,String CardNum,String cvv) {
        super(username, password);
        this.ID=id;
        this.itemList=itemList;
        this.orderList=orderList;
        this.AccountType=AT;
        this.cvv=cvv;
        this.CardNum=CardNum;
    }


    public Client() {

    }



    //__ paymentlist &&  cancellist

    public void setCardNum(String cardNum) {
        CardNum = cardNum;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    public String getCardNum() {
        return CardNum;
    }

    public String getCvv() {
        return cvv;
    }
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    public List<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<Order> orderList) {
        this.orderList = orderList;
    }

    public void AddItem(Item item)
    {
        this.itemList.add(item);
    }

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public String getAccountType() {
        return AccountType;
    }
    public void setAccountType(String accountType) {
        AccountType = accountType;
    }
}