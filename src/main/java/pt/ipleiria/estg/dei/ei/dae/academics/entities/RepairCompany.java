package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.Data;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "repairCompanies")


@NamedQueries({
        @NamedQuery(
                name = "getAllCompanies",
                query = "SELECT repairCompany FROM RepairCompany repairCompany ORDER BY repairCompany.companyID"
        )

})

@Data
public class RepairCompany extends Versionable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long companyID;
    @NotNull
    private String repairCompany;
    @NotNull
    private String email;
    @OneToMany(fetch = FetchType.EAGER)
    private List<RepairMan> repairNIFs;

    @NotNull
    @ElementCollection
    @Enumerated(EnumType.ORDINAL)
    private List<InsuredObject> insuredObjects;

    @OneToMany(cascade = CascadeType.REMOVE)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Occurrence> occurrences;




    public RepairCompany(String repairCompany,String email) {
        this();
        this.repairCompany = repairCompany;
        this.email = email;
    }
    public RepairCompany() {
       repairNIFs = new ArrayList<>();
       insuredObjects = new ArrayList<>();
       occurrences = new ArrayList<>();

    }


    public void AddRepair(RepairMan nif) {
        if (! this.repairNIFs.contains(nif)) {
            this.repairNIFs.add(nif);
        }
    }

    public void removeRepair(RepairMan nif) {
        this.repairNIFs.remove(nif);
    }

        public void AddOccurrence(Occurrence occurrence) {
        if (! this.occurrences.contains(occurrence)) {
            this.occurrences.add(occurrence);
        }
    }

    public void removeOccurrence(Occurrence occurrence) {
        this.occurrences.remove(occurrence);
    }

        public void AddInsuredObject(InsuredObject insuredObject) {
        if (! this.insuredObjects.contains(insuredObject)) {
            this.insuredObjects.add(insuredObject);
        }
    }

    public void RemoveInsuredObject(InsuredObject insuredObject) {
        this.insuredObjects.remove(insuredObject);
    }


    public ArrayList<Long> getRepairNIFs(){
        ArrayList<Long>list = new ArrayList<>();
        for (RepairMan repairMan : repairNIFs) {
            list.add(repairMan.getNif());
        }

        return list;

    }



}
