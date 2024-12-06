package com.pvin.curso.springboot.webapp.controllers;

import ch.qos.logback.core.model.Model;
import com.pvin.curso.springboot.webapp.models.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {
    @GetMapping(path = "/details")
    public User details(Model model) {
        User user = new User("Kevin", "Carrillo");
        user.setEmail("kevincode98@gmail.com");
        return user;
    }

    @GetMapping("/list")
    public List<User> listUsers() {
        return List.of(
                new User("Kevin", "Carrillo"),
                new User("Alan", "Zaragoza"),
                new User("Sergio", "Malek")
        );
    }
}
