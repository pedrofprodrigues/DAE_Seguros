package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.RepairServiceBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairService;

import java.util.List;
import java.util.stream.Collectors;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairServiceDTO {


    private Long id;

    private String insuranceCompany;

    private String client;


    public static RepairServiceDTO from(RepairService repairService) {
        return new RepairServiceDTO(
                repairService.getId(),
                repairService.getInsuranceCompany(),
                repairService.getClient()
        );
    }

    public static List<RepairServiceDTO> from(List<RepairService> repairServices) {
        return repairServices.stream().map(RepairServiceDTO::from).collect(Collectors.toList());
    }







}
