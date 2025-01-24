package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pvin.curso.springboot.springbootjparelationship.entities.Course;

import java.util.Optional;

public interface CoursesRepository extends CrudRepository<Course, Long> {
    @Query("SELECT c FROM Course c LEFT JOIN FETCH c.students WHERE c.id=:id")
    Optional<Course> findOneWithStudents(Long id);
}
