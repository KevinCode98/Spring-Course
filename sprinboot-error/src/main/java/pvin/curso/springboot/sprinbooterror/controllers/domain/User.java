package pvin.curso.springboot.sprinbooterror.controllers.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long id;
    private String name;
    private String lastaname;
    private Role role;

    public User(Long id, String name, String lastaname) {
        this.id = id;
        this.name = name;
        this.lastaname = lastaname;
    }
}
