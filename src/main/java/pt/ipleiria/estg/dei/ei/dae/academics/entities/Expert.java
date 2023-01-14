package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity

@Table(name = "users")


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Expert extends Versionable {

    @Id
    protected String username;


    @NotNull
    protected String name;

    @NotNull
    protected String email;


}
