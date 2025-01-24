package pvin.curso.springboot.springbootjparelationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;


@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Table(name = "invoices")
public class Invoice extends BaseEntity {
    private String description;
    private Long total;

    public Invoice(String description, Long total) {
        this.description = description;
        this.total = total;
    }

    @ManyToOne
    @JoinColumn(name = "id_client")
    @ToString.Exclude
    private Client client;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(description, invoice.description) && Objects.equals(total, invoice.total);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, total);
    }
}
