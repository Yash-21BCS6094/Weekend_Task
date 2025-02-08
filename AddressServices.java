package com.example.demo.services;

import com.example.demo.dto.AddressDTO;

public interface AddressServices {
    AddressDTO createAddress(AddressDTO addressDTO);
    AddressDTO updateAddress(Long addressId, AddressDTO addressDTO);
    AddressDTO getAddressById(Long addressId);
    void deleteAddress(Long addressId);
}
