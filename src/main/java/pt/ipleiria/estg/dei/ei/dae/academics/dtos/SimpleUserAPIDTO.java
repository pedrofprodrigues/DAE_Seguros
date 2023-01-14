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
public class SimpleUserAPIDTO {
    @NotNull
    private String nif;
    @NotNull
    private String name;


    public static SimpleUserAPIDTO from(UserAPIBean userAPIbean) {
        return new SimpleUserAPIDTO(
                userAPIbean.getNif(),
                userAPIbean.getName()

        );
    }

    public static List<SimpleUserAPIDTO> from(List<UserAPIBean> mockAPIDTOList) {
        return mockAPIDTOList.stream().map(SimpleUserAPIDTO::from).collect(Collectors.toList());
    }

}
