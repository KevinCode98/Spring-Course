package pvin.curso.springboot.springbootinterseptor.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/app")
public class FooController {
    @GetMapping("/foo")
    public Map<String, String> foo() {
        return Collections.singletonMap("foo", "handler foo del controlador");
    }

    @GetMapping("/bar")
    public Map<String, String> bar() {
        return Collections.singletonMap("bar", "handler bar del controlador");
    }

    @GetMapping("/baz")
    public Map<String, String> baz() {
        return Collections.singletonMap("baz", "handler baz del controlador");
    }
}
