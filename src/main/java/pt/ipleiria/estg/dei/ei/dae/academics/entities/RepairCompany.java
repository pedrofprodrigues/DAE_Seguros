package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repairCompanies")


@NamedQueries({
        @NamedQuery(
                name = "getAllRepairCompanies",
                query = "SELECT repairCompany FROM RepairCompany repairCompany ORDER BY repairCompany.id"
        )
})

@Data
public class RepairCompany extends Versionable {

    @Id
    @NotNull
    private String repairCompany;

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<InsuredObject> insuredObjects;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "repairCompany")
    private List<Occurrence> occurrences;

    public RepairCompany(String repairCompany) {
        this();
        this.repairCompany = repairCompany;
    }
    public RepairCompany() {
        this.occurrences = new ArrayList<>();
        this.insuredObjects = new ArrayList<>();

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
