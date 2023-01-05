package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Expert extends User {


    @ManyToOne(cascade = CascadeType.REMOVE)
    private Company company;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<Occurrence> occurrences;

    private String password;

    public Expert() {
        this.occurrences = new ArrayList<>();
    }

    public Expert(String username, String password, String name, String email, Company company) {
        super(username, name, email);
        this.password = password;
        this.company = company;
        this.occurrences = new ArrayList<>();
    }




    public void addOccurrence(Occurrence occurrence) {
        if (! this.occurrences.contains(occurrence)) {
            this.occurrences.add(occurrence);
        }
    }

    public void removeOccurrence(Occurrence occurrence) {
        this.occurrences.remove(occurrence);
    }
}
