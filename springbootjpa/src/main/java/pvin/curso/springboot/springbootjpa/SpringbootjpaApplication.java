package pvin.curso.springboot.springbootjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pvin.curso.springboot.springbootjpa.entities.Person;
import pvin.curso.springboot.springbootjpa.repositories.PersonRepository;

import java.util.List;

@SpringBootApplication
public class SpringbootjpaApplication implements CommandLineRunner {
    @Autowired
    private PersonRepository repository;


    public static void main(String[] args) {
        SpringApplication.run(SpringbootjpaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        List<Person> persons = (List<Person>) repository.findAll();
//        List<Person> persons = (List<Person>) repository.findByProgrammingLanguage("Java");
        List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("C++","John");

        persons.forEach(System.out::println);
    }
}