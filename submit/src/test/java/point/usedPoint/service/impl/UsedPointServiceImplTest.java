package point.usedPoint.service.impl;

import com.nhnacademy.shoppingmall.point.usedPoint.domain.UsedPoint;
import com.nhnacademy.shoppingmall.point.usedPoint.repository.UsedPointRepository;
import com.nhnacademy.shoppingmall.point.usedPoint.service.UsedPointService;
import com.nhnacademy.shoppingmall.point.usedPoint.service.impl.UsedPointServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
public class UsedPointServiceImplTest {

    UsedPointRepository usedRepository = Mockito.mock(UsedPointRepository.class);
    UsedPointService usedPointService = new UsedPointServiceImpl(usedRepository);
    UsedPoint testUsedPoint = new UsedPoint("a", 1000, LocalDateTime.now(), "a", "user");

    @Test
    @DisplayName("get UsedPoint")
    public void getUsedPoint() {
        Mockito.when(usedRepository.findByUsedPointId(anyString())).thenReturn(Optional.of(testUsedPoint));
        usedPointService.getUsedPoint(testUsedPoint.getUsedPointId());
        Mockito.verify(usedRepository, Mockito.times(1)).findByUsedPointId(anyString());
    }

    @Test
    @DisplayName("save UsedPoint")
    public void saveUsedPoint() {
        Mockito.when(usedRepository.countByUsedPointId(anyString())).thenReturn(0);
        Mockito.when(usedRepository.save(any())).thenReturn(1);
        usedPointService.saveUsedPoint(testUsedPoint);
        Mockito.verify(usedRepository, Mockito.times(1)).save(any());
        Mockito.verify(usedRepository, Mockito.times(1)).countByUsedPointId(anyString());
    }

    @Test
    @DisplayName("delete UsedPoint")
    public void deleteUsedPoint() {
        Mockito.when(usedRepository.countByUsedPointId(anyString())).thenReturn(1);
        Mockito.when(usedRepository.deleteByUsedPointId(anyString())).thenReturn(1);
        usedPointService.deleteUsedPoint(testUsedPoint.getUsedPointId());
        Mockito.verify(usedRepository, Mockito.times(1)).deleteByUsedPointId(anyString());
        Mockito.verify(usedRepository, Mockito.times(1)).countByUsedPointId(anyString());
    }
}
