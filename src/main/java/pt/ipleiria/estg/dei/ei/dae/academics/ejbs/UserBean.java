package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Hibernate;
import pt.ipleiria.estg.dei.ei.dae.academics.entities.User;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Stateless
public class UserBean {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Hasher hasher;

    public User find(String nif) {
        return em.find(User.class, nif);
    }

    public User findUserSafe(String nif) {
        var user = em.getReference(User.class, nif);
        Hibernate.initialize(user);

        return user;
    }



    public List<MockAPIBean> getOnMockAPI(String nif, String password) {

        List<MockAPIBean> mockAPIBean;

        try {
            URL url = new URL("https://63b4b69c9f50390584b8113e.mockapi.io/DAE?nif=" + nif );
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responseCode);
            } else {
                StringBuilder informationString = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while (scanner.hasNext()) {
                    informationString.append(scanner.nextLine());
                }
                scanner.close();

                ObjectMapper mapper = new ObjectMapper();
                mockAPIBean = mapper.readValue(informationString.toString(), new TypeReference<List<MockAPIBean>>() {
                });

            }
                canLogin(nif, password);

            return mockAPIBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean canLogin(String nif, String password) {
        var user = find(nif);

        return user != null && user.getPassword().equals(hasher.hash(password));
    }
}
