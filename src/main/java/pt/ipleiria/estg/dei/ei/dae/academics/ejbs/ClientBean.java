package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;
import pt.ipleiria.estg.dei.ei.dae.academics.exceptions.StudentNotInTheSameSubjectCourseException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Stateless
public class ClientBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private PolicyBean policyBean;

    public void create(String username, String name, String email) {
        Client client = new Client(username, name, email);

        em.persist(client);

    }


    public Client find(String username) {
        return em.find(Client.class, username);
    }

    public Client findClientSafe(String username) {
        Client client = em.getReference(Client.class, username);
        Hibernate.initialize(client);

        return client;
    }

    public List<Client> getAll(int offset, int limit) {
        return em.createNamedQuery("getAllClients", Client.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long count() {
        return em.createQuery("SELECT COUNT(*) FROM " + Client.class.getSimpleName(), Long.class).getSingleResult();
    }
}
