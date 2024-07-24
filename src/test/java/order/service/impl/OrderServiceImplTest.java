package order.service.impl;

import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.service.OrderService;
import com.nhnacademy.shoppingmall.order.service.impl.OrderServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {

    OrderRepository orderRepository = Mockito.mock(OrderRepository.class);
    OrderService orderService = new OrderServiceImpl(orderRepository);
    Order testOrder = new Order("a", "user", "광주", "admin");

    @Test
    @DisplayName("getOrder")
    void getOrder() {
        Mockito.when(orderRepository.findByOrderId(anyString())).thenReturn(Optional.of(testOrder));
        orderService.getOrder(testOrder.getOrderId());
        Mockito.verify(orderRepository, Mockito.times(1)).findByOrderId(anyString());
    }

    @Test
    @DisplayName("getOrder -> order not found -> return null")
    void getOrderNotFound() {
        Mockito.when(orderRepository.findByOrderId(anyString())).thenReturn(Optional.empty());
        Order order = orderService.getOrder(testOrder.getOrderId());
        Assertions.assertNull(order);
    }

    @Test
    @DisplayName("save order")
    void saveOrder() {
        Mockito.when(orderRepository.countByOrderId(anyString())).thenReturn(0);
        Mockito.when(orderRepository.save(any())).thenReturn(1);
        orderService.saveOrder(testOrder);
        Mockito.verify(orderRepository, Mockito.times(1)).countByOrderId(anyString());
        Mockito.verify(orderRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("update order status")
    void updateOrderStatus() {
        Mockito.when(orderRepository.countByOrderId(anyString())).thenReturn(1);
        Mockito.when(orderRepository.updateOrderStatusByOrderId(anyString(), any())).thenReturn(1);
        orderService.updateOrderStatus(testOrder);
        Mockito.verify(orderRepository, Mockito.times(1)).updateOrderStatusByOrderId(anyString(), any());
        Mockito.verify(orderRepository, Mockito.times(1)).countByOrderId(anyString());
    }

    @Test
    @DisplayName("update order deliveredAt")
    void updateOrderDeliveredAt() {
        Mockito.when(orderRepository.countByOrderId(anyString())).thenReturn(1);
        Mockito.when(orderRepository.updateDeliveredAtByOrderId(anyString(), any())).thenReturn(1);
        orderService.updateOrderDeliveredAt(testOrder);
        Mockito.verify(orderRepository, Mockito.times(1)).updateDeliveredAtByOrderId(anyString(), any());
        Mockito.verify(orderRepository, Mockito.times(1)).countByOrderId(anyString());
    }

    @Test
    @DisplayName("delete Order")
    void deleteOrder() {
        Mockito.when(orderRepository.countByOrderId(anyString())).thenReturn(1);
        Mockito.when(orderRepository.deleteByOrderId(anyString())).thenReturn(1);
        orderService.deleteOrder(testOrder.getOrderId());
        Mockito.verify(orderRepository, Mockito.times(1)).deleteByOrderId(anyString());
        Mockito.verify(orderRepository, Mockito.times(1)).countByOrderId(anyString());
    }
}
