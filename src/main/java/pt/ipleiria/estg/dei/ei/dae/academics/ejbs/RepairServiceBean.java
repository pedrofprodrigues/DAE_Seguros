package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairService;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RepairServiceBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String companyName, String username, InsuredObject insuredObject) {

        RepairService policy = new RepairService( companyName , username, insuredObject);
        em.persist(policy);
    }

    public List<RepairService> all(int offset, int limit) {
        return em.createNamedQuery("getAllrepairServices", RepairService.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long count() {
        return em.createQuery("SELECT COUNT(*) FROM " + RepairService.class.getSimpleName(), Long.class).getSingleResult();
    }

    public RepairService find(Long code) {
        return em.find(RepairService.class, code);
    }

    public RepairService findRepairServiceSafe(Long code) {
        RepairService policy = em.getReference(RepairService.class, code);
        Hibernate.initialize(policy);

        return policy;
    }
/*
    public void addCoverOnPolicy(Long code, Cover cover){
        Policy policy = findPolicySafe(code);
        policy.addCover(cover);

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

     */

    public void remove(Long code) {
        em.remove(findRepairServiceSafe(code));
    }
}
