package com.nhnacademy.shoppingmall.address.service;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;

public interface AddressService {

    Address getAddress(int addressId);
    List<Address> getAddressesByUserId(String userId);
    void saveAddress(Address address);
    void deleteAddressByAddressId(int addressId);
    void deleteAddressByUserId(String userId);
}
