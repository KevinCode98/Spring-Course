package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjparelationship.entities.ClientDetails;

public interface ClientDetailsRepository extends CrudRepository<ClientDetails, Long> {
}
