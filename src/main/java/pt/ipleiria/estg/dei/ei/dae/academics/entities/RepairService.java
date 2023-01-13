package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.Cover;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repairServices")


@NamedQueries({
        @NamedQuery(
                name = "getAllrepairServices",
                query = "SELECT repairService FROM RepairService repairService ORDER BY repairService.id"
        )
})

@Data
public class RepairService extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull

    private String insuranceCompany;

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private InsuredObject insuredObject;

    @NotNull
    private String client;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "repairService")
    private List<Occurrence> occurrences;



    public RepairService(String insuranceCompany, String client, InsuredObject insuredObject) {
        this();
        this.insuranceCompany = insuranceCompany;
        this.client = client;
        this.insuredObject = insuredObject;
    }
    public RepairService() {
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
