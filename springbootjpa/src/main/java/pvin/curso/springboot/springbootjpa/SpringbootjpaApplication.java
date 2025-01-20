package pvin.curso.springboot.springbootjpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import pvin.curso.springboot.springbootjpa.dto.PersonDto;
import pvin.curso.springboot.springbootjpa.entities.Person;
import pvin.curso.springboot.springbootjpa.repositories.PersonRepository;

import java.util.ArrayList;
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
        update();
    }

    @Transactional(readOnly = true)
    public void whereIn() {
        System.out.println("---------------- Consulta Where in----------------------");
        List<Long> ids = List.of(1L, 2L, 4L);
        List<Person> personList = repository.getPersonsById(ids);
        personList.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void subQuerys() {
        System.out.println("---------------- Consulta por el nombre mas corto y su largo -----------------------");
        List<Object[]> register = repository.getShorterName();
        register.forEach(reg -> {
            System.out.println(reg[0] + " ==> " + reg[1]);
        });

        System.out.println("---------------- Obtener el ultimo registro ----------------------");
        Optional<Person> person = repository.getLastRegisteredPerson();
        person.ifPresentOrElse(System.out::println, () -> {
            System.out.println("Usuario no existe");
        });


    }

    @Transactional(readOnly = true)
    public void querysFunctionAggregation() {
        System.out.println("---------------- Consulta con el total de registros de la tabla persona  ------------------------");
        Long count = repository.getTotalPersons();
        Long min = repository.getMinId();
        Long max = repository.getMaxId();

        System.out.println("Total Persons: " + count);
        System.out.println("Min Person: " + min);
        System.out.println("Max Person: " + max);


        System.out.println("---------------- Consulta con el nombre y su largo -----------------------");
        List<Object[]> regs = repository.getPersonNameLength();
        regs.forEach(reg -> System.out.println(reg[0] + " ->  " + reg[1]));


        System.out.println("Menor nombre es: " + repository.getMinLengthName());
        System.out.println("Mayor nombre es: " + repository.getMaxLengthName());

        System.out.println("---------------- Consultas resumen de funciones de agregacion min, max, sum, avg, count -----------------------");
        Object[] resumReg = (Object[]) repository.getResumeAggregationFunction();
        System.out.println("min = " + resumReg[0]);
        System.out.println("max = " + resumReg[1]);
        System.out.println("sum = " + resumReg[2]);
        System.out.println("avg = " + resumReg[3]);
        System.out.println("count = " + resumReg[4]);
    }

    @Transactional(readOnly = true)
    public void allNamesQueryDistinct() {
        System.out.println("----------------  All Names  ------------------------");
        List<String> names = repository.findAllNames();
        names.forEach(System.out::println);

        System.out.println("----------------  All Names Distinct ------------------------");
        List<String> namesDistinct = repository.findAllNamesDistinct();
        namesDistinct.forEach(System.out::println);

        long countProgrammingLanguage = repository.countProgrammingLanguage();
        System.out.printf("----------------  All Languages Distinct: %d ------------------------%n", countProgrammingLanguage);
        List<String> languages = repository.findAllProgrammingLanguageDistinct();
        languages.forEach(System.out::println);
    }

    @Transactional(readOnly = true)
    public void mixQuery() {
        List<Object[]> personRegs = repository.findAllMixPersons();
        personRegs.forEach(reg -> System.out.println(reg[0] + " -> " + reg[1]));

        System.out.println("----------------  Listado de Objetos personalizados  ------------------------");
        List<Person> personList = repository.findAllPersonsPersonalized();
        personList.forEach(System.out::println);

        System.out.println("----------------  Listado de Objetos personalizados ------------------------");
        List<PersonDto> personDtoList = repository.findAllPersonsPersonalizedWithDto();
        personDtoList.forEach(p -> System.out.println(p.getName() + " " + p.getLastname()));
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

    @Transactional(readOnly = true)
    public void getPersonaBetween() {
        System.out.println("----------------  Obterner lista de usuarios entre dos ID's ------------------------");
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingresa el Id start: ");
        Long start = scanner.nextLong();

        System.out.print("Ingresa el Id end: ");
        Long end = scanner.nextLong();

        List<Person> personListId = repository.findAllPersonsPersonalizedBetweenById(start, end);
        personListId.forEach(System.out::println);

        System.out.println("----------------  Obterner lista de usuarios entre dos Caracters - Nombre ------------------------");

        System.out.print("Ingresa el caracter start: ");
        String caracterStart = scanner.next();

        System.out.print("Ingresa el caracter end: ");
        String caracterEnd = scanner.next();

        List<Person> personListName = repository.findByNameBetween(caracterStart, caracterEnd);
        personListName.forEach(System.out::println);
        scanner.close();

        System.out.println("----------------  Mostrar todas las personas ------------------------");
        repository.findAllOrdered().forEach(System.out::println);

        System.out.println("----------------  Mostrar todas las descendente por nombre ------------------------");
        repository.findAllByOrderByNameDesc().forEach(System.out::println);
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
        repository.findOne(id).ifPresent(System.out::println);
    }
}