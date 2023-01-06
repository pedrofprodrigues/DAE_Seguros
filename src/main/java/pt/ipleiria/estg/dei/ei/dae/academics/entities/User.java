package pt.ipleiria.estg.dei.ei.dae.academics.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)


@Data
@AllArgsConstructor
@NoArgsConstructor

public class User extends Versionable {

    @Id
    protected String username;


    @NotNull
    protected String name;

    @Email
    @NotNull
    protected String email;

}
