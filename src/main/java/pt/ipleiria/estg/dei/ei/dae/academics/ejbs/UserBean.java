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

    public User findUserSafe(String username) {
        var user = em.getReference(User.class, username);
        Hibernate.initialize(user);

        return user;
    }

    public boolean canLogin(String passwordAPI, String passwordReceived) {

        // System.out.println("PASSWORDS \n\n\n" + passwordAPI + "\n\n\n" + passwordReceived + "\n\n");
        // System.out.println(hasher.hash(passwordReceived)+"\n\n");

        return passwordAPI.equals(hasher.hash(passwordReceived));
    }
}
