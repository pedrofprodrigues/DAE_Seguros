package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;


import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAPIDTO {

    private String nif;
    private String name;
    private String email;



    public static UserAPIDTO from(UserAPIBean userAPIDTO) {
        return new UserAPIDTO(
                userAPIDTO.getNif(),
                userAPIDTO.getName(),
                userAPIDTO.getEmail()
        );
    }

    public static List<UserAPIDTO> from(List<UserAPIBean> mockAPIDTOList) {
        return mockAPIDTOList.stream().map(UserAPIDTO::from).collect(Collectors.toList());
    }



}
