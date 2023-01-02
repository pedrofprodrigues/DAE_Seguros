package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "occurrences",
        uniqueConstraints = @UniqueConstraint(columnNames = { "name", "policy_code", "scholar_year" })
)
@NamedQueries({
        @NamedQuery(
                name = "getAllOccurrences",
                query = "SELECT s FROM Occurrence s ORDER BY s.policy.name, s.scholarYear DESC, s.courseYear, s.name"
        )
})
public class Occurrence extends Versionable {

    @Id
    private Long code;

    private String name;

    @Column(name = "course_year")
    private String courseYear;

    @Column(name = "scholar_year")
    private String scholarYear;

    @ManyToOne
    @JoinColumn(name = "policy_code")
    private Policy policy;

    @ManyToMany
    @JoinTable(
            name = "occurrences_clients",
            joinColumns = @JoinColumn(name = "occurrence_code", referencedColumnName = "code"),
            inverseJoinColumns = @JoinColumn(name = "client_username", referencedColumnName = "username")
    )
    private List<Client> clients;

    @ManyToMany
    @JoinTable(
            name = "occurrences_experts",
            joinColumns = @JoinColumn(name = "occurrence_code", referencedColumnName = "code"),
            inverseJoinColumns = @JoinColumn(name = "expert_username", referencedColumnName = "username")
    )
    private List<Expert> experts;

    public Occurrence() {
        this.clients = new ArrayList<>();
        this.experts = new ArrayList<>();
    }

    public Occurrence(Long code, String name, String courseYear, String scholarYear, Policy policy) {
        this();
        this.code = code;
        this.name = name;
        this.courseYear = courseYear;
        this.scholarYear = scholarYear;
        this.policy = policy;
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

    public String getCourseYear() {
        return courseYear;
    }

    public void setCourseYear(String courseYear) {
        this.courseYear = courseYear;
    }

    public String getScholarYear() {
        return scholarYear;
    }

    public void setScholarYear(String scholarYear) {
        this.scholarYear = scholarYear;
    }

    public Policy getPolicy() {
        return policy;
    }

    public void setPolicy(Policy policy) {
        this.policy = policy;
    }

    public List<Client> getStudents() {
        return clients;
    }

    public void setStudents(List<Client> clients) {
        this.clients = clients;
    }

    public List<Expert> getTeachers() {
        return experts;
    }

    public void setTeachers(List<Expert> experts) {
        this.experts = experts;
    }

    public void addStudent(Client client) {
        if (! this.clients.contains(client)) {
            this.clients.add(client);
        }
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
    }

    public void addExpert(Expert expert) {
        if (! this.experts.contains(expert)) {
            this.experts.add(expert);
        }
    }

    public void removeExpert(Expert expert) {
        this.experts.remove(expert);
    }
}
