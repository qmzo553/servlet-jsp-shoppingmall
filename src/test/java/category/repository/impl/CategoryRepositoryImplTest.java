package category.repository.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.repository.impl.CategoryRepositoryImpl;
import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import org.junit.jupiter.api.*;

import java.util.Optional;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class CategoryRepositoryImplTest {
    CategoryRepository categoryRepository = new CategoryRepositoryImpl();

    Category testCategory;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testCategory = new Category("상의", 0);
        categoryRepository.save(testCategory);
        int categoryId = categoryRepository.getLastCategoryId();
        testCategory = categoryRepository.findByCategoryId(categoryId).get();
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("category 조회")
    void findByCategoryId() {
        Optional<Category> categoryOptional = categoryRepository.findByCategoryId(testCategory.getCategoryId());
        Assertions.assertEquals(testCategory, categoryOptional.get());
    }

    @Test
    @Order(2)
    @DisplayName("category 등록")
    void save() {
        Category newCategory = new Category("하의", 0);
        int result = categoryRepository.save(newCategory);
        int categoryId = categoryRepository.getLastCategoryId();
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(categoryId, categoryRepository.findByCategoryId(categoryId).get().getCategoryId())
        );
    }

    @Test
    @Order(3)
    @DisplayName("category 수정")
    void update() {
        testCategory.setCategoryName("신발");

        int result = categoryRepository.update(testCategory);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(testCategory, categoryRepository.findByCategoryId(testCategory.getCategoryId()).get())
        );
    }

    @Test
    @Order(4)
    @DisplayName("category 삭제")
    void deleteByCategoryId() {
        int result = categoryRepository.deleteByCategoryId(testCategory.getCategoryId());

        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(categoryRepository.findByCategoryId(testCategory.getCategoryId()).isPresent())
        );
    }
}
