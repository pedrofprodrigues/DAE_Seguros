package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pt.ipleiria.estg.dei.ei.dae.academics.security.Hasher;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Stateless




@JsonIgnoreProperties({"ID"})
public class UserAPIBean {

    @Inject
    private Hasher hasher;

    private String password;
    private String nif;
    private Long ID;

    private String name;
    private String email;

    private String role;



    public UserAPIBean getUserMockAPI(String way) {



        UserAPIBean[] list;

        try{
            URL url = new URL("https://63a3873e471b38b20611069a.mockapi.io/users"+way);
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
                list = mapper.readValue(informationString.toString(), UserAPIBean[].class);


                System.out.println("\n\n\n "+list+"\n\n\n");




                return list[0];





            }



        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;}


    public String MockAPItoJSON(UserAPIBean userAPIBean)  {

        ObjectMapper mapper = new ObjectMapper();
        String json = null;
        try {
            json = mapper.writeValueAsString(userAPIBean);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return json;
    }


    public boolean canLogin(String passwordAPI, String passwordReceived) {

        System.out.println("PASSWORDS \n\n\n" + passwordAPI + "\n\n\n" + passwordReceived + "\n\n");
        System.out.println(hasher.hash(passwordReceived)+"\n\n");

        return passwordAPI.equals(hasher.hash(passwordReceived));
    }



}
