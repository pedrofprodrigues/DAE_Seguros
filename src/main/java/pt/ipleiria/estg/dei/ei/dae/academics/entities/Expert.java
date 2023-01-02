package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Expert extends User {

    @NotNull
    private String company;

    @ManyToMany(mappedBy = "experts", fetch = FetchType.EAGER)
    private List<Occurrence> occurrences;

    public Expert() {
        this.occurrences = new ArrayList<>();
    }

    public Expert(String username, String password, String name, String email, String company) {
        super(username, password, name, email);
        this.company = company;
        this.occurrences = new ArrayList<>();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public List<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(List<Occurrence> occurrences) {
        this.occurrences = occurrences;
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
