package product.service.impl;

import com.nhnacademy.shoppingmall.product.domain.Product;
import com.nhnacademy.shoppingmall.product.exception.ProductAlreadyExistsException;
import com.nhnacademy.shoppingmall.product.repository.ProductRepository;
import com.nhnacademy.shoppingmall.product.service.ProductService;
import com.nhnacademy.shoppingmall.product.service.impl.ProductServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);
    ProductService productService = new ProductServiceImpl(productRepository);
    Product testProduct = new Product(1, "nhn아카데미", 10000, 10, "a", 1);

    @Test
    @DisplayName("getProduct")
    void getProduct() {
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.of(testProduct));
        Assertions.assertEquals(testProduct, productService.getProduct(testProduct.getProductId()));
        Mockito.verify(productRepository, Mockito.times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("getProduct -> product not found -> return null")
    void getProduct_not_found() {
        Mockito.when(productRepository.findById(anyInt())).thenReturn(Optional.empty());
        Product product = productService.getProduct(testProduct.getProductId());
        Assertions.assertNull(product);
    }

    @Test
    @DisplayName("save product")
    void saveProduct() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(0);
        Mockito.when(productRepository.save(any())).thenReturn(1);
        productService.saveProduct(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).countByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("save product - aready exist product")
    void saveProduct_already_exist() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(1);
        Throwable throwable = Assertions.assertThrows(ProductAlreadyExistsException.class, () -> productService.saveProduct(testProduct));
        log.debug("error: {}", throwable.getMessage());
    }

    @Test
    @DisplayName("update product")
    void updateProduct() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(1);
        Mockito.when(productRepository.update(any())).thenReturn(1);
        productService.updateProduct(testProduct);
        Mockito.verify(productRepository, Mockito.times(1)).update(any());
        Mockito.verify(productRepository, Mockito.times(1)).countByProductId(anyInt());
    }

    @Test
    @DisplayName("delete product")
    void deleteProduct() {
        Mockito.when(productRepository.countByProductId(anyInt())).thenReturn(1);
        Mockito.when(productRepository.deleteByProductId(anyInt())).thenReturn(1);
        productService.deleteProduct(testProduct.getProductId());
        Mockito.verify(productRepository, Mockito.times(1)).countByProductId(anyInt());
        Mockito.verify(productRepository, Mockito.times(1)).deleteByProductId(anyInt());
    }
}
