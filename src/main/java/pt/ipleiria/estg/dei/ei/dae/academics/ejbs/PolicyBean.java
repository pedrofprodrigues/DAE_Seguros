package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Client;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class PolicyBean {

    @PersistenceContext
    private EntityManager em;

    public void create(Long code, String name) {
        var course = new Policy(code, name);
        em.persist(course);
    }

    public List<Policy> all(int offset, int limit) {
        return em.createNamedQuery("getAllPolicies", Policy.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long count() {
        return em.createQuery("SELECT COUNT(*) FROM " + Policy.class.getSimpleName(), Long.class).getSingleResult();
    }

    public Policy find(Long code) {
        return em.find(Policy.class, code);
    }

    public Policy findPolicySafe(Long code) {
        Policy policy = em.getReference(Policy.class, code);
        Hibernate.initialize(policy);

        return policy;
    }

    public void updatePolicy(Long code, String name) {
        Policy policy = findPolicySafe(code);
        policy.setName(name);
        em.merge(policy);
    }

    // if for some reason you need to change the PK
    // Hibernate doesn't allow that modification.
    // The workaround is to delete the old one (be careful with cascade operations)
    // and create a new one.
    public void updatePolicy(Long oldCode, Long newCode, String name) {
        Policy previousPolicy = findPolicySafe(oldCode);

        create(newCode, name + ".tmp"); // unique key on column name
        Policy newPolicy = findPolicySafe(newCode);

        while (previousPolicy.getClients().size() > 0) {
            Client client = previousPolicy.getClients().get(0);
            client.setPolicy(newPolicy);

            previousPolicy.removeClient(client);
            newPolicy.addClient(client);
        }

        while (previousPolicy.getOccurrences().size() > 0) {
            Occurrence occurrence = previousPolicy.getOccurrences().get(0);
            occurrence.setPolicy(newPolicy);

            previousPolicy.removeOccurrence(occurrence);
            newPolicy.addOccurrence(occurrence);
        }

        em.flush();

        em.remove(previousPolicy);
        em.flush();

        newPolicy.setName(name);
        em.merge(newPolicy);
    }

    public void remove(Long code) {
        em.remove(findPolicySafe(code));
    }
}
