package pvin.curso.springboot.springbootjparelationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
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
}
