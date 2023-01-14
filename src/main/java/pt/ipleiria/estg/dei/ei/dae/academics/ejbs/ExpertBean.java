package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.Expert;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class ExpertBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    public Expert find(String username) {
        return em.find(Expert.class, username);
    }

    public void create(String username, String password, String name) {
        Expert expert = new Expert(username, hasher.hash(password), name);

        em.persist(expert);

    }

    public Expert findUserSafe(String username) {
        var user = em.getReference(Expert.class, username);
        Hibernate.initialize(user);

        return user;
    }


}
