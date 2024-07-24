package payment.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.payment.domain.Payment;
import com.nhnacademy.shoppingmall.payment.repository.PaymentRepository;
import com.nhnacademy.shoppingmall.payment.repository.impl.PaymentRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.Optional;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class PaymentRepositoryImplTest {
    PaymentRepository paymentRepository = new PaymentRepositoryImpl();

    Payment testPayment;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testPayment = new Payment("a", 1000, 1000, 1000, 1000, "a");
        paymentRepository.save(testPayment);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("payment 조회 by paymentId")
    void findByPaymentId() {
        Optional<Payment> paymentOptional = paymentRepository.findByPaymentId(testPayment.getPaymentId());
        Assertions.assertEquals(testPayment, paymentOptional.get());
    }

    @Test
    @Order(2)
    @DisplayName("payment 등록")
    void save() {
        Payment newPayment = new Payment("b", 1000, 1000, 1000, 1000, "a");
        int result = paymentRepository.save(newPayment);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newPayment, paymentRepository.findByPaymentId(newPayment.getPaymentId()).get())
        );
    }

    @Test
    @Order(3)
    @DisplayName("payment 삭제")
    void deleteByPaymentId() {
        int result = paymentRepository.deleteByPaymentId(testPayment.getPaymentId());
        Assertions.assertAll(
                () ->  Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(paymentRepository.findByPaymentId(testPayment.getPaymentId()).isPresent())
        );
    }
}
