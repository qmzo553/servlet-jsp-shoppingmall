package product.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.repository.impl.ProductRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Optional;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class ProductRepositoryImlTest {
    ProductRepository productRepository = new ProductRepositoryImpl();

    Product testProduct;

    @BeforeEach
    void setUp() throws Exception {
        DbConnectionThreadLocal.initialize();
        testProduct = new Product("nhn아카데미", 10000, 10, "a", 1);
        productRepository.save(testProduct);
        int productId = productRepository.getLastProductId();
        testProduct = productRepository.findById(productId).get();
    }

    @AfterEach
    void tearDown() throws Exception {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("product 조회 by productId")
    void findById() {
        Optional<Product> productOptional = productRepository.findById(testProduct.getProductId());
        Assertions.assertEquals(testProduct, productOptional.get());
    }

    @Test
    @Order(2)
    @DisplayName("product 등록")
    void save() {
        Product newProduct = new Product("nhn아카데미2", 10000, 10, "a", 1);
        int result = productRepository.save(newProduct);
        int productId = productRepository.getLastProductId();
        newProduct = productRepository.findById(productId).get();

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(productId, productRepository.findById(productId).get().getProductId())
        );
    }

    @Test
    @Order(3)
    @DisplayName("product 삭제")
    void deleteByProductId() {
        int result = productRepository.deleteByProductId(testProduct.getProductId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(productRepository.findById(testProduct.getProductId()).isPresent())
        );
    }

    @Test
    @Order(4)
    @DisplayName("product 수정")
    void update() {
        testProduct.setProductName("nhn 아카데미");
        testProduct.setProductPrice(20000);
        testProduct.setProductStock(20);

        int result = productRepository.update(testProduct);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testProduct, productRepository.findById(testProduct.getProductId()).get())
        );
    }
}
