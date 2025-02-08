package com.example.demo.dto;

import com.example.demo.entity.Address;
import com.example.demo.entity.Order;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

public class CustomerDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Address address;
    private List<Order> orders;


    CustomerDTO(String firstName) {
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
