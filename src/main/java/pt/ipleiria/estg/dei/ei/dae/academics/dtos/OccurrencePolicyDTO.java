package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.PolicyAPIBean;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;

import javax.validation.constraints.NotNull;
import java.util.List;


@NoArgsConstructor
@Data
@AllArgsConstructor
public class OccurrencePolicyDTO {

    @NotNull
    private Long policyNumber;
    @NotNull
    private String description;

    private OccurrenceState occurrenceState;
    @NotNull
    private String repairServiceName;
    @NotNull
    private String expertNif;
    @NotNull
    private Long policy_number;

    private String insured_object;
    private List<String> covers;

    public static OccurrencePolicyDTO from(Occurrence occurrence, PolicyAPIBean policyAPIBean) {
        return new OccurrencePolicyDTO(
                occurrence.getId(),
                occurrence.getDescription(),
                occurrence.getOccurrenceState(),
                occurrence.getRepairService().getInsuranceCompany(),
                occurrence.getExpertNif(),
                policyAPIBean.getPolicy_number(),
                policyAPIBean.getInsured_object(),
                policyAPIBean.getCovers()
        );
    }
}
