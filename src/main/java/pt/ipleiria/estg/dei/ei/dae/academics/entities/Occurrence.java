package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "occurrences",
        uniqueConstraints = @UniqueConstraint(columnNames = { "description", "policy_code" })
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOccurrences",
                query = "SELECT s FROM Occurrence s ORDER BY s.policy.insuranceCompany.name"
        )
})


@Data
public class Occurrence extends Versionable {

    @Id
    private Long code;

    @NotNull
    private String description;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private OccurrenceState occurrenceState;

    @ManyToOne
    @JoinColumn(name = "policy_code")
    private Policy policy;

    @OneToMany(mappedBy = "occurrence")
    private List<Document> documents;


    @ManyToMany
    @JoinTable(
            name = "occurrences_experts",
            joinColumns = @JoinColumn(name = "occurrence_code", referencedColumnName = "code"),
            inverseJoinColumns = @JoinColumn(name = "expert_username", referencedColumnName = "username")
    )
    private List<Expert> experts;

    public Occurrence() {
        this.experts = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public Occurrence(Long code, String description, Policy policy, OccurrenceState occurrenceState) {
        this();
        this.code = code;
        this.description = description;
        this.occurrenceState = occurrenceState;
        this.policy = policy;
        this.documents = new ArrayList<>();
        this.experts = new ArrayList<>();
    }


    public void addExpert(Expert expert) {
        if (! this.experts.contains(expert)) {
            this.experts.add(expert);
        }
    }

    public void removeExpert(Expert expert) {
        this.experts.remove(expert);
    }


    public void addDocument(Document document) {
        if (! this.documents.contains(document)) {
            this.documents.add(document);
        }
    }

    public void removeDocument(Document document) {
        this.documents.remove(document);
    }

}
