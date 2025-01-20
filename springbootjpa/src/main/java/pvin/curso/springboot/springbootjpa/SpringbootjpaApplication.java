package pvin.curso.springboot.springbootjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import pvin.curso.springboot.springbootjpa.entities.Person;
import pvin.curso.springboot.springbootjpa.repositories.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

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

        System.out.println("---------------- GetName from Person ------------------------");
        personalizedQuery();


    }

    @Transactional(readOnly = true)
    public void personalizedQuery() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Ingresa el Id del usuario para obtener su nombre: ");
        Long id = scanner.nextLong();

        if (repository.findById(id).isPresent()) {
            System.out.println(repository.getNameById(id));
            System.out.println(repository.getFullNameById(id));
            Object personObj[] = (Object[]) repository.obtenerPersonaById(id);
            System.out.println(personObj[0] + ".- " + personObj[1] + " " + personObj[2] + " ==> " + personObj[3]);
        } else {
            System.out.println("El usuario no existe");
        }

        scanner.close();
    }

    @Transactional
    public void create() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nombre: ");
        String name = scanner.nextLine();
        System.out.print("Apellido: ");
        String lastname = scanner.nextLine();
        System.out.print("Lenguaje favorito: ");
        String language = scanner.nextLine();
        scanner.close();

        Person person = repository.save(new Person(null, name, lastname, language));
        System.out.println(person);
        repository.findOneName(name).forEach(System.out::println);
        scanner.close();
    }

    @Transactional
    public void update() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Id del usuario que se modificara: ");
        Long id = scanner.nextLong();

        Optional<Person> person = repository.findById(id);

        if (person.isPresent()) {
            System.out.println(person.get());
            System.out.print("Ingrese el nuevo lenguaje de programacion: ");
            String language = scanner.next();
            person.get().setProgrammingLanguage(language);
            Person upadatePerson = repository.save(person.get());

            System.out.println(upadatePerson);
        } else {
            System.out.println("El usuario no existe");
        }
    }

    @Transactional
    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Id del usuario que se quiere eliminar: ");
        Long id = scanner.nextLong();

        Optional<Person> person = repository.findById(id);
        if (person.isPresent()) {
            repository.deleteById(id);
            System.out.println("Se ha eliminado : " + person.get());
        } else {
            System.out.println("El usuario no existe");
        }

        scanner.close();
    }


    @Transactional
    public void delete2() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Id del usuario que quiere eliminar: ");
        Long id = scanner.nextLong();

        Optional<Person> person = repository.findById(id);
        person.ifPresentOrElse(p -> {
                    repository.delete(p);
                    System.out.println("Se ha eliminado : " + p);
                },
                () -> System.out.println("El usuario no existe")
        );
        scanner.close();
    }

    @Transactional(readOnly = true)
    public void findOne(String name) {
        repository.findOneName(name).forEach(System.out::println);
    }

    @Transactional(readOnly = true)
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