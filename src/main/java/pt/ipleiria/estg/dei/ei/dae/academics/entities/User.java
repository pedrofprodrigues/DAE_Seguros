package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)


@Data
@AllArgsConstructor
@NoArgsConstructor

public class User extends Versionable {

    @Id
    protected String nif;

    @NotNull
    protected String password;

    @NotNull
    protected String name;

    @NotNull
    protected String type;

    @Email
    @NotNull
    protected String email;

}
