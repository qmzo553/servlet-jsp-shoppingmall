package point.usedPoint.repository.impl;

import com.nhnacademy.shoppingmall.common.mvc.transaction.DbConnectionThreadLocal;
import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.repository.UsedPointRepository;
import com.nhnacademy.shoppingmall.point.usedPoint.repository.impl.UsedPointRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class UsedPointrepositoryImplTest {
    UsedPointRepository usedPointRepository = new UsedPointRepositoryImpl();

    UsedPoint testUsedPoint;

    @BeforeEach
    void setUp() {
        DbConnectionThreadLocal.initialize();
        testUsedPoint = new UsedPoint("a", 1000, LocalDateTime.now(), "a", "user");
        usedPointRepository.save(testUsedPoint);
    }

    @AfterEach
    void tearDown() {
        DbConnectionThreadLocal.setSqlError(true);
        DbConnectionThreadLocal.reset();
    }

    @Test
    @Order(1)
    @DisplayName("usedPoint 조회 by usedPointId")
    void getByUsedPointId() {
        Optional<UsedPoint> usedPointOptional = usedPointRepository.findByUsedPointId(testUsedPoint.getUsedPointId());
        Assertions.assertEquals(testUsedPoint, usedPointOptional.get());
    }

    @Test
    @Order(2)
    @DisplayName("usedPoint 등록")
    void save() {
        UsedPoint newUsedPoint = new UsedPoint("b", 1000, LocalDateTime.now(), "a", "user");
        int result = usedPointRepository.save(newUsedPoint);
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertEquals(newUsedPoint, usedPointRepository.findByUsedPointId(newUsedPoint.getUsedPointId()).get())
        );
    }

    @Test
    @Order(2)
    @DisplayName("delete usedPoint")
    void deleteByUsedPointId() {
        int result = usedPointRepository.deleteByUsedPointId(testUsedPoint.getUsedPointId());
        Assertions.assertAll(
                () -> Assertions.assertEquals(1, result),
                () -> Assertions.assertFalse(usedPointRepository.findByUsedPointId(testUsedPoint.getUsedPointId()).isPresent())
        );
    }
}
