package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class ClientDTO implements Serializable {

    @NotNull
    protected String username;

    @NotNull
    protected String name;

    @NotNull
    protected String email;

    @NotNull
    protected Long policyCode;

    protected String courseName;

    public ClientDTO() {}

    public ClientDTO(String username, String name, String email, Long policyCode, String courseName) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.policyCode = policyCode;
        this.courseName = courseName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getPolicyCode() {
        return policyCode;
    }

    public void setPolicyCode(Long policyCode) {
        this.policyCode = policyCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getUsername(),
                client.getName(),
                client.getEmail(),
                client.getPolicy().getCode(),
                client.getPolicy().getName()
        );
    }

    public static List<ClientDTO> from(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }
}
