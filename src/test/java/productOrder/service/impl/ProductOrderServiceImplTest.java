package productOrder.service.impl;

import com.nhnacademy.shoppingmall.productOrder.domain.ProductOrder;
import com.nhnacademy.shoppingmall.productOrder.repository.ProductOrderRepository;
import com.nhnacademy.shoppingmall.productOrder.service.ProductOrderService;
import com.nhnacademy.shoppingmall.productOrder.service.impl.ProductOrderServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ProductOrderServiceImplTest {

    ProductOrderRepository productOrderRepository = Mockito.mock(ProductOrderRepository.class);
    ProductOrderService productOrderService = new ProductOrderServiceImpl(productOrderRepository);
    ProductOrder testProductOrder = new ProductOrder(1, "a", 5, 1000);

    @Test
    @DisplayName("get ProductOrder by productId and orderId")
    void getProductOrder() {
        Mockito.when(productOrderRepository.findByProductIdAndOrderId(anyInt(), anyString())).thenReturn(Optional.of(testProductOrder));
        productOrderService.getProductOrder(testProductOrder.getProductId(), testProductOrder.getOrderId());
        Mockito.verify(productOrderRepository, Mockito.times(1)).findByProductIdAndOrderId(anyInt(), anyString());
    }

    @Test
    @DisplayName("get ProductOrder List by orderId")
    void getProductOrderList() {
        Mockito.when(productOrderRepository.findByOrderId(anyString())).thenReturn(any());
        productOrderService.getProductOrderList(testProductOrder.getOrderId());
        Mockito.verify(productOrderRepository, Mockito.times(1)).findByOrderId(anyString());
    }

    @Test
    @DisplayName("save ProductOrder")
    void saveProductOrder() {
        Mockito.when(productOrderRepository.save(any())).thenReturn(1);
        productOrderService.saveProductOrder(testProductOrder);
        Mockito.verify(productOrderRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("save ProductOrders")
    void saveProductOrders() {
        List<ProductOrder> productOrders = new ArrayList<>();
        productOrders.add(testProductOrder);

        Mockito.when(productOrderRepository.save(any())).thenReturn(1);
        productOrderService.saveProductOrders(productOrders);
        Mockito.verify(productOrderRepository, Mockito.times(productOrders.size())).save(any());
    }

    @Test
    @DisplayName("delete ProductOrder by productId and orderId")
    void deleteProductOrderByProducIdAndOrderId() {
        Mockito.when(productOrderRepository.countByOrderId(anyString())).thenReturn(1);
        Mockito.when(productOrderRepository.deleteByProductIdAndOrderId(anyInt(), anyString())).thenReturn(1);
        productOrderService.deleteByProductIdAndOrderId(testProductOrder.getProductId(), testProductOrder.getOrderId());
        Mockito.verify(productOrderRepository, Mockito.times(1)).deleteByProductIdAndOrderId(anyInt(), anyString());
        Mockito.verify(productOrderRepository, Mockito.times(1)).countByOrderId(anyString());
    }

    @Test
    @DisplayName("delete ProductOrder by orderId")
    void deleteProductOrderByOrderId() {
        Mockito.when(productOrderRepository.countByOrderId(anyString())).thenReturn(1);
        Mockito.when(productOrderRepository.deleteByOrderId(anyString())).thenReturn(1);
        productOrderService.deleteByOrderId(testProductOrder.getOrderId());
        Mockito.verify(productOrderRepository, Mockito.times(1)).deleteByOrderId(anyString());
        Mockito.verify(productOrderRepository, Mockito.times(1)).countByOrderId(anyString());
    }
}
