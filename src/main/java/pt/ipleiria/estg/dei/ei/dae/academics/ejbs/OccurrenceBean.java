package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.OccurrenceState;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairCompany;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
public class OccurrenceBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    PolicyAPIBean policyAPIBean;
    @EJB
    RepairCompanyBean repairCompanyBean;
     @EJB
    UserAPIBean userAPIBean;
    @EJB
    OccurrenceBean occurrenceBean;

    public Occurrence find(Long code) {
        return em.find(Occurrence.class, code);
    }

    public Occurrence findOccurrenceSafe(Long code) {
        Occurrence occurrence = em.getReference(Occurrence.class, code);
        Hibernate.initialize(occurrence);

        return occurrence;
    }



    public Long create( Long policyCode, String description, String expertCode) {

        PolicyAPIBean occurrencePolicy = policyAPIBean.getPolicyMockAPI("?policy_number="+policyCode);
       // RepairCompany occurrenceRepairCompany = repairCompanyBean.findRepairCompanySafe(repairCompanyCode);
        UserAPIBean expertNif = userAPIBean.getUserMockAPI("?nif="+expertCode);

        Occurrence occurrence = new Occurrence(occurrencePolicy.getPolicy_number(),description,expertNif.getNif(), OccurrenceState.opened);

        em.persist(occurrence);
        return occurrence.getId();
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

    public void updateOccurrence(Long occurrenceID, String occurrenceState) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;
        try {
            json = mapper.readTree(occurrenceState);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String status = json.get("occurrenceState").asText();


        Occurrence occurrence = findOccurrenceSafe(occurrenceID);
        occurrence.setOccurrenceState(OccurrenceState.valueOf(status));
        em.merge(occurrence);

    }



    public void updateRepairCompany(Long occurrenceID, String companyName) {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode json = null;
        try {
            json = mapper.readTree(companyName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String newCompanyName = json.get("repairCompany").asText();

        Occurrence occurrence = findOccurrenceSafe(occurrenceID);
        RepairCompany repairCompany = repairCompanyBean.findRepairCompanySafe(newCompanyName);
        occurrence.setRepairCompany(repairCompany);
        em.merge(occurrence);

    }
}




