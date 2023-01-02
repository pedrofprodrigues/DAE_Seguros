package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.StudentNotInTheSameSubjectCourseException;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Stateless
public class ClientBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private PolicyBean policyBean;

    @EJB
    private OccurrenceBean occurrenceBean;

    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String email, Long courseCode) {
        Policy policy = policyBean.findPolicySafe(courseCode);
        Client client = new Client(username, hasher.hash(password), name, email, policy);

        policy.addClient(client);
        em.persist(client);
    }

    // the rest of the code ...

    public void update(String username, String password, String name, String email, Long courseCode) {
        var student = findOrFail(username);

        em.lock(student, LockModeType.OPTIMISTIC);

        student.setPassword(password);
        student.setName(name);
        student.setEmail(email);

        if (!Objects.equals(student.getPolicy().getCode(), courseCode)) {
            student.setPolicy(policyBean.findPolicySafe(courseCode));
        }
    }

    public Client find(String username) {
        return em.find(Client.class, username);
    }

    public Client findOrFail(String username) {
        var student = em.getReference(Client.class, username);
        Hibernate.initialize(student);

        return student;
    }

    public List<Client> getAll(int offset, int limit) {
        return em.createNamedQuery("getAllStudents", Client.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long count() {
        return em.createQuery("SELECT COUNT(*) FROM " + Client.class.getSimpleName(), Long.class).getSingleResult();
    }

    public void enroll(String studentUsername, Long subjectCode) throws StudentNotInTheSameSubjectCourseException {
        var student = findOrFail(studentUsername);
        var subject = occurrenceBean.findOccurrenceSafe(subjectCode);

        if (! student.getPolicy().equals(subject.getPolicy())) {
            throw new StudentNotInTheSameSubjectCourseException(studentUsername, subjectCode);
        }

        student.addOccurrence(subject);
        subject.addStudent(student);
    }

    public void unroll(String studentUsername, Long subjectCode) throws StudentNotInTheSameSubjectCourseException {

        var student = findOrFail(studentUsername);
        var subject = occurrenceBean.findOccurrenceSafe(subjectCode);

        if (! student.getPolicy().equals(subject.getPolicy())) {
            throw new StudentNotInTheSameSubjectCourseException(studentUsername, subjectCode);
        }

        subject.removeClient(student);
        student.removeOccurrence(subject);
    }

    public List<Occurrence> enrolled(String username) {
        var subjects = findOrFail(username).getOccurrences();
        Hibernate.initialize(subjects);

        return subjects;
    }

    public List<Occurrence> unrolled(String username) {
        var student = findOrFail(username);

        return em.createNamedQuery("getAllSubjectsUnrolled", Occurrence.class)
            .setParameter("username", username)
            .setParameter("courseCode", student.getPolicy().getCode())
            .getResultList();
    }
}
