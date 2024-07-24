package address.repository.impl;

import com.nhnacademy.shoppingmall.address.domain.Address;
import com.nhnacademy.shoppingmall.address.repository.impl.AddressRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class AddressRepositoryImplTest {
    AddressRepositoryImpl addressRepository = new AddressRepositoryImpl();

    Address testAddress;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testAddress = new Address("광주 북구 서암대로 268", "104동 1903호", "user");
        addressRepository.save(testAddress);
        int addressId = addressRepository.findLastAddressId();
        testAddress = addressRepository.findByAddressId(addressId).get();
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("address 조회 by addressId")
    void findByAddressId() {
        Optional<Address> addressOptional = addressRepository.findByAddressId(testAddress.getAddressId());
        Assertions.assertEquals(testAddress, addressOptional.get());
    }

    @Test
    @Order(2)
    @DisplayName("addresses 조회 by userId")
    void findByUserId() {
        List<Address> addresses = addressRepository.findByUserId(testAddress.getUserId());
        Assertions.assertEquals(1, addresses.size());
    }

    @Test
    @Order(3)
    @DisplayName("save address")
    void saveAddress() {
        Address newAddress = new Address("광주 북구", "조선대 IT융합대학", "user");
        int result = addressRepository.save(newAddress);
        int addressId = addressRepository.findLastAddressId();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(addressId, addressRepository.findByAddressId(addressId).get().getAddressId())
        );
    }

    @Test
    @Order(4)
    @DisplayName("deleteByAddressId")
    void deleteByAddressId() {
        int result = addressRepository.deleteByAddressId(testAddress.getAddressId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(addressRepository.findByAddressId(testAddress.getAddressId()).isPresent())
        );
    }

    @Test
    @Order(5)
    @DisplayName("deleteByUserId")
    void deleteByUserId() {
        int result = addressRepository.deleteByUserId(testAddress.getUserId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertTrue(addressRepository.findByUserId(testAddress.getUserId()).isEmpty())
        );
    }
}
