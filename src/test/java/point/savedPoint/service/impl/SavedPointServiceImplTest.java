package point.savedPoint.service.impl;

import com.nhnacademy.shoppingmall.point.savedPoint.domain.SavedPoint;
import com.nhnacademy.shoppingmall.point.savedPoint.repository.SavedPointRepository;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.SavedPointService;
import com.nhnacademy.shoppingmall.point.savedPoint.sevice.impl.SavedPointServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class SavedPointServiceImplTest {

    SavedPointRepository savedPointRepository = Mockito.mock(SavedPointRepository.class);
    SavedPointService savedPointService = new SavedPointServiceImpl(savedPointRepository);
    SavedPoint testSavedPoint = new SavedPoint("a", 1000, LocalDateTime.now(), "a", "user");

    @Test
    @DisplayName("getSavedPoint")
    void getSavedPoint() {
        Mockito.when(savedPointRepository.findBySavedPointId(anyString())).thenReturn(Optional.of(testSavedPoint));
        savedPointService.getSavedPoint(testSavedPoint.getSavedPointId());
        Mockito.verify(savedPointRepository, Mockito.times(1)).findBySavedPointId(anyString());
    }

    @Test
    @DisplayName("save savedPoint")
    void saveSavedPoint() {
        Mockito.when(savedPointRepository.countBySavedPointId(anyString())).thenReturn(0);
        Mockito.when(savedPointRepository.save(any())).thenReturn(1);
        savedPointService.saveSavedPoint(testSavedPoint);
        Mockito.verify(savedPointRepository, Mockito.times(1)).save(any());
        Mockito.verify(savedPointRepository, Mockito.times(1)).countBySavedPointId(anyString());
    }

    @Test
    @DisplayName("delete savedPoint")
    void deleteSavedPoint() {
        Mockito.when(savedPointRepository.countBySavedPointId(anyString())).thenReturn(1);
        Mockito.when(savedPointRepository.save(any())).thenReturn(1);
        savedPointService.deleteSavedPoint(testSavedPoint.getSavedPointId());
        Mockito.verify(savedPointRepository, Mockito.times(1)).deleteBySavedPointId(anyString());
        Mockito.verify(savedPointRepository, Mockito.times(1)).countBySavedPointId(anyString());
    }
}
