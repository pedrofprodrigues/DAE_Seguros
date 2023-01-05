package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class Repair implements Serializable {

    @Id
    private Long code;

    @NotNull
    private String description;

    @NotNull
    private String status;

    @NotNull
    private String type;
    private String date;
    private String time;
    private String clientNumber;

    public Repair() {
    }

    public Repair(Long code, String description, String status, String type, String date, String time, String clientNumber) {
        this.code = code;
        this.description = description;
        this.status = status;
        this.type = type;
        this.date = date;
        this.time = time;
        this.clientNumber = clientNumber;
    }

    public Long getCode() {
        return code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClient() {
        return clientNumber;
    }

    public void setClient(String clientNumber) {
        this.clientNumber = clientNumber;
    }
}
