package il.cshaifasweng.OCSFMediatorExample.entities;
import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="item")

public class Item implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private double price;
    private String kind;
    private String ImgURL;

    public Item(){}

    public Item(String name,double price, String kind,String ImageURL){
        this.name=name;
        this.price=price;
        this.kind=kind;
        this.ImgURL=ImageURL;
    }
    public String getImgURL() {
        return ImgURL;
    }

    public void setImgURL(String imgURL) {
        ImgURL = imgURL;
    }

    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public double getPrice(){
        return this.price;
    }
    public String getKind(){
        return this.kind;
    }
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name=name;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public void setKind(String kind){
        this.kind= kind;
    }

}
