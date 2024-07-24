package address.service.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.AddressRepository;
import com.nhnacademy.shoppingmall.address.service.AddressService;
import com.nhnacademy.shoppingmall.address.service.impl.AddressServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class AddressServiceImplTest {

    AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
    AddressService addressService = new AddressServiceImpl(addressRepository);
    Address testAddress = new Address("광주 북구 서암대로 268", "104동 1903호", "user");

    @Test
    @DisplayName("get Address")
    void getAddress() {
        testAddress.setAddressId(addressRepository.findLastAddressId());
        Mockito.when(addressRepository.findByAddressId(anyInt())).thenReturn(Optional.of(testAddress));
        addressService.getAddress(testAddress.getAddressId());
        Mockito.verify(addressRepository, Mockito.times(1)).findByAddressId(anyInt());
    }

    @Test
    @DisplayName("get Addresses")
    void getAddresses() {
        Mockito.when(addressRepository.findByUserId(anyString())).thenReturn(any());
        addressService.getAddressesByUserId(testAddress.getUserId());
        Mockito.verify(addressRepository, Mockito.times(1)).findByUserId(anyString());
    }

    @Test
    @DisplayName("save Address")
    void saveAddress() {
        Mockito.when(addressRepository.save(any())).thenReturn(1);
        addressService.saveAddress(testAddress);
        Mockito.verify(addressRepository, Mockito.times(1)).save(testAddress);
    }

    @Test
    @DisplayName("delete by addressId")
    void deleteByAddressId() {
        testAddress.setAddressId(addressRepository.findLastAddressId());
        Mockito.when(addressRepository.deleteByAddressId(anyInt())).thenReturn(1);
        Mockito.when(addressRepository.countByAddressId(anyInt())).thenReturn(1);
        addressService.deleteAddressByAddressId(testAddress.getAddressId());
        Mockito.verify(addressRepository, Mockito.times(1)).deleteByAddressId(anyInt());
        Mockito.verify(addressRepository, Mockito.times(1)).countByAddressId(anyInt());
    }

    @Test
    @DisplayName("delete by userId")
    void deleteByUserId() {
        Mockito.when(addressRepository.deleteByUserId(anyString())).thenReturn(1);
        Mockito.when(addressRepository.countByUserId(anyString())).thenReturn(1);
        addressService.deleteAddressByUserId(testAddress.getUserId());
        Mockito.verify(addressRepository, Mockito.times(1)).deleteByUserId(anyString());
        Mockito.verify(addressRepository, Mockito.times(1)).countByUserId(anyString());
    }
}
