package com.example.demo.controllers;

import com.example.demo.dto.AddressDTO;
import com.example.demo.services.AddressServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/addresses")
public class AddressController {

    @Autowired
    private AddressServices addressServices;

    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressServices.createAddress(addressDTO));
    }

    @PutMapping("/{addressId}")
    public ResponseEntity<AddressDTO> updateAddress(@PathVariable Long addressId,
                                                    @RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(addressServices.updateAddress(addressId, addressDTO));
    }

    @GetMapping("/{addressId}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable Long addressId) {
        return ResponseEntity.ok(addressServices.getAddressById(addressId));
    }

    @DeleteMapping("/{addressId}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long addressId) {
        addressServices.deleteAddress(addressId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/customers/{customerId}")
    public ResponseEntity<AddressDTO> getAddressByCustomerId(@PathVariable Long customerId) {
        return ResponseEntity.ok(addressServices.getAddressById(customerId));
    }
}

