package pvin.curso.springboot.springbootjparelationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "courses")
public class Course extends BaseEntity {
    private String name;
    private String instructor;

    @ManyToMany
    @ToString.Exclude
    private Set<Student> students;

    public Course() {
        students = new HashSet<>();
    }

    public Course(String name, String instructor) {
        this();
        this.name = name;
        this.instructor = instructor;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return Objects.equals(name, course.name) && Objects.equals(instructor, course.instructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, instructor);
    }
}
