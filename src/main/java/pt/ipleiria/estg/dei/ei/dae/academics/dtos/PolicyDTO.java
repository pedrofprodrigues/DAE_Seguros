package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class PolicyDTO implements Serializable {

    private Long code;

    private String name;

    public PolicyDTO() {
    }

    public PolicyDTO(Long code, String name) {
        this.code = code;
        this.name = name;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static PolicyDTO from(Policy policy) {
        return new PolicyDTO(
                policy.getCode(),
                policy.getName()
        );
    }

    public static List<PolicyDTO> from(List<Policy> students) {
        return students.stream().map(PolicyDTO::from).collect(Collectors.toList());
    }
}
