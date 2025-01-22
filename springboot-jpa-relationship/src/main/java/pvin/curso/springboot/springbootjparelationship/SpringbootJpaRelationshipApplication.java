package pvin.curso.springboot.springbootjparelationship;

import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pvin.curso.springboot.springbootjparelationship.entities.Client;
import pvin.curso.springboot.springbootjparelationship.entities.Invoice;
import pvin.curso.springboot.springbootjparelationship.repositories.ClientRepository;
import pvin.curso.springboot.springbootjparelationship.repositories.InvoiceRepository;

import java.util.Optional;

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
        manyToOneFindByIdClient();
    }

    public void manyToOne() {
        Client client = new Client("Max", "Valencia");
        clientRepository.save(client);

        Invoice invoice = new Invoice("Compras de oficina", 2000L);
        invoice.setClient(client);
        Invoice invoiceDB = invoiceRepository.save(invoice);
        System.out.println(invoiceDB);
    }

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
