package payment.service.impl;

import com.nhnacademy.shoppingmall.payment.domain.Payment;
import com.nhnacademy.shoppingmall.payment.repository.PaymentRepository;
import com.nhnacademy.shoppingmall.payment.service.PaymentService;
import com.nhnacademy.shoppingmall.payment.service.impl.PaymentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class PaymentServiceImplTest {

    PaymentRepository paymentRepository = Mockito.mock(PaymentRepository.class);
    PaymentService paymentService = new PaymentServiceImpl(paymentRepository);
    Payment testPayment = new Payment("a", 1000, 1000, 1000, 1000, "a");

    @Test
    @DisplayName("getPayment")
    void getPayment() {
        Mockito.when(paymentRepository.findByPaymentId(anyString())).thenReturn(Optional.of(testPayment));
        paymentService.getPayment(testPayment.getPaymentId());
        Mockito.verify(paymentRepository, Mockito.times(1)).findByPaymentId(anyString());
    }

    @Test
    @DisplayName("savePayment")
    void savePayment() {
        Mockito.when(paymentRepository.countByPaymentId(anyString())).thenReturn(0);
        Mockito.when(paymentRepository.save(any())).thenReturn(1);
        paymentService.savePayment(testPayment);
        Mockito.verify(paymentRepository, Mockito.times(1)).countByPaymentId(anyString());
        Mockito.verify(paymentRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("deletePayment")
    void deletePayment() {
        Mockito.when(paymentRepository.countByPaymentId(anyString())).thenReturn(1);
        Mockito.when(paymentRepository.deleteByPaymentId(anyString())).thenReturn(1);
        paymentService.deletePayment(testPayment.getPaymentId());
        Mockito.verify(paymentRepository, Mockito.times(1)).deleteByPaymentId(anyString());
        Mockito.verify(paymentRepository, Mockito.times(1)).countByPaymentId(anyString());
    }
}
