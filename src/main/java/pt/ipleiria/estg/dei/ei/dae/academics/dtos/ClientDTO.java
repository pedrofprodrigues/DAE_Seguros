package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ClientDTO implements Serializable {

    @NotNull
    protected String username;

    @NotNull
    protected String name;

    @NotNull
    protected String email;



    public static ClientDTO from(Client client) {
        return new ClientDTO(
                client.getUsername(),
                client.getName(),
                client.getEmail()
        );
    }

    public static List<ClientDTO> from(List<Client> clients) {
        return clients.stream().map(ClientDTO::from).collect(Collectors.toList());
    }
}
