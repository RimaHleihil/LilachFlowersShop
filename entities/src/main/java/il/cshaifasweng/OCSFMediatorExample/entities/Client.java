package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name="Clients")
public class Client extends User implements Serializable {
    private String ID;
    private long CardNum;
    private int cvv;
    private String AccountType;

/*
    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    @OneToMany
    private List<Item> itemList= Collections.synchronizedList(new ArrayList<>());
*/

    public Client(String id, String username, String password) {
        super(username, password);
        this.ID=id;
    }
/*
    public Client(String id, String username, String password, ArrayList itemList) {
        super(username, password);
        this.ID=id;
        this.itemList=itemList;
    }
*/


    public Client() {

    }

    //__ paymentlist &&  cancellist

/*

    public void AddItem(Item item)
    {
        this.itemList.add(item);
    }
*/

    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }

    public long getCardNum() {
        return CardNum;
    }
    public void setCardNum(long cardNum) {
        CardNum = cardNum;
    }

    public int getCvv() {
        return cvv;
    }
    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    public String getAccountType() {
        return AccountType;
    }
    public void setAccountType(String accountType) {
        AccountType = accountType;
    }
}
