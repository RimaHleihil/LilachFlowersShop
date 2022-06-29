package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="chainmanagers")

public class ChainManager extends User implements Serializable {
    private String ID;
    public ChainManager(String id, String username, String password) {

        super(username, password);
        this.ID=id;
    }

    public ChainManager() {

    }
    public String getID() {
        return ID;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
}
