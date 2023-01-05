package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@Entity


@Data
public class Company {

    @Id
    @NotNull
    private String name;

    @NotNull
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Expert> experts;

    @NotNull
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Policy> policies;


    public Company(String name) {
        this();
        this.name = name;
    }

    public Company() {
        this.experts = new ArrayList<>();
        this.policies = new ArrayList<>();
    }

    public void addExpert(Expert expert) {
        if (! this.experts.contains(expert)) {
            this.experts.add(expert);
        }
    }

    public void removeExpert(Expert expert) {
        this.experts.remove(expert);
    }

    public void addPolicy(Policy policy) {
        if (! this.policies.contains(policy)) {
            this.policies.add(policy);
        }
    }

    public void removePolicy(Policy policy) {
        this.experts.remove(policy);
    }

}
