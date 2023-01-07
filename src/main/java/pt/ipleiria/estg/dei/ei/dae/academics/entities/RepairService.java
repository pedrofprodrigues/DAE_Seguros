package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.Cover;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.RepairState;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repairServices")


@NamedQueries({
        @NamedQuery(
                name = "getAllRepairServices",
                query = "SELECT rs FROM RepairService rs ORDER BY rs.id"
        ),

        @NamedQuery(
                name = "getRepairServiceByState",
                query = "SELECT rs FROM RepairService rs WHERE rs.repairState = :repairState ORDER BY rs.id"
        ),

        @NamedQuery(
                name = "getAllRepairServicesNotFinished",
                query = "SELECT rs FROM RepairService rs WHERE rs.repairState Not IN ('rejected', 'closed') ORDER BY rs.id"
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

    @NotNull
    @Enumerated(EnumType.ORDINAL)
    private RepairState repairState;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "repairService")
    private List<Occurrence> occurrences;


    public RepairService(String insuranceCompany, String client, InsuredObject insuredObject) {
        this();
        this.insuranceCompany = insuranceCompany;
        this.client = client;
        this.insuredObject = insuredObject;
        this.repairState = RepairState.waiting;
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
        if (this.occurrences.contains(occurrence)) {
            this.occurrences.remove(occurrence);
        }
    }

    public void setRepairState(RepairState repairState) {
        if (this.repairState == RepairState.closed) {
            return;
        }
        this.repairState = repairState;
    }

    public boolean isClosed() {
        return this.repairState == RepairState.closed;
    }

    public boolean isWaiting() {
        return this.repairState == RepairState.waiting;
    }

    public boolean isRejected() {
        return this.repairState == RepairState.rejected;
    }

}
