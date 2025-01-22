package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjparelationship.entities.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {
}
