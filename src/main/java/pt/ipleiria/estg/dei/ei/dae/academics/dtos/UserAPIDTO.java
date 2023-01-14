package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.ejbs.UserAPIBean;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAPIDTO {
    @NotNull
    private String nif;
    @NotNull
    private String name;
    @NotNull
    private String email;
    @NotNull
    private String role;

    public UserAPIDTO(String nif, String name) {
        this.nif = nif;
        this.name = name;
    }


    public static UserAPIDTO from(UserAPIBean userAPIbean) {
        return new UserAPIDTO(
                userAPIbean.getNif(),
                userAPIbean.getName(),
                userAPIbean.getEmail(),
                userAPIbean.getRole()
        );
    }

    public static List<UserAPIDTO> from(List<UserAPIBean> mockAPIDTOList) {
        return mockAPIDTOList.stream().map(UserAPIDTO::from).collect(Collectors.toList());
    }


    public static UserAPIDTO from2(UserAPIBean userAPIbean) {
        return new UserAPIDTO(
                userAPIbean.getNif(),
                userAPIbean.getName()
        );
    }

    public static List<UserAPIDTO> from2(List<UserAPIBean> mockAPIDTOList) {
        return mockAPIDTOList.stream().map(UserAPIDTO::from2).collect(Collectors.toList());
    }


}
