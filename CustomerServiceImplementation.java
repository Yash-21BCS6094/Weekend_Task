package com.example.demo.services.implementations;

import com.example.demo.dto.CustomerDTO;
import com.example.demo.dto.OrderDTO;
import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.repository.ProductRepository;
import com.example.demo.services.CustomerServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImplementation implements CustomerServices {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AddressRepository addressRepository;

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO, Customer.class);
        if (customerDTO.getAddress().getId() != null) {
            Address address = addressRepository.findById(customerDTO.getAddress().getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Address not found"));
            customer.setAddress(address);
        }
        Customer savedCustomer = customerRepository.save(customer);

        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public CustomerDTO getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public Page<CustomerDTO> getAllCustomer(Pageable pageable) {
        return customerRepository.findAll(pageable).
                map(customer -> modelMapper.map(customer, CustomerDTO.class));
    }

    @Override
    public CustomerDTO updateCustomer(Long customerId, CustomerDTO updatedCustomer) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find customer")
        );
        customer.setAddress(updatedCustomer.getAddress());
        customer.setFirstName(updatedCustomer.getFirstName());
        customer.setLastName(updatedCustomer.getLastName());
        customer.setEmail(updatedCustomer.getEmail());

        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDTO.class);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerRepository.findById(customerId).
                            orElseThrow(() -> new ResourceNotFoundException("Cannot find customer"));
        customerRepository.delete(customer);
    }

    @Override
    public CustomerDTO getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        return modelMapper.map(customer, CustomerDTO.class);
    }

    @Override
    public List<CustomerDTO> searchCustomersByName(String name) {
        List<Customer> customers = customerRepository.findByFirstNameContainingOrLastNameContaining(name, name);
        List<CustomerDTO> customerDTOS = (List<CustomerDTO>) customers.stream().map((customer) -> modelMapper.map(customer, CustomerDTO.class));
        return customerDTOS;
    }

    @Override
    public List<OrderDTO> getAllOrder(Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(
                () -> new ResourceNotFoundException("Cannot find customer")
        );

        return customer.getOrders().stream().map(order -> modelMapper.map(order, OrderDTO.class)).
                toList();
    }
}
