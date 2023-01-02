package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@NamedQueries({
        @NamedQuery(
                name = "getAllClients",
                query = "SELECT s FROM Client s ORDER BY s.name"
        ),

        // SOLUTION 1
        @NamedQuery(
                name = "getAllOccurrencesUnrolled",
                query = "SELECT s FROM Occurrence s WHERE s.code NOT IN" +
                        "    (SELECT ss.subjectCode FROM SubjectStudent ss WHERE ss.studentUsername = :username) " +
                        "AND s.policy.code = :policyCode"
        )
})
public class Client extends User {

    @NotNull
    @ManyToOne
    @JoinColumn(name = "policy_code")
    @NotNull
    private Policy policy;

    @ManyToMany(mappedBy = "clients", fetch = FetchType.LAZY)
    private List<Occurrence> occurrences;

    @OneToMany(mappedBy = "client")
    private List<Document> documents;

    public Client() {
        this.occurrences = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public Client(String username, String password, String name, String email, Policy policy) {
        super(username, password, name, email);
        this.policy = policy;
        this.occurrences = new ArrayList<>();
        this.documents = new ArrayList<>();
    }
    public Client(String username, String name, String email, Policy policy) {
        super(username, name, email);
        this.policy = policy;
        this.occurrences = new ArrayList<>();
        this.documents = new ArrayList<>();
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public List<Occurrence> getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(List<Occurrence> occurrences) {
        this.occurrences = occurrences;
    }

    public List<Document> getDocuments() {
        return this.documents;
    }

    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }

    public void addOccurrence(Occurrence occurrence) {
        if (! this.occurrences.contains(occurrence)) {
            this.occurrences.add(occurrence);
        }
    }

    public void removeOccurrence(Occurrence occurrence) {
        this.occurrences.remove(occurrence);
    }

    public void addDocument(Document document) {
        if (! this.documents.contains(document)) {
            this.documents.add(document);
        }
    }

    public void removeDocument(Document document) {
        this.documents.remove(document);
    }
}
