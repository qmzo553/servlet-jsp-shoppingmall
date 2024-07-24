package category.service.impl;

import com.nhnacademy.shoppingmall.category.domain.Category;
import com.nhnacademy.shoppingmall.category.repository.CategoryRepository;
import com.nhnacademy.shoppingmall.category.sevice.CategoryService;
import com.nhnacademy.shoppingmall.category.sevice.impl.CategoryServiceImpl;
import lombok.extern.slf4j.Slf4j;
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
public class CategoryServiceImplTest {

    CategoryRepository categoryRepository = Mockito.mock(CategoryRepository.class);
    CategoryService categoryService = new CategoryServiceImpl(categoryRepository);
    Category testCategory = new Category(1, "상의", 0);

    @Test
    @DisplayName("get category")
    void getCategory() {
        Mockito.when(categoryRepository.findByCategoryId(anyInt())).thenReturn(Optional.of(testCategory));
        categoryService.getCategory(testCategory.getCategoryId());
        Mockito.verify(categoryRepository, Mockito.times(1)).findByCategoryId(anyInt());
    }

    @Test
    @DisplayName("save category")
    void saveCategory() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(0);
        Mockito.when(categoryRepository.save(any())).thenReturn(1);
        categoryService.saveCategory(testCategory);
        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).save(any());
    }

    @Test
    @DisplayName("update category")
    void updateCategory() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.update(any())).thenReturn(1);
        categoryService.updateCategory(testCategory);
        Mockito.verify(categoryRepository, Mockito.times(1)).update(any());
        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
    }

    @Test
    @DisplayName("delete category")
    void deleteCategory() {
        Mockito.when(categoryRepository.countByCategoryId(anyInt())).thenReturn(1);
        Mockito.when(categoryRepository.deleteByCategoryId(anyInt())).thenReturn(1);
        categoryService.deleteCategory(testCategory.getCategoryId());
        Mockito.verify(categoryRepository, Mockito.times(1)).countByCategoryId(anyInt());
        Mockito.verify(categoryRepository, Mockito.times(1)).deleteByCategoryId(anyInt());
    }
}
