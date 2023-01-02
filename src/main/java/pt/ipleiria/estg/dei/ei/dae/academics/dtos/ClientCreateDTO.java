package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import javax.validation.constraints.NotNull;

public class ClientCreateDTO extends ClientDTO {

    @NotNull
    private String password;

    public ClientCreateDTO() {}

    public ClientCreateDTO(String username, String password, String name, String email, Long policyCode, String courseName) {
        super(username, name, email, policyCode, courseName);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
