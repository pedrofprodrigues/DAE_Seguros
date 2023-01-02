package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "policies")
@NamedQueries({
        @NamedQuery(
                name = "getAllPolicies",
                query = "SELECT policy FROM Policy policy ORDER BY policy.name"
        )
})
public class Policy extends Versionable {

    @Id
    private Long code;

    @NotNull
    private String name;

    @OneToMany(mappedBy = "policy", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Client> clients;

    @OneToMany(mappedBy = "policy", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Occurrence> occurrences;

    public Policy() {
        this.clients = new ArrayList<>();
        this.occurrences = new ArrayList<>();
    }

    public Policy(Long code, String name) {
        this();
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

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(List<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public void addClient(Client client) {
        if (! this.clients.contains(client)) {
            this.clients.add(client);
        }
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
    }

    public void addOccurrence(Occurrence occurrence) {
        if (! this.occurrences.contains(occurrence)) {
            this.occurrences.add(occurrence);
        }
    }

    public void removeOccurrence(Occurrence occurrence) {
        this.occurrences.remove(occurrence);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Policy && Objects.equals(((Policy) obj).code, this.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, name);
    }
}
