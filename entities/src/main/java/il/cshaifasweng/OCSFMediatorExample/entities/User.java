package il.cshaifasweng.OCSFMediatorExample.entities;

import javax.persistence.*;
import java.io.Serializable;

@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Entity
@Table
public class User implements Serializable {
    @Id
    @Column(name = "userid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected int Id;
    protected String username;
    protected String password;

    public User() {};
    public User(String username, String password){
        this.username = username;
        this.password=password;
    }

    public int getId() {
        return Id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
