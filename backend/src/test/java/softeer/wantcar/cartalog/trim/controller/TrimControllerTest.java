package softeer.wantcar.cartalog.trim.controller;


import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import softeer.wantcar.cartalog.trim.dto.DetailTrimInfoDto;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepositoryImpl;
import softeer.wantcar.cartalog.trim.service.TrimQueryService;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@DisplayName("트림 도메인 컨트롤러 테스트")
@ExtendWith(MockitoExtension.class)
class TrimControllerTest {
    TrimController trimController;
    TrimQueryService trimQueryService;
    TrimQueryRepository trimQueryRepository;
    SoftAssertions softAssertions;

    @BeforeEach
    void setUp() {
        softAssertions = new SoftAssertions();
        trimQueryRepository = mock(TrimQueryRepositoryImpl.class);
        trimQueryService = mock(TrimQueryService.class);
        trimController = new TrimController(trimQueryRepository, trimQueryService);
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

    @Nested
    @DisplayName("세부 트림 정보 조회 테스트")
    class getDetailTrimInfoTest {
        @Test
        @DisplayName("적절한 트림 식별자와 모델 타입 식별자들을 전달할 경우 200 상태와 함께 이에 맞는 세부 트림 정보를 반환한다.")
        void returnDetailTrimInfos() {
            //given
            when(trimQueryService.getDetailTrimInfo(1L, List.of(1L, 2L, 3L)))
                    .thenReturn(DetailTrimInfoDto.builder()
                            .detailTrimId(1L)
                            .displacement(1000)
                            .fuelEfficiency(10)
                            .build());

            //when
            ResponseEntity<DetailTrimInfoDto> response = trimController.getDetailTrimInfo(1L, List.of(1L, 2L, 3L));

            //then
            softAssertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(response.getBody()).isNotNull();
            DetailTrimInfoDto detailTrimInfoDto = response.getBody();
            softAssertions.assertThat(detailTrimInfoDto.getDetailTrimId()).isEqualTo(1L);
            softAssertions.assertThat(detailTrimInfoDto.getDisplacement()).isEqualTo(1000L);
            softAssertions.assertThat(detailTrimInfoDto.getFuelEfficiency()).isEqualTo(10L);
        }

        @Test
        @DisplayName("존재하지 않는 식별자일 경우 404 상태와 함께 null을 반환해야 한다")
        void returnNullWhenIdIsNotExist() {
            //given
            when(trimQueryService.getDetailTrimInfo(-1L, List.of(1L, 2L, 3L)))
                    .thenReturn(null);
            when(trimQueryService.getDetailTrimInfo(1L, List.of(-1L, 2L, 3L)))
                    .thenReturn(null);

            //when
            ResponseEntity<DetailTrimInfoDto> wrongTrimId =
                    trimController.getDetailTrimInfo(-1L, List.of(1L, 2L, 3L));
            ResponseEntity<DetailTrimInfoDto> wrongModelTypeId =
                    trimController.getDetailTrimInfo(1L, List.of(-1L, 2L, 3L));

            //then
            softAssertions.assertThat(wrongTrimId.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(wrongTrimId.getBody()).isNull();
            softAssertions.assertThat(wrongModelTypeId.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            softAssertions.assertThat(wrongModelTypeId.getBody()).isNull();
        }

        @Test
        @DisplayName("같은 유형의 모델 타입을 전달할 경우 IllegalArgumentException 에러가 발생해야 한다")
        void throwIllegalArgumentException() {
            //given
            when(trimQueryService.getDetailTrimInfo(1L, List.of(1L, 2L, 3L)))
                    .thenThrow(IllegalArgumentException.class);

            //when
            //then
            assertThatThrownBy(() -> trimController.getDetailTrimInfo(1L, List.of(1L, 2L, 3L)))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}
