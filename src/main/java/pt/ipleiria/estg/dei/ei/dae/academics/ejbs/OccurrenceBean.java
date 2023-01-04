package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OccurrenceBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private PolicyBean policyBean;

    public Occurrence find(Long code) {
        return em.find(Occurrence.class, code);
    }

    public Occurrence findOccurrenceSafe(Long code) {
        Occurrence occurrence = em.getReference(Occurrence.class, code);
        Hibernate.initialize(occurrence);

        return occurrence;
    }

    public void create(Long code, String name, Long policyCode) {
        Policy policy = policyBean.findPolicySafe(policyCode);
        Occurrence occurrence = new Occurrence(code, name, policy);

        em.persist(occurrence);
        policy.addOccurrence(occurrence);
    }

    public List<Occurrence> all(int offset, int limit) {
        return em.createNamedQuery("getAllOccurrences", Occurrence.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long count() {
        return em.createQuery("SELECT COUNT(*) FROM " + Occurrence.class.getSimpleName(), Long.class).getSingleResult();
    }
}
