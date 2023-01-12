package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PolicyAPIDTO {

    private String company_name;
    private String nif;
    private Long policy_number;
    private String insured_object;
    private List<String> covers;


    public static PolicyAPIDTO from(PolicyAPIDTO mockAPIDTO) {
        return new PolicyAPIDTO(
                mockAPIDTO.getCompany_name(),
                mockAPIDTO.getNif(),
                mockAPIDTO.getPolicy_number(),
                mockAPIDTO.getInsured_object(),
                mockAPIDTO.getCovers()
        );
    }

    public static List<PolicyAPIDTO> from(List<PolicyAPIDTO> mockAPIDTOList) {
        return mockAPIDTOList.stream().map(PolicyAPIDTO::from).collect(Collectors.toList());
    }



}
