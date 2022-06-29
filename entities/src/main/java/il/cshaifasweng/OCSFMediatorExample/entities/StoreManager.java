package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="storemanagers")
public class StoreManager extends User implements Serializable {

    private String ID;
    public StoreManager(String id, String username, String password) {

        super(username, password);
        this.ID=id;
    }

    public StoreManager() {

    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
}
