package pvin.curso.springboot.springbootjparelationship.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@ToString
@Table(name = "clients_details")
public class ClientDetails extends BaseEntity {
    private boolean premium;
    private Integer points;

    @OneToOne
    @JoinColumn(name = "id_cliente_detalle")
    @ToString.Exclude
    private Client client;

    public ClientDetails(boolean premium, Integer points) {
        this.premium = premium;
        this.points = points;
    }
}
