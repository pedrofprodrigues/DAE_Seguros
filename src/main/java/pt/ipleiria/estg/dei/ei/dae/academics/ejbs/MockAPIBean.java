package pt.ipleiria.estg.dei.ei.dae.academics.ejbs;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.net.ssl.HttpsURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Stateless


@JsonIgnoreProperties({"ID"})
public class MockAPIBean {





    private String company_name;
    private String password;
    private Long nif;
    private Long ID;

    private Long policy_number;
    private String name;
    private String email;
    private String insured_object;
    private List<String> covers;




    public List<MockAPIBean> getOnMockAPI(String way) {

        List<MockAPIBean> mockAPIBean;

        try{
            URL url = new URL("https://63a3873e471b38b20611069a.mockapi.io/seguroAPI"+way);
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
                mockAPIBean = mapper.readValue(informationString.toString(), new TypeReference<List<MockAPIBean>>(){});

            }

            return mockAPIBean;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;}










}
