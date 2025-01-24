package pvin.curso.springboot.springbootjparelationship.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    @ToString.Exclude
    protected Long id;

    public boolean isNew() {
        return this.id == null;
    }

    public BaseEntity() {
    }

    public BaseEntity(Long id) {
        this.id = id;
    }
}
