package com.example.demo.dto;

import com.example.demo.entity.Customer;
import com.example.demo.entity.OrderStatus;
import com.example.demo.entity.Product;
import jakarta.validation.constraints.NotNull;

import javax.persistence.*;
import java.util.List;

public class OrderDTO {
    private Long id;
    private String orderNum;
    private OrderStatus status;
    private Customer customer;
    private List<ProductDTO> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
}
