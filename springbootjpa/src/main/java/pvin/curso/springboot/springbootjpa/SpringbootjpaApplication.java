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

    private static int compareObject(Object[] a, Object[] b, int parameter) {
        return ((String) b[parameter]).compareToIgnoreCase((String) a[parameter]);
    }

    @Override
    public void run(String... args) throws Exception {
        List<Person> persons = repository.buscarByProgrammingLanguage("C++", "John");
        List<Object[]> personsValues = repository.obtenerPersonDataByProgrammingLanguageAndName("Daniel", "Python");
        List<Object[]> namesValues = repository.obtenerPersonDataByName("David");
        personsValues.sort((a, b) -> compareObject(a, b, 0));
        namesValues.sort((b, a) -> compareObject(a, b, 1));

        System.out.println("--------------------------------------------------");
        for (Object[] person : personsValues)
            System.out.println(person[0] + " -> " + person[1]);

        System.out.println("--------------------------------------------------");
        for (Object[] person : namesValues)
            System.out.println(person[0] + " es experto en " + person[1]);

        System.out.println("--------------------------------------------------");
        persons.forEach(System.out::println);

        System.out.println("--------------------------------------------------");
    }
}