package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class Auth implements Serializable {
    @NotBlank
    private String nif;
    @NotBlank
    private String password;

    public Auth() {}

    public String getNif() {
        return nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}