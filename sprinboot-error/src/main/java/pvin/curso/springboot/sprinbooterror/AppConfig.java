package pvin.curso.springboot.sprinbooterror;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pvin.curso.springboot.sprinbooterror.controllers.domain.User;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {

    @Bean
    public List<User> UserServiceImpl() {
        List<User> users = new ArrayList<>();

        users.add(new User(1L, "Kevin", "Carrillo"));
        users.add(new User(2L, "Alan", "Zaragoza"));
        users.add(new User(3L, "Sergio", "Malek"));
        users.add(new User(4L, "Edgar", "Hernandez"));

        return users;
    }
}
