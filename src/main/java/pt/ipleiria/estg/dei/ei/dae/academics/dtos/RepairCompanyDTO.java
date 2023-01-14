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

    private Long id;
    @NotNull
    private String repairCompany;

    @NotNull
    private String email;

    @NotNull
    private List<Long> repairNifs;





    public static RepairCompanyDTO from(RepairCompany repairCompany) {
        return new RepairCompanyDTO(
                repairCompany.getCompanyID(),
                repairCompany.getRepairCompany(),
                repairCompany.getEmail(),
                repairCompany.getRepairNIFs()
        );
    }

    public static List<RepairCompanyDTO> from(List<RepairCompany> repairCompanies) {
        return repairCompanies.stream().map(RepairCompanyDTO::from).collect(Collectors.toList());
    }







}
