package com.pvin.curso.springboot.webapp.controllers;

import com.pvin.curso.springboot.webapp.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class UserController {
    @GetMapping("/details")
    public String details(Model model) {
        User user = new User("Kevin", "Carrillo");
        user.setEmail("kevincode98@gmail.com");
        model.addAttribute("title", "Hola mundo String desde model");
        model.addAttribute("user", user);

        return "details";
    }

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("title", "lista de usuarios:");
        return "list";
    }

    @ModelAttribute("users")
    public List<User> usersModel() {
        return List.of(
                new User("Kevin", "Carrillo", "kcarrillo@gmail.com"),
                new User("Alan", "Zaragoza"),
                new User("Sergio", "Malek"),
                new User("Edgar", "Hernandez", "ehernandez@gmail.com")
        );
    }
}
