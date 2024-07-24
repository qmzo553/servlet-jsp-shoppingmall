package com.nhnacademy.shoppingmall.address.repository;

import com.nhnacademy.shoppingmall.address.domain.Address;

import java.util.List;
import java.util.Optional;

public interface AddressRepository {

    Optional<Address> findByAddressId(int addressId);
    List<Address> findByUserId(String userId);
    int save(Address address);
    int deleteByAddressId(int addressId);
    int deleteByUserId(String userId);
    int findLastAddressId();
    int countByUserId(String userId);
    int countByAddressId(int addressId);
}
