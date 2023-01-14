package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "repairMan")

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairMan {

    @Id
    private Long nif;
    @ManyToOne
    private RepairCompany repairCompany;


}
