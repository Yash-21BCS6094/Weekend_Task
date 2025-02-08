package com.example.demo.entity;


import jakarta.validation.constraints.NotNull;
import javax.persistence.*;
import java.util.Optional;

@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String street;
    @NotNull
    private String city;
    @NotNull
    private Long customerId;

    @OneToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Optional<Customer> customer;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Optional<Customer> getCustomer() {
        return customer;
    }

    public void setCustomer(Optional<Customer> customer) {
        this.customer = customer;
    }
}
