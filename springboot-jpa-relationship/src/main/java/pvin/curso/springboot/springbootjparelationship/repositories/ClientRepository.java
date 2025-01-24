package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjparelationship.entities.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Query("select c from Client c left join fetch c.addresses where c.id=:id")
    Optional<Client> findOneWithAddresses(Long id);

    @Query("select c from Client c left join fetch c.invoices where c.id=:id")
    Optional<Client> findOneWithInvoices(Long id);

    @Query("select c from Client c left join fetch c.invoices left join fetch c.addresses left join fetch c.clientDetails where c.id=:id")
    Optional<Client> findOne(Long id);
}
