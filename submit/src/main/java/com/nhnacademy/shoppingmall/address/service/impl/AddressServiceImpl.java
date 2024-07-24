package com.nhnacademy.shoppingmall.address.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.exception.AddressNotFoundException;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;

import java.util.List;
import java.util.Optional;

public class AddressServiceImpl implements AddressService {

    private AddressRepository addressRepository;

    public AddressServiceImpl(AddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Override
    public Address getAddress(int addressId) {
        return addressRepository.findByAddressId(addressId).get();
    }

    @Override
    public List<Address> getAddressesByUserId(String userId) {
        return addressRepository.findByUserId(userId);
    }

    @Override
    public void saveAddress(Address address) {
        addressRepository.save(address);
    }

    @Override
    public void deleteAddressByAddressId(int addressId) {
        if(addressRepository.countByAddressId(addressId) <= 0) {
            throw new AddressNotFoundException("Address not found");
        }

        addressRepository.deleteByAddressId(addressId);
    }

    @Override
    public void deleteAddressByUserId(String userId) {
        if(addressRepository.countByUserId(userId) <= 0) {
            throw new AddressNotFoundException("Address not found");
        }

        addressRepository.deleteByUserId(userId);
    }
}
