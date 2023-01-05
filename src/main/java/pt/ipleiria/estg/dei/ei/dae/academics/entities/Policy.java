package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.Cover;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

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
    @JoinColumn(name = "client_username")
    private Client client;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "policy")
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
