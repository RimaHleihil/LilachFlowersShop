package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="managers")
public class Manager extends User implements Serializable {

    public Manager(String id, String username, String password) {
        super(username, password);
    }

    public Manager() {

    }
}
