package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Company;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Expert;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ExpertBean {

    @PersistenceContext
    private EntityManager em;
    @EJB
    private OccurrenceBean occurrenceBean;

    @Inject
    private Hasher hasher;

    public void create(String username, String password, String name, String email, String companyName) {
        Company company = em.find(Company.class, companyName);
        Expert expert = new Expert(username, hasher.hash(password), name, email, company);
        company.addExpert(expert);
        em.persist(expert);
    }


    public Expert find(String username) {
        return em.find(Expert.class, username);
    }

    public Expert findExpertSafe(String username) {
        Expert expert = em.getReference(Expert.class, username);
        Hibernate.initialize(expert);

        return expert;
    }

    public void addExpertOnOccurrence(String expertUsername, Long OccurrenceCode) {
        Expert expert = findExpertSafe(expertUsername);
        Occurrence occurrence = occurrenceBean.findOccurrenceSafe(OccurrenceCode);

        expert.addOccurrence(occurrence);
        occurrence.addExpert(expert);
    }

    public void removeExpertOnOccurrence(String expertUsername, Long OccurrenceCode) {
        Expert expert = findExpertSafe(expertUsername);
        Occurrence occurrence = occurrenceBean.findOccurrenceSafe(OccurrenceCode);

        occurrence.removeExpert(expert);
        expert.removeOccurrence(occurrence);
    }
}
