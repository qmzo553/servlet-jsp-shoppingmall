package order.repository.impl;

import com.nhnacademy.shoppingmall.order.domain.Order;
import com.nhnacademy.shoppingmall.order.repository.OrderRepository;
import com.nhnacademy.shoppingmall.order.repository.impl.OrderRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class OrderRepositoryImplTest {

    OrderRepository orderRepository = new OrderRepositoryImpl();

    Order testOrder;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testOrder = new Order("a", "user", "광주", "admin");
        orderRepository.save(testOrder);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    @DisplayName("order 조회 by orderId")
    void findByOrderId() {
        Optional<Order> orderOptional = orderRepository.findByOrderId(testOrder.getOrderId());
        Assertions.assertEquals(testOrder, orderOptional.get());
    }

    @Test
    @org.junit.jupiter.api.Order(2)
    @DisplayName("order 등록")
    void save() {
        Order newOrder = new Order("b", "user", "서울", "admin");
        int result = orderRepository.save(newOrder);
        Assertions.assertAll(
                ()->Assertions.assertEquals(1,result),
                ()->Assertions.assertEquals(newOrder, orderRepository.findByOrderId(newOrder.getOrderId()).get())
        );
    }

    @Test
    @org.junit.jupiter.api.Order(3)
    @DisplayName("order 삭제")
    void deleteByOrderId() {
        int result = orderRepository.deleteByOrderId(testOrder.getOrderId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(orderRepository.findByOrderId(testOrder.getOrderId()).isPresent())
        );
    }

    @Test
    @org.junit.jupiter.api.Order(4)
    @DisplayName("order status 수정")
    void updateStatus() {
        testOrder.setOrderStatus(Order.Status.DELIVERED);

        int result = orderRepository.updateOrderStatusByOrderId(testOrder.getOrderId(), Order.Status.DELIVERED);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testOrder, orderRepository.findByOrderId(testOrder.getOrderId()).get())
        );
    }

    @Test
    @org.junit.jupiter.api.Order(5)
    @DisplayName("order DeliveredAt 수정")
    void updateDeliveredAt() {
        int result = orderRepository.updateDeliveredAtByOrderId(testOrder.getOrderId(), LocalDateTime.now());
        Assertions.assertEquals(1, result);
    }
}
