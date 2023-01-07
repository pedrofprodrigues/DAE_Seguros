package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import jakarta.persistence.LockModeType;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.Cover;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.EstadosEnums.InsuredObject;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairService;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RepairServiceBean {

    @PersistenceContext
    private EntityManager em;

    @EJB
    private OccurrenceBean occurrenceBean;


    public void create(String insuranceCompany, String client, InsuredObject insuredObject, Long occurrenceId) {
        var occurrence = occurrenceBean.findOrFail(occurrenceId);
        var repairService = new RepairService(insuranceCompany, client, insuredObject);

        occurrence.addRepairService(repairService);
        em.persist(repairService);
    }

    public void update(String insuranceCompany, String client, InsuredObject insuredObject, Long repairServiceId, Long occurrenceId) {
        var repairService = findOrFail(repairServiceId);
        var occurrence = occurrenceBean.findOrFail(occurrenceId);

        repairService.setInsuranceCompany(insuranceCompany);
        repairService.setClient(client);
        repairService.setInsuredObject(insuredObject);

        em.merge(repairService);
    }

    public RepairService findOrFail(Long repairServiceId) {
        var repairService = em.getReference(RepairService.class, repairServiceId);
        Hibernate.initialize(repairService);

        return repairService;
    }

    public RepairService find(Long repairServiceId) {
        return em.find(RepairService.class, repairServiceId);
    }

    public List<RepairService> allAllPolicies(int offset, int limit) {
        return em.createNamedQuery("getAllPolicies", RepairService.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<RepairService> allRepairServiceNotClosed(int offset, int limit) {
        return em.createNamedQuery("getAllRepairServicesNotFinished", RepairService.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public List<RepairService> allRepairServiceByState(int offset, int limit) {
        return em.createNamedQuery("getRepairServiceByState", RepairService.class)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    public Long count() {
        return em.createQuery("SELECT COUNT(*) FROM " + RepairService.class.getSimpleName(), Long.class).getSingleResult();
    }

    public RepairService findPolicySafe(Long code) {
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
        em.remove(findPolicySafe(code));
    }
}
