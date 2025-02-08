package com.example.demo.services;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerServices {
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO getCustomerById(Long customerId);
    Page<CustomerDTO> getAllCustomer(Pageable pageable);
    CustomerDTO updateCustomer(Long customerId, CustomerDTO updatedCustomer);
    void deleteCustomer(Long customerId);
    public CustomerDTO getCustomerByEmail(String email);
    List<CustomerDTO> searchCustomersByName(String name);
    List<OrderDTO> getAllOrder(Long customerId);
}
