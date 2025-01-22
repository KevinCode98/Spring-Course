package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjparelationship.entities.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {
}
