package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.RepairState;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairService;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class RepairServiceDTO implements Serializable {

    private Long id;
    private String insuranceCompany;

    private RepairState repairState;

    private String client;

    public RepairServiceDTO() {
    }

    public RepairServiceDTO(Long id, String insuranceCompany, RepairState repairState, String client) {
        this.id = id;
        this.insuranceCompany = insuranceCompany;
        this.repairState = repairState;
        this.client = client;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInsuranceCompany() {
        return insuranceCompany;
    }

    public void setInsuranceCompany(String insuranceCompany) {
        this.insuranceCompany = insuranceCompany;
    }

    public RepairState getRepairState() {
        return repairState;
    }

    public void setRepairState(RepairState repairState) {
        this.repairState = repairState;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public static RepairServiceDTO from(RepairService repairService) {
        return new RepairServiceDTO(
                repairService.getId(),
                repairService.getInsuranceCompany(),
                repairService.getRepairState(),
                repairService.getClient()
        );
    }

    public static List<RepairServiceDTO> from(List<RepairService> repairServices) {
        return repairServices.stream().map(RepairServiceDTO::from).collect(Collectors.toList());
    }

}
