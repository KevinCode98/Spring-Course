package pvin.curso.springboot.springbootjparelationship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import pvin.curso.springboot.springbootjparelationship.entities.Address;
import pvin.curso.springboot.springbootjparelationship.entities.Client;
import pvin.curso.springboot.springbootjparelationship.entities.Invoice;
import pvin.curso.springboot.springbootjparelationship.repositories.ClientRepository;
import pvin.curso.springboot.springbootjparelationship.repositories.InvoiceRepository;

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

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJpaRelationshipApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        oneToManyInvoiceBidireccionalFindById();
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
