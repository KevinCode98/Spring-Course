package pvin.curso.springboot.springbootjparelationship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import pvin.curso.springboot.springbootjparelationship.entities.*;
import pvin.curso.springboot.springbootjparelationship.repositories.ClientDetailsRepository;
import pvin.curso.springboot.springbootjparelationship.repositories.ClientRepository;
import pvin.curso.springboot.springbootjparelationship.repositories.InvoiceRepository;
import pvin.curso.springboot.springbootjparelationship.repositories.StudentRepository;

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

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        manyToMany();
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
