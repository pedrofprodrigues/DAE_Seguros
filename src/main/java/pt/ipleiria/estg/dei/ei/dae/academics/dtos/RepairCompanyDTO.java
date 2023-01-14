package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairCompany;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairCompanyDTO {


    @NotNull
    private String repairCompany;



    public static RepairCompanyDTO from(RepairCompany repairCompany) {
        return new RepairCompanyDTO(
                repairCompany.getRepairCompany()
        );
    }

    public static List<RepairCompanyDTO> from(List<RepairCompany> repairCompanies) {
        return repairCompanies.stream().map(RepairCompanyDTO::from).collect(Collectors.toList());
    }







}
