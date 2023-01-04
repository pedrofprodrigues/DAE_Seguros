package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.InsuredObject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PolicyDTO implements Serializable {

    private Long code;

    private String companyName;
    private String username;

    private InsuredObject insuredObject;

    public static PolicyDTO from(Policy policy) {
        return new PolicyDTO(
                policy.getCode(),
                policy.getInsuranceCompany().getName(),
                policy.getClient().getUsername(),
                policy.getInsuredObject()
        );
    }

    public static List<PolicyDTO> from(List<Policy> students) {
        return students.stream().map(PolicyDTO::from).collect(Collectors.toList());
    }
}
