package pvin.curso.springboot.springbootjpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjpa.entities.Person;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByProgrammingLanguage(String language);

    @Query("select p from Person p where p.programmingLanguage=:language and p.name=:name")
    List<Person> buscarByProgrammingLanguage(String language, String name);

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=:language and p.name=:name")
    List<Object[]> obtenerPersonDataByProgrammingLanguageAndName(String name, String language);

    @Query("select p.name, p.programmingLanguage from Person p where p.name=:name")
    List<Object[]> obtenerPersonDataByName(String name);
}