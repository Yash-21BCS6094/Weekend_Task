package com.example.demo.services.implementations;

import com.example.demo.dto.AddressDTO;
import com.example.demo.entity.Address;
import com.example.demo.entity.Customer;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.AddressRepository;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.services.AddressServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddressServiceImplementation implements AddressServices {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(addressDTO.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found")
        ));
        Address address = modelMapper.map(addressDTO, Address.class);
        address.setCustomer(customer);

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO updateAddress(Long addressId, AddressDTO addressDTO) {
        Optional<Customer> customer = Optional.ofNullable(customerRepository.findById(addressDTO.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Customer not found")
        ));

        Address address = modelMapper.map(addressDTO, Address.class);
        address.setCustomer(customer);

        Address savedAddress = addressRepository.save(address);
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public AddressDTO getAddressById(Long addressId) {
        Address savedAddress = addressRepository.findById(addressId).orElseThrow(
                () -> new ResourceNotFoundException("Address not found")
        );
        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public void deleteAddress(Long addressId) {
        if(!addressRepository.existsById(addressId)){
            throw new ResourceNotFoundException("Address not found");
        }
        addressRepository.deleteById(addressId);
    }
}
