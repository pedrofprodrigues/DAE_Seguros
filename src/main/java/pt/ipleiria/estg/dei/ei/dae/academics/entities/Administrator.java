package pt.ipleiria.estg.dei.ei.dae.academics.entities;


import lombok.Data;

import javax.persistence.Entity;

@Data

@Entity
public class Administrator extends User {

    public Administrator() {
    }
    String password;

    public Administrator(String username, String password, String name, String email) {
        super(username, name, email);
        this.password = password;
    }
}
