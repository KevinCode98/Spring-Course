package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjparelationship.entities.Student;

public interface StudentRepository extends CrudRepository<Student, Long> {
}
