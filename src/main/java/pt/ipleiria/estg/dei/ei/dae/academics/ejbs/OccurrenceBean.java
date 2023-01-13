package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairService;

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
    PolicyAPIBean policyAPIBean;
    @EJB
    RepairServiceBean repairServiceBean;
     @EJB
    UserAPIBean userAPIBean;

    public Occurrence find(Long code) {
        return em.find(Occurrence.class, code);
    }

    public Occurrence findOccurrenceSafe(Long code) {
        Occurrence occurrence = em.getReference(Occurrence.class, code);
        Hibernate.initialize(occurrence);

        return occurrence;
    }



    public void create( Long policyCode, String description, String expertCode,  Long repairServiceCode, OccurrenceState occurrenceState) {

        PolicyAPIBean occurrencePolicy = policyAPIBean.getPolicyMockAPI("?policy_number="+policyCode);
        RepairService occurrenceRepairService = repairServiceBean.findRepairServiceSafe(repairServiceCode);
        UserAPIBean expertNif = userAPIBean.getUserMockAPI("?nif="+expertCode);

        Occurrence occurrence = new Occurrence(occurrencePolicy.getPolicy_number(),description,expertNif.getNif(), occurrenceRepairService , occurrenceState);

        em.persist(occurrence);
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