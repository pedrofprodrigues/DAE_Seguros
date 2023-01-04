package pt.ipleiria.estg.dei.ei.dae.academics.dtos;


import lombok.Data;

@Data
public class ErrorDTO {

    private String key;

    private String value;

    private String reason;

    public ErrorDTO(String reason) {
        this.reason = reason;
    }

    public ErrorDTO(String key, String value, String reason) {
        this(reason);
        this.key = key;
        this.value = value;
    }

}
