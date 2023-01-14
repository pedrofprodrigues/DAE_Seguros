package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity


@Table(name = "occurrences")

@NamedQueries({
        @NamedQuery(
                name = "getAllPolicyOccurrences",
                query = "SELECT s FROM Occurrence s WHERE s.policyNumber = : policy"
        ),
        @NamedQuery(
                name = "getAllExpertOccurrences",
                query = "SELECT s FROM Occurrence s WHERE s.expertNif = : expertNif"
        ),
        @NamedQuery(
                name = "getAllRepairCompanyOccurrences",
                query = "SELECT s FROM Occurrence s WHERE s.repairCompany.id = : repairID"
        )
})


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
    private RepairCompany repairCompany;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "occurrence")
    private List<Document> documents;

    @NotNull
    private String expertNif;



    public Occurrence() {

        this.documents = new ArrayList<>();
    }

    public Occurrence(Long policyNumber, String description, String expertNif, OccurrenceState occurrenceState) {
        this();
        this.expertNif = expertNif;
        this.description = description;
        this.occurrenceState = occurrenceState;
        this.repairCompany = null;
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


    public ArrayList<Long> getDocumentsId(){
        ArrayList<Long>list = new ArrayList<>();
        for (Document document : documents) {
            list.add(document.getId());
        }
        return list;

    }

}
