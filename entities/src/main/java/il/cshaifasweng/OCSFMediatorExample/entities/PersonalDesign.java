package il.cshaifasweng.OCSFMediatorExample.entities;


import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table
public class PersonalDesign implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int PDID;
    private String PDColor;
    private double RangePrice;
    private String PDType;

    public PersonalDesign() {
    }


    public double getRangePrice() {
        return RangePrice;
    }

    public void setRangePrice(double rangePrice) {
        RangePrice = rangePrice;
    }

    public String getPDColor() {
        return PDColor;
    }

    public void setPDColor(String PDColor) {
        this.PDColor = PDColor;
    }

    public String getPDType() {
        return PDType;
    }

    public void setPDType(String PDType) {
        this.PDType = PDType;
    }
}
