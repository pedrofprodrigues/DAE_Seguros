package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;


import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Company;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Expert;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Occurrence;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Policy;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class CompanyBean {
    @PersistenceContext
    private EntityManager em;

    @EJB
    private PolicyBean policyBean;


    public void create(String name, String email) {
        Company company = new Company(name);
        em.persist(company);
    }


    public Company find(Long id) {
        return em.find(Company.class, id);
    }

    public Company findCompanySafe(String name) {
        Company company = em.getReference(Company.class, name);
        Hibernate.initialize(company);

        return company;
    }

    public void addPolicyOnCompany(String companyName, Long policyCode) {
        Company company = findCompanySafe(companyName);
        Policy policy = policyBean.findPolicySafe(policyCode);

        company.addPolicy(policy);

    }


}
