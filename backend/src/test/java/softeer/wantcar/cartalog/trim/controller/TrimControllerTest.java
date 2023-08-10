package softeer.wantcar.cartalog.trim.controller;


import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepositoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("트림 도메인 컨트롤러 테스트")
@ExtendWith(MockitoExtension.class)
class TrimControllerTest {
    TrimController trimController;
    TrimQueryRepository trimQueryRepository;
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        trimQueryRepository = mock(TrimQueryRepositoryImpl.class);
        trimController = new TrimController(trimQueryRepository);
    }

    @Nested
    @DisplayName("트림 모델 목록 조회 테스트")
    class getTrimModelListTest {
        @Test
        @DisplayName("올바른 요청시 200 상태와 함께 트림 목록을 반환해야 한다.")
        void returnDtoHasTrimsWhenGetTrimsByExistModel() {
            //given
            Long basicModelId = 1L;
            TrimListResponseDto mockTrimListResponseDto = mock(TrimListResponseDto.class);
            when(trimQueryRepository.findTrimsByBasicModelId(basicModelId))
                    .thenReturn(mockTrimListResponseDto);

            //when
            ResponseEntity<TrimListResponseDto> actualResponse = trimController.searchTrimList(basicModelId);

            //then
            assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(actualResponse.getBody()).isNotNull();
        }

        @Test
        @DisplayName("존재하지 않는 모델 식별자로 트림 목록 요청시 404 상태를 반환해야 한다.")
        void returnStatusCode404WhenGetTrimsByNotExistModel() {
            //given
            Long notExistBasicModelId = -1L;
            when(trimQueryRepository.findTrimsByBasicModelId(notExistBasicModelId))
                    .thenReturn(null);

            //when
            ResponseEntity<TrimListResponseDto> actualResponse = trimController.searchTrimList(notExistBasicModelId);

            //then
            assertThat(actualResponse.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(actualResponse.getBody()).isNull();
        }
    }
}
