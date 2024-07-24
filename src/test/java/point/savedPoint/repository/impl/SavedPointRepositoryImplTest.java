package point.savedPoint.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.savedPoint.repository.impl.SavedPointRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class SavedPointRepositoryImplTest {
    SavedPointRepositoryImpl savedPointRepository = new SavedPointRepositoryImpl();

    SavedPoint testSavedPoint;

    @BeforeEach
    void setUp() throws SQLException {
        DbConnectionThreadLocal.initialize();
        testSavedPoint = new SavedPoint("a", 1000, LocalDateTime.now(), "a", "user");
        savedPointRepository.save(testSavedPoint);
    }

    @AfterEach
    void tearDown() throws SQLException {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("saved point 조회 by savedPointId")
    void findBySavedPointId() {
        Optional<SavedPoint> savedPointOptional = savedPointRepository.findBySavedPointId(testSavedPoint.getSavedPointId());
        Assertions.assertEquals(testSavedPoint, savedPointOptional.get());
    }

    @Test
    @Order(2)
    @DisplayName("saved point 등록")
    void save() {
        SavedPoint newSavedPoint = new SavedPoint("b", 1000, LocalDateTime.now(), "a", "user");
        int result = savedPointRepository.save(newSavedPoint);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newSavedPoint, savedPointRepository.findBySavedPointId(newSavedPoint.getSavedPointId()).get())
        );
    }

    @Test
    @Order(3)
    @DisplayName("saved point 삭제")
    void deleteBySavedPointId() {
        int result = savedPointRepository.deleteBySavedPointId(testSavedPoint.getSavedPointId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(savedPointRepository.findBySavedPointId(testSavedPoint.getSavedPointId()).isPresent())
        );
    }
}
