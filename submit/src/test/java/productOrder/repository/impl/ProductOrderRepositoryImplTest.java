package productOrder.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.productOrder.domain.ProductOrder;
import com.nhnacademy.shoppingmall.productOrder.repository.ProductOrderRepository;
import com.nhnacademy.shoppingmall.productOrder.repository.impl.ProductOrderRepositoryImpl;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ProductOrderRepositoryImplTest {

    ProductOrderRepository productOrderRepository = new ProductOrderRepositoryImpl();

    ProductOrder testProductOrder;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testProductOrder = new ProductOrder(1, "a", 5, 1000);
        productOrderRepository.save(testProductOrder);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("product_order 조회 by productId, orderId")
    void findByProductIdAndOrderId() {
        Optional<ProductOrder> productOrderOptional = productOrderRepository.findByProductIdAndOrderId(testProductOrder.getProductId(), testProductOrder.getOrderId());
        Assertions.assertEquals(testProductOrder, productOrderOptional.get());
    }

    @Test
    @Order(2)
    @DisplayName("product_order 조회 by orderId")
    void findByOrderId() {
        List<ProductOrder> productOrders = productOrderRepository.findByOrderId(testProductOrder.getOrderId());
        Assertions.assertEquals(1, productOrders.size());
    }

    @Test
    @Order(3)
    @DisplayName("save product_order")
    void save() {
        ProductOrder newProductOrder = new ProductOrder(2, "a", 8, 1000);
        int result = productOrderRepository.save(newProductOrder);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newProductOrder, productOrderRepository.findByProductIdAndOrderId(newProductOrder.getProductId(), newProductOrder.getOrderId()).get())
        );
    }

    @Test
    @Order(4)
    @DisplayName("delete product_order by productId, orderId")
    void deleteByProductIdAndOrderId() {
        int result = productOrderRepository.deleteByProductIdAndOrderId(testProductOrder.getProductId(), testProductOrder.getOrderId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(productOrderRepository.findByProductIdAndOrderId(testProductOrder.getProductId(), testProductOrder.getOrderId()).isPresent())
        );
    }

    @Test
    @Order(5)
    @DisplayName("delete product_order by orderId")
    void deleteByOrderId() {
        int result = productOrderRepository.deleteByOrderId(testProductOrder.getOrderId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(productOrderRepository.findByProductIdAndOrderId(testProductOrder.getProductId(), testProductOrder.getOrderId()).isPresent())
        );
    }
}
