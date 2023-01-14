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

    public void create(String companyName, String email) {

        RepairCompany repairCompany = new RepairCompany( companyName, email);
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

    public RepairCompany findRepairCompanySafe(Long id) {
        RepairCompany repairCompany = em.getReference(RepairCompany.class, id);
        Hibernate.initialize(repairCompany);

        return repairCompany;
    }

    public void remove(Long id) {
        em.remove(findRepairCompanySafe(id));
    }
}
