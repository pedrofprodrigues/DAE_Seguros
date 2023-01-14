package pt.ipleiria.estg.dei.ei.dae.academics.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ErrorDTO {

    private String key;

    private String value;

    private String reason;

    public ErrorDTO(String reason) {
        this.reason = reason;
    }

}
