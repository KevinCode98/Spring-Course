package pvin.curso.springboot.springbootjpa.repositories;

import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjpa.entities.Person;

public interface PersonRepository extends CrudRepository<Person, Long> {
}