package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "occurrences", uniqueConstraints = @UniqueConstraint(columnNames = {"id"}))
@NamedQueries({
        @NamedQuery(
                name = "getAllOccurrences",
                query = "SELECT o FROM Occurrence o ORDER BY o.id"
        )
})

@Data
public class Occurrence extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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


    public Occurrence() {
        this.documents = new ArrayList<>();
    }

    public Occurrence(Long code, String description, RepairService repairService, OccurrenceState occurrenceState) {
        this();
        this.description = description;
        this.occurrenceState = occurrenceState;
        this.repairService = repairService;
        this.documents = new ArrayList<>();

    }

    public List<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }


    public void addDocument(Document document) {
        if (! this.documents.contains(document)) {
            this.documents.add(document);
        }
    }

    public void removeDocument(Document document) {
        this.documents.remove(document);
    }

    public void addRepairService(RepairService repairService) {
        if (this.repairService != repairService) {
            this.repairService = repairService;
        }
    }
}
