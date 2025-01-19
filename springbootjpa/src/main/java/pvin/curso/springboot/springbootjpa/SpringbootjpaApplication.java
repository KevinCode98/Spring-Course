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
        findOne(22L);

        System.out.println("---------------- Using findOne -> sending a String ------------------------");
        findOne("Daniel");

        System.out.println("---------------- Using findByName -> sending a String ------------------------");
        repository.findByName("Daniel").forEach(System.out::println);

        System.out.println("---------------- Using findOneLikeName -> sending a String ------------------------");
        repository.findOneLikeName("da").forEach(System.out::println);

        System.out.println("---------------- Using findByNameContaining -> sending a String ------------------------");
        repository.findByNameContaining("mi").forEach(System.out::println);

        System.out.println("---------------- Creating new Person ------------------------");
        create();
    }

    public void findOne(String name) {
        repository.findOneName(name).forEach(System.out::println);
    }

    public void create(){
        Person person = repository.save(new Person(null, "Kevin", "Carrillo", "Java"));
        System.out.println(person);
    }

    public void findOne(Long id) {
//        Optional<Person> optionalPerson = repository.findById(id);
//
//        if(optionalPerson.isPresent())
//            System.out.println(optionalPerson.get());
//        else
//            System.out.printf("La persona con el ID: %d no se encuentra en el sistema. :c%n", id);

        repository.findOne(id).ifPresent(System.out::println);
    }
}