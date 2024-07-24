package com.nhnacademy.shoppingmall.address.domain;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
public class Address {

    private int addressId;
    private String addressName;
    private String addressDetail;
    private String userId;

    public Address(int addressId, String addressName, String addressDetail, String userId) {
        this.addressId = addressId;
        this.addressName = addressName;
        this.addressDetail = addressDetail;
        this.userId = userId;
    }

    public Address(String addressName, String addressDetail, String userId) {
        this.addressId = -1;
        this.addressName = addressName;
        this.addressDetail = addressDetail;
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId &&
                Objects.equals(addressName, address.addressName) &&
                Objects.equals(addressDetail, address.addressDetail) &&
                Objects.equals(userId, address.userId);
    }

    @Override
    public int hashCode() { return Objects.hash(addressId, addressName, addressDetail, userId); }

    @Override
    public String toString() {
        return "Address { " +
                "addressId = '" + addressId + '\'' +
                ", addressName = '" + addressName + '\'' +
                ", addressDetail = '" + addressDetail + '\'' +
                ", userId = '" +userId + '}';
    }
}
