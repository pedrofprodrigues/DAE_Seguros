package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity


@Table(
        name = "occurrences")

@NamedQueries({
        @NamedQuery(
                name = "getAllOccurrences",
                query = "SELECT s FROM Occurrence s ORDER BY s.policyNumber"
        )
        ,
        @NamedQuery(
                name = "getAllPolicyOccurrences",
                query = "SELECT s FROM Occurrence s WHERE s.policyNumber = : policy"
        )})


@Data
public class Occurrence extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull

    private Long policyNumber;

    @NotNull
    private String description;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private OccurrenceState occurrenceState;

    @ManyToOne
    @JoinColumn(name = "repair_id")
    private RepairService repairService;

    @OneToMany(mappedBy = "occurrence")
    private List<Document> documents;

    @NotNull
    private String expertNif;






    public Occurrence() {

        this.documents = new ArrayList<>();
    }

    public Occurrence(  Long policyNumber, String description, String expertNif, RepairService repairService, OccurrenceState occurrenceState) {
        this();
        this.expertNif = expertNif;
        this.description = description;
        this.occurrenceState = occurrenceState;
        this.repairService = repairService;
        this.policyNumber = policyNumber;
        this.documents = new ArrayList<>();

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
