package pvin.curso.springboot.springbootjpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjpa.entities.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByProgrammingLanguage(String language);

    @Query("select p from Person p where p.programmingLanguage=:language and p.name=:name")
    List<Person> buscarByProgrammingLanguage(String language, String name);
}