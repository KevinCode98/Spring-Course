package pvin.curso.springboot.springbootjpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjpa.dto.PersonDto;
import pvin.curso.springboot.springbootjpa.entities.Person;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person, Long> {
    @Query("select count(distinct(p.programmingLanguage)) from Person p")
    Long countProgrammingLanguage();

    @Query("select p.name from Person p")
    List<String> findAllNames();

    @Query("select distinct (p.name) from Person p")
    List<String> findAllNamesDistinct();

    @Query("select distinct (p.programmingLanguage) from Person p")
    List<String> findAllProgrammingLanguageDistinct();

//    @Query("select concat(p.name,  ' ', p.lastname) as fullname from Person p where p.id=:id")
    @Query("select upper(p.name ||  ' ' || p.lastname) as fullname from Person p where p.id=:id")
    String getFullNameById(Long id);

    @Query("select lower(p.name) from Person p where p.id=:id")
    String getNameById(Long id);

    @Query("select p from Person p where p.id=:id")
    Optional<Person> findOne(Long id);

    @Query("select p from Person p where p.name=:name")
    List<Optional<Person>> findOneName(String name);

    List<Person> findByProgrammingLanguage(String language);

    @Query("select p from Person p where p.name like %:name% order by p.id asc")
    List<Optional<Person>> findOneLikeName(String name);

    List<Optional<Person>> findByNameContaining(String name);

    List<Person> findByName(String name);

    @Query("select p from Person p where p.programmingLanguage=:language and p.name=:name")
    List<Person> buscarByProgrammingLanguage(String language, String name);

    @Query("select p.name, p.programmingLanguage from Person p where p.programmingLanguage=:language and p.name=:name")
    List<Object[]> obtenerPersonDataByProgrammingLanguageAndName(String name, String language);

    @Query("select p.name, p.programmingLanguage from Person p where p.name=:name")
    List<Object[]> obtenerPersonDataByName(String name);

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p")
    List<Object[]> obtenerPersonaFullData(String name);

    @Query("select p.id, p.name, p.lastname, p.programmingLanguage from Person p where p.id=:id")
    Object obtenerPersonaById(Long id);

    @Query("select p, p.programmingLanguage from Person p")
    List<Object[]> findAllMixPersons();

    @Query("select new Person(p.name, p.lastname) from Person p")
    List<Person> findAllPersonsPersonalized();

    @Query("select new pvin.curso.springboot.springbootjpa.dto.PersonDto(p.name, p.lastname) from Person p")
    List<PersonDto> findAllPersonsPersonalizedWithDto();

    @Query("select p from Person p where p.id between :start and :end")
    List<Person> findAllPersonsPersonalizedBetweenById(Long start, Long end);

    @Query("select p from Person p where p.name between :start and :end")
    List<Person> findAllPersonsPersonalizedBetweenByName(String start, String end);
}