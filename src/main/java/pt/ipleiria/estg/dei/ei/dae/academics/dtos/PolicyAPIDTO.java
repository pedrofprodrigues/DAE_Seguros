package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.PolicyAPIBean;

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


    public static PolicyAPIDTO from(PolicyAPIBean policyAPIBean) {
        return new PolicyAPIDTO(
                policyAPIBean.getCompany_name(),
                policyAPIBean.getNif(),
                policyAPIBean.getPolicy_number(),
                policyAPIBean.getInsured_object(),
                policyAPIBean.getCovers()
        );
    }

    public static List<PolicyAPIDTO> from(List<PolicyAPIBean> policyAPIBeans) {
        return policyAPIBeans.stream().map(PolicyAPIDTO::from).collect(Collectors.toList());
    }



}
