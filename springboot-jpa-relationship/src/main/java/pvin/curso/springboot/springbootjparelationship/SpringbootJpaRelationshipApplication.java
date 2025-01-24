package pvin.curso.springboot.springbootjparelationship;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pvin.curso.springboot.springbootjparelationship.entities.*;
import pvin.curso.springboot.springbootjparelationship.repositories.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@SpringBootApplication
public class SpringbootJpaRelationshipApplication implements CommandLineRunner {
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ClientDetailsRepository clientDetailsRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private CoursesRepository coursesRepository;

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
    }

    @Transactional
    public void manyToManyBidirectional() {
        Student firstStudent = new Student("Sergio", "Malek");
        Student secondStudent = new Student("Alan", "Zaragoza");

        Course firstCourse = new Course("Spring Boot 6", "Guru");
        Course secondCourse = new Course("Testing - Spring Boot", "Andres");

        firstStudent.addCourse(firstCourse).addCourse(secondCourse);
        secondStudent.addCourse(secondCourse);

        studentRepository.saveAll(Arrays.asList(firstStudent, secondStudent));
        System.out.println(firstStudent);
        System.out.println(secondStudent);
    }

    @Transactional
    public void manyToManyRemoveFind() {
        Student firstStudent = new Student("Sergio", "Malek");
        Student secondStudent = new Student("Alan", "Zaragoza");
        studentRepository.saveAll(Arrays.asList(firstStudent, secondStudent));
        Course firstCourse = new Course("Spring Boot 6", "Guru");
        Course secondCourse = new Course("Testing - Spring Boot", "Andres");
        coursesRepository.saveAll(Arrays.asList(firstCourse, secondCourse));

        Optional<Student> optFirstStudent = studentRepository.findOneWithCourses(1L);
        Optional<Student> optSecondStudent = studentRepository.findOneWithCourses(2L);

        optFirstStudent.ifPresent(student -> {
            Course courseSpring = coursesRepository.findById(1L).get();
            Course courseTesting = coursesRepository.findById(2L).get();
            student.setCourses(Set.of(courseSpring, courseTesting));

            System.out.println("First student: " + student);
        });

        optSecondStudent.ifPresent(student -> {
            Course courseTesting = coursesRepository.findById(2L).get();
            student.setCourses(Set.of(courseTesting));
            studentRepository.save(student);

            System.out.println("Second student: " + student);
        });


        Optional<Student> optStudent = studentRepository.findOneWithCourses(1L);
        optStudent.ifPresent(student -> {
            Optional<Course> optCourse = coursesRepository.findById(2L);
            optCourse.ifPresent(course -> {
                Course auxCourse = new Course(course.getName(), course.getInstructor());

                student.getCourses().remove(auxCourse);
                studentRepository.save(student);

                System.out.println("Delete course: " + student);
            });
        });
    }

    @Transactional
    public void manyToManyFind() {
        Student firstStudent = new Student("Sergio", "Malek");
        Student secondStudent = new Student("Alan", "Zaragoza");
        studentRepository.saveAll(Arrays.asList(firstStudent, secondStudent));
        Course firstCourse = new Course("Spring Boot 6", "Guru");
        Course secondCourse = new Course("Testing - Spring Boot", "Andres");
        coursesRepository.saveAll(Arrays.asList(firstCourse, secondCourse));


        Optional<Student> optFirstStudent = studentRepository.findById(1L);
        Optional<Student> optSecondStudent = studentRepository.findById(2L);

        optFirstStudent.ifPresent(student -> {
            Course courseSpring = coursesRepository.findById(1L).get();
            Course courseTesting = coursesRepository.findById(2L).get();
            student.setCourses(Set.of(courseSpring, courseTesting));
            studentRepository.save(student);

            System.out.println("First student: " + student);
        });

        optSecondStudent.ifPresent(student -> {
            Course courseTesting = coursesRepository.findById(2L).get();
            student.setCourses(Set.of(courseTesting));
            studentRepository.save(student);

            System.out.println("Second student: " + student);
        });
    }

    @Transactional
    public void manyToMany() {
        Student firstStudent = new Student("Sergio", "Malek");
        Student secondStudent = new Student("Alan", "Zaragoza");

        Course firstCourse = new Course("Spring Boot 6", "Guru");
        Course secondCourse = new Course("Testing - Spring Boot", "Andres");

        firstStudent.setCourses(Set.of(firstCourse, secondCourse));
        secondStudent.setCourses(Set.of(firstCourse));

        studentRepository.saveAll(Arrays.asList(firstStudent, secondStudent));
        System.out.println(firstStudent);
        System.out.println(secondStudent);
    }

    @Transactional
    public void oneToOneFindByIdBidirectionalFindBy() {
        Client client = new Client("Max", "Valencia");
        clientRepository.save(client);

        Optional<Client> optClient = clientRepository.findOne(1L);
        optClient.ifPresent(cli -> {
            ClientDetails clientDetails = new ClientDetails(true, 3500);
            cli.addClientDetails(clientDetails);
            clientRepository.save(cli);

            System.out.println(cli);
        });
    }

    @Transactional
    public void oneToOneFindByIdBidirectional() {
        Client client = new Client("Kevin", "Carrillo");
        ClientDetails clientDetails = new ClientDetails(true, 5000);

        client.setClientDetails(clientDetails);
        clientRepository.save(client);

        System.out.println(client);

        client.removeClientDetails(clientDetails);
        System.out.println(client);
    }

    @Transactional
    public void oneToOneFindById() {
        Client client = new Client("Kevin", "Carrillo");
        clientRepository.save(client);

        ClientDetails clientDetails = new ClientDetails(true, 5000);
        clientDetailsRepository.save(clientDetails);
        Optional<Client> optClient = clientRepository.findOne(1L);
        optClient.ifPresent(cli -> {
            cli.setClientDetails(clientDetails);
            clientRepository.save(cli);

            System.out.println(cli);
        });
    }

    @Transactional
    public void oneToOne() {
        ClientDetails clientDetails = new ClientDetails(true, 5000);
        clientDetailsRepository.save(clientDetails);
        Client client = new Client("Kevin", "Carrillo");
        client.setClientDetails(clientDetails);
        clientRepository.save(client);

        System.out.println(client);
    }

    @Transactional
    public void removeInvoiceBidireccionalFindById() {
        Client cli = new Client("el kakaz", "ekkk");
        clientRepository.save(cli);

        Optional<Client> optionalClient = clientRepository.findOne(1L);

        optionalClient.ifPresent(client -> {

            Invoice invoice1 = new Invoice("compras de la casa", 5000L);
            Invoice invoice2 = new Invoice("compras de oficina", 8000L);

            client.addInvoice(invoice1).addInvoice(invoice2);

            clientRepository.save(client);

            System.out.println(client);
        });

        Optional<Client> optionalClientDb = clientRepository.findOne(1L);

        optionalClientDb.ifPresent(client -> {
            Invoice invoice3 = new Invoice("compras de la casa", 5000L);
            invoice3.setId(1L);

            Optional<Invoice> invoiceOptional = Optional.of(invoice3); // invoiceRepository.findById(2L);
            invoiceOptional.ifPresent(invoice -> {
                client.removeInvoice(invoice);
                clientRepository.save(client);
                System.out.println(client);
            });
        });
    }

    @Transactional
    public void oneToManyInvoiceBidireccionalFindById() {
        Optional<Client> optionalClient = clientRepository.findOne(1L);

        optionalClient.ifPresent(client -> {
            Invoice computadora = new Invoice("Computadora", 3500L);
            Invoice teclado = new Invoice("Teclado", 500L);
            Invoice mouse = new Invoice("Mouse", 200L);

            Address firstAddress = new Address("Robert Schummann", 1234);
            Address secondAddress = new Address("Av. Lazaro Cardenas", 4321);

            Set<Address> addresses = new HashSet<>(Arrays.asList(firstAddress, secondAddress));
            client.setAddresses(addresses);

            client.addInvoice(computadora).addInvoice(teclado).addInvoice(mouse);
            clientRepository.save(client);
            System.out.println(client);
        });
    }

    @Transactional
    public void oneToManyInvoiceBidireccional() {
        Client client = new Client("Max", "Valencia");

        Invoice computadora = new Invoice("Computadora", 3500L);
        Invoice teclado = new Invoice("Teclado", 500L);
        Invoice mouse = new Invoice("Mouse", 200L);

        client.addInvoice(computadora).addInvoice(teclado).addInvoice(mouse);

        clientRepository.save(client);
        System.out.println(client);
    }

    @Transactional
    public void removeAddressFindById() {
        Optional<Client> optionalClient = clientRepository.findById(2L);

        optionalClient.ifPresent(client -> {
            Address firstAddress = new Address("Robert Schummann", 1234);
            Address secondAddress = new Address("Av. Lazaro Cardenas", 4321);

            Set<Address> addresses = new HashSet<>(Arrays.asList(firstAddress, secondAddress));
            client.setAddresses(addresses);
            clientRepository.save(client);

            System.out.println(client);

            // Buscando el cliente para eliminar una direccion
            Optional<Client> optClient = clientRepository.findOneWithAddresses(2L);
            optClient.ifPresent(existingClient -> {
                existingClient.getAddresses().remove(firstAddress);
                clientRepository.save(existingClient);
                System.out.println(existingClient);
            });
        });
    }

    @Transactional
    public void removeAddress() {
        // Creando un usuario
        Client client = new Client("Yadira", "Ruiz");
        Address firstAddress = new Address("Av. Pablo Neruda", 1234);
        Address secondAddress = new Address("Av. Patria", 4321);

        client.getAddresses().add(firstAddress);
        client.getAddresses().add(secondAddress);

        clientRepository.save(client);
        System.out.println(client);

        // Buscando el cliente para eliminar una direccion
        Optional<Client> optionalClient = clientRepository.findById(3L);
        optionalClient.ifPresent((existingClient) -> {
            existingClient.getAddresses().remove(firstAddress);
            clientRepository.save(existingClient);
            System.out.println(existingClient);
        });
    }

    @Transactional
    public void oneToManyFindById() {
        Optional<Client> optionalClient = clientRepository.findById(2L);

        optionalClient.ifPresent(client -> {
            Address firstAddress = new Address("Robert Schummann", 1234);
            Address secondAddress = new Address("Av. Lazaro Cardenas", 4321);

            Set<Address> addresses = new HashSet<>(Arrays.asList(firstAddress, secondAddress));
            client.setAddresses(addresses);
            clientRepository.save(client);

            System.out.println(client);
        });
    }

    @Transactional
    public void oneToMany() {
        Client client = new Client("Yadira", "Ruiz");
        Address firstAddress = new Address("Av. Pablo Neruda", 1234);
        Address secondAddress = new Address("Av. Patria", 4321);

        client.getAddresses().add(firstAddress);
        client.getAddresses().add(secondAddress);

        clientRepository.save(client);

        System.out.println(client);
    }

    @Transactional
    public void manyToOne() {
        Client client = new Client("Max", "Valencia");
        clientRepository.save(client);

        Invoice invoice = new Invoice("Compras de oficina", 2000L);
        invoice.setClient(client);
        Invoice invoiceDB = invoiceRepository.save(invoice);
        System.out.println(invoiceDB);
    }

    @Transactional
    public void manyToOneFindByIdClient() {
        Optional<Client> optionalClient = clientRepository.findById(1L);

        if (optionalClient.isPresent()) {
            Invoice invoice = new Invoice("Compras de oficina", 5000L);
            invoice.setClient(optionalClient.get());
            Invoice invoiceDB = invoiceRepository.save(invoice);
            System.out.println(invoiceDB);
        }
    }
}
