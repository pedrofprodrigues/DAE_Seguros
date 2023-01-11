package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;


import javax.ejb.EJB;
import org.hibernate.Hibernate;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairService;

@Stateless
public class OccurrenceBean {

    @PersistenceContext
    private EntityManager em;

    public void create(Long code, String description, RepairService repairService, OccurrenceState occurrenceState) {
        var occurrence = new Occurrence(code, description, repairService, occurrenceState);
        em.persist(occurrence);
    }

    public Occurrence findOrFail(Long occurrenceId) {
        var occurrence = em.getReference(Occurrence.class, occurrenceId);
        Hibernate.initialize(occurrence);
        return occurrence;
    }

    public Occurrence find (Long occurrenceId) {
        return em.find(Occurrence.class, occurrenceId);
    }


}
