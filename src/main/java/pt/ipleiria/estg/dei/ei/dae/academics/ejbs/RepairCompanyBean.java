package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairCompany;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class RepairCompanyBean {

    @PersistenceContext
    private EntityManager em;

    public void create(String companyName) {

        RepairCompany repairCompany = new RepairCompany( companyName);
        em.persist(repairCompany);
    }

    public List<RepairCompany> all() {
        return em.createNamedQuery("getAllRepairCompanies", RepairCompany.class)
                .getResultList();
    }

    public Long count() {
        return em.createQuery("SELECT COUNT(*) FROM " + RepairCompany.class.getSimpleName(), Long.class).getSingleResult();
    }

    public RepairCompany find(String companyName) {
        return em.find(RepairCompany.class, companyName);
    }

    public RepairCompany findRepairCompanySafe(String companyName) {
        RepairCompany policy = em.getReference(RepairCompany.class, companyName);
        Hibernate.initialize(policy);

        return policy;
    }

    public void remove(String companyName) {
        em.remove(findRepairCompanySafe(companyName));
    }
}
