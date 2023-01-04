package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "policies")


@NamedQueries({
        @NamedQuery(
                name = "getAllPolicies",
                query = "SELECT policy FROM Policy policy ORDER BY policy.insuranceCompany"
        )
})

@Data
public class Policy extends Versionable {

    @Id
    private Long code;

    @NotNull
    @ManyToOne
    private Company insuranceCompany;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private InsuredObject insuredObject;

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<Cover> covers;

    @ManyToOne(cascade = CascadeType.REMOVE)
    private Client client;

    @OneToMany(mappedBy = "policy", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Occurrence> occurrences;



    public Policy(Long code, Company insuranceCompany, Client client, InsuredObject insuredObject) {
        this();
        this.code = code;
        this.insuranceCompany = insuranceCompany;
        this.client = client;
        this.insuredObject = insuredObject;
    }
    public Policy() {
        this.occurrences = new ArrayList<>();
        this.covers = new ArrayList<>();
    }


    public void addOccurrence(Occurrence occurrence) {
        if (! this.occurrences.contains(occurrence)) {
            this.occurrences.add(occurrence);
        }
    }

    public void removeOccurrence(Occurrence occurrence) {
        this.occurrences.remove(occurrence);
    }

    public void addCover(Cover cover) {
        if (! this.covers.contains(cover)) {
            this.covers.add(cover);
        }

    }

    public void removeCover(Cover cover) {
        this.covers.remove(cover);
    }

}
