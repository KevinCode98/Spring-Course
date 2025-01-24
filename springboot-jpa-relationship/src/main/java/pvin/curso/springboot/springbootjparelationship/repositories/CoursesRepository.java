package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjparelationship.entities.Course;

public interface CoursesRepository extends CrudRepository<Course, Long> {
}
