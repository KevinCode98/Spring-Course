package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjparelationship.entities.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {
    @Query("select c from Client c join fetch c.addresses")
    Optional<Client> findOne(Long id);
}
