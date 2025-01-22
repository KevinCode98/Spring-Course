package pvin.curso.springboot.springbootjparelationship.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street;
    private Integer number;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Address(String street, Integer number) {
        this.street = street;
        this.number = number;
    }

    public Address() {
    }

    @Override
    public String toString() {
        return "Address -> {" +
               "id=" + id +
               ", street='" + street + '\'' +
               ", number=" + number +
               '}';
    }
}
