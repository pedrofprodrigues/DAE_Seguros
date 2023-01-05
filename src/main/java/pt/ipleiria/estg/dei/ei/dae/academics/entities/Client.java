package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT s FROM Client s ORDER BY s.name"
        )
})

@Data

public class Client extends User {

    @NotNull
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinTable
    private List<Policy> policies;


    public Client() {
        this.policies = new ArrayList<>();

    }

    public Client(String username, String name, String email) {
        super(username, name, email);
        this.policies = new ArrayList<>();
    }




    public void AddPolicy(Policy policy) {
        if (! this.policies.contains(policy)) {
            this.policies.add(policy);
        }
    }

    public void removePolicy(Policy policy) {
        this.policies.remove(policy);
    }


}
