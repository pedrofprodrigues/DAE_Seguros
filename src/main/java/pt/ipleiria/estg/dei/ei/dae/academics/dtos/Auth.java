package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@NoArgsConstructor
@Data
public class Auth implements Serializable {
    @NotBlank
    private String nif;
    @NotBlank
    private String password;

}