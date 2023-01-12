package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.User;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    public User find(String username) {
        return em.find(User.class, username);
    }
    public void create(String username, String password, String name) {
        User user = new User(username,hasher.hash(password), name);

        em.persist(user);

    }

    public User findUserSafe(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);

        return user;
    }


}
