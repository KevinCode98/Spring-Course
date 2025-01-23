package pvin.curso.springboot.springbootjparelationship.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "invoices")
public class Invoice {
    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;


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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(description, invoice.description) && Objects.equals(total, invoice.total) && Objects.equals(client, invoice.client);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, total, client);
    }
}
