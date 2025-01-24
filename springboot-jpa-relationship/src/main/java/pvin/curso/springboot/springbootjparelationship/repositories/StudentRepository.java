package pvin.curso.springboot.springbootjparelationship.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pvin.curso.springboot.springbootjparelationship.entities.Student;

import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.courses WHERE s.id=:id")
    Optional<Student> findOneWithCourses(Long id);
}
