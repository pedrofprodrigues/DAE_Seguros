package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@NoArgsConstructor
@Data

public class EmailDTO implements Serializable {
    private String subject;
    private Map<String, String> headers;
    private String message;
}
