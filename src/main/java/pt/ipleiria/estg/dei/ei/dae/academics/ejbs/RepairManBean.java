package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairCompany;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.RepairMan;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RepairManBean {

    @EJB
    RepairCompanyBean repairCompanyBean;
    @PersistenceContext
    private EntityManager em;

    public void create(Long nif, Long repairID) {
        RepairCompany repairCompany = repairCompanyBean.find(repairID);

        RepairMan repair = new RepairMan(nif, repairCompany);

        em.persist(repair);


    }

    public RepairMan findRepairSafe(Long nif) {
        var user = em.getReference(RepairMan.class, nif);
        Hibernate.initialize(user);

        return user;
    }


}
