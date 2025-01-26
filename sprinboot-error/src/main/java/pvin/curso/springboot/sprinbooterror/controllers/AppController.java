package pvin.curso.springboot.sprinbooterror.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pvin.curso.springboot.sprinbooterror.controllers.domain.User;
import pvin.curso.springboot.sprinbooterror.services.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/app")
public class AppController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String index() {
//        int value = 100 / 0;
        int value = Integer.parseInt("20x");
        System.out.println("value = " + value);

        return "0k 200";
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<User> show(@PathVariable(name = "id") Long id) {
        Optional<User> user = userService.findById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
