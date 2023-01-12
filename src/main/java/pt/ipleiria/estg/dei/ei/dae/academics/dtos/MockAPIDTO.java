package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MockAPIDTO {

    private String company_name;
    private String nif;

    private Long policy_number;
    private String name;
    private String email;
    private String insured_object;
    private List<String> covers;


    public static MockAPIDTO from(MockAPIDTO mockAPIDTO) {
        return new MockAPIDTO(
                mockAPIDTO.getCompany_name(),
                mockAPIDTO.getNif(),
                mockAPIDTO.getPolicy_number(),
                mockAPIDTO.getName(),
                mockAPIDTO.getEmail(),
                mockAPIDTO.getInsured_object(),
                mockAPIDTO.getCovers()
        );
    }

    public static List<MockAPIDTO> from(List<MockAPIDTO> mockAPIDTOList) {
        return mockAPIDTOList.stream().map(MockAPIDTO::from).collect(Collectors.toList());
    }



}
