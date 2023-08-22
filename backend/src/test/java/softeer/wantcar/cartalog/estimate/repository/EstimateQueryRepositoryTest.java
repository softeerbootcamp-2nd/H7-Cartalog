package softeer.wantcar.cartalog.estimate.repository;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import softeer.wantcar.cartalog.estimate.repository.dto.*;
import softeer.wantcar.cartalog.global.ServerPath;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;


@DisplayName("견적 Repository 테스트")
@JdbcTest
@Transactional
@Sql({"classpath:schema.sql"})
class EstimateQueryRepositoryTest {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    EstimateQueryRepository estimateQueryRepository;
    SoftAssertions softAssertions;
    ServerPath serverPath = new ServerPath();

    @BeforeEach
    void setUp() {
        estimateQueryRepository = new EstimateQueryRepositoryImpl(jdbcTemplate, new ServerPath());
        softAssertions = new SoftAssertions();
    }

    @Nested
    @DisplayName("findEstimateOptionIdsByEstimateId 테스트")
    class findEstimateOptionIdsByEstimateIdTest {
        @Test
        @DisplayName("존재하는 견적 식별자를 전달할 경우, 견적의 트림 식별자, 선택 옵션 식별자 목록, 패키지 식별자 목록을 반환한다")
        void returnEstimateInfo() {
            //given
            //when
            EstimateOptionIdListDto estimateInfo = estimateQueryRepository.findEstimateOptionIdsByEstimateId(1L);

            //then
            softAssertions.assertThat(estimateInfo).isNotNull();
            softAssertions.assertThat(estimateInfo.getOptionIds()).isNotNull();
            softAssertions.assertThat(estimateInfo.getPackageIds()).isNotNull();
            List<Character> prefixes = estimateInfo.getAllOptionIds().stream()
                    .map(id -> id.charAt(0))
                    .distinct()
                    .collect(Collectors.toList());
            softAssertions.assertThat(prefixes.size())
                    .isLessThanOrEqualTo(2);
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적 식별자를 전달할 경우 null을 반환한다")
        void returnNull() {
            //given
            //when
            EstimateOptionIdListDto estimateInfo = estimateQueryRepository.findEstimateOptionIdsByEstimateId(-1L);

            //then
            assertThat(estimateInfo).isNull();
        }
    }

    @Nested
    @DisplayName("findEstimateCounts 테스트")
    class findEstimateCountsTest {
        //TODO: 등록된 유사 견적이 존재하지 않아 테스트 불가
        @Test
        @DisplayName("존재하는 견적 식별자들을 전달할 경우 견적의 개수를 반환한다")
        void returnEstimateCounts() {
            //given
            //when
            List<EstimateCountDto> estimateCounts = estimateQueryRepository.findEstimateCounts(List.of(1L, 2L, 3L));

            //then
            softAssertions.assertThat(estimateCounts.size()).isEqualTo(3);
            for (int estimateId = 1; estimateId <= estimateCounts.size(); estimateId++) {
                softAssertions.assertThat(estimateCounts.get(estimateId - 1).getEstimateId())
                        .isEqualTo(estimateId);
            }
//            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적 식별자들만을 전달할 경우 빈 List를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<EstimateCountDto> estimateCounts = estimateQueryRepository.findEstimateCounts(List.of(-1L));

            //then
            assertThat(estimateCounts.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("findSimilarEstimateInfoBydEstimateIds 테스트")
    class findSimilarEstimateInfoBydEstimateIdsTest {
        @Test
        @DisplayName("견적 식별자 목록에 존재하는 견적 정보를 반환한다")
        void returnEstimateInfos() {
            //given
            //when
            List<EstimateInfoDto> estimateInfos =
                    estimateQueryRepository.findEstimateInfoBydEstimateIds(List.of(1L, 2L, 3L));

            //then
            Map<Long, List<EstimateInfoDto>> mappedEstimateInfos = estimateInfos.stream()
                    .collect(Collectors.groupingBy(EstimateInfoDto::getEstimateId));
            softAssertions.assertThat(mappedEstimateInfos.keySet().size()).isEqualTo(3);
            softAssertions.assertThat(mappedEstimateInfos.keySet()).containsAll(List.of(1L, 2L, 3L));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("견적 식별자 목록의 모든 식별자가 존재하지 않는다면 빈 리스트를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<EstimateInfoDto> estimateInfos =
                    estimateQueryRepository.findEstimateInfoBydEstimateIds(List.of(-1L));

            //then
            assertThat(estimateInfos.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("findSimilarEstimateOptionsByEstimateIds 테스트")
    class findSimilarEstimateOptionsByEstimateIdsTest {
        @Test
        @DisplayName("견적 식별자 목록에 존재하는 견적 선택 옵션 정보를 반환한다")
        void returnSelectiveOptionInfos() {
            //given
            //when
            //TODO: 3, 4이 아니거나, 데이터가 변경될 경우 실패 가능
            List<EstimateOptionInfoDto> estimateOptions =
                    estimateQueryRepository.findEstimateOptionsByEstimateIds(List.of(3L, 4L));

            //then
            Map<Long, List<EstimateOptionInfoDto>> mappedEstimateOptionInfos = estimateOptions.stream()
                    .collect(Collectors.groupingBy(EstimateOptionInfoDto::getEstimateId));
            for (EstimateOptionInfoDto estimateOption : estimateOptions) {
                softAssertions.assertThat(estimateOption.getImageUrl()).startsWith(serverPath.IMAGE_SERVER_PATH);
            }
            softAssertions.assertThat(mappedEstimateOptionInfos.keySet().size()).isEqualTo(2);
            softAssertions.assertThat(mappedEstimateOptionInfos.keySet()).containsAll(List.of(3L, 4L));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("견적 식별자 목록의 모든 식별자가 존재하지 않는다면 빈 리스트를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<EstimateOptionInfoDto> estimateOptions =
                    estimateQueryRepository.findEstimateOptionsByEstimateIds(List.of(-1L));

            //then
            assertThat(estimateOptions.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("findSimilarEstimatePackagesBtEstimateIds 테스트")
    class findSimilarEstimatePackagesBtEstimateIdsTest {
        @Test
        @DisplayName("견적 식별자 목록에 존재하는 견적 패키지 정보를 반환한다")
        void returnSelectivePackageInfos() {
            //given
            //when
            //TODO: 1, 3이 아니거나, 데이터가 변경될 경우 실패 가능
            List<EstimateOptionInfoDto> estimatePackages =
                    estimateQueryRepository.findEstimatePackagesByEstimateIds(List.of(1L, 3L));

            //then
            for (EstimateOptionInfoDto estimatePackage : estimatePackages) {
                softAssertions.assertThat(estimatePackage.getImageUrl()).startsWith(serverPath.IMAGE_SERVER_PATH);
            }
            Map<Long, List<EstimateOptionInfoDto>> mappedEstimatePackageInfos = estimatePackages.stream()
                    .collect(Collectors.groupingBy(EstimateOptionInfoDto::getEstimateId));
            softAssertions.assertThat(mappedEstimatePackageInfos.keySet().size()).isEqualTo(2);
            softAssertions.assertThat(mappedEstimatePackageInfos.keySet()).containsAll(List.of(1L, 3L));
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("견적 식별자 목록의 모든 식별자가 존재하지 않는다면 빈 리스트를 반환한다")
        void returnEmptyList() {
            //given
            //when
            List<EstimateOptionInfoDto> estimatePackages =
                    estimateQueryRepository.findEstimatePackagesByEstimateIds(List.of(-1L));

            //then
            assertThat(estimatePackages.isEmpty()).isTrue();
        }
    }

    @Nested
    @DisplayName("견적서 조회 테스트")
    class findEstimateShareInfoByEstimateIdTest {
        @Test
        @DisplayName("적절한 견적서 식별자를 전달 했을 때 견적서 정보를 반환해야 한다.")
        void success() {
            //given
            //when
            EstimateShareInfoDto estimateShareInfo = estimateQueryRepository.findEstimateShareInfoByEstimateId(103L);

            //then
            softAssertions.assertThat(estimateShareInfo.getDetailTrimId()).isEqualTo(10L);
            softAssertions.assertThat(estimateShareInfo.getTrimId()).isEqualTo(2L);
            softAssertions.assertThat(estimateShareInfo.getDisplacement()).isEqualTo(2199.0f);
            softAssertions.assertThat(estimateShareInfo.getFuelEfficiency()).isEqualTo(12.16f);
            softAssertions.assertThat(estimateShareInfo.getExteriorColorCode()).isEqualTo("P7V");
            softAssertions.assertThat(estimateShareInfo.getInteriorColorCode()).isEqualTo("YJY");
            softAssertions.assertThat(estimateShareInfo.getExteriorColorImageUrl())
                    .isEqualTo(serverPath.attachImageServerPath("colors/exterior/P7V.png"));
            softAssertions.assertThat(estimateShareInfo.getExteriorCarImageDirectory()).isEqualTo(serverPath.attachImageServerPath("palisade/exterior/P7V/"));
            softAssertions.assertThat(estimateShareInfo.getExteriorColorPrice()).isEqualTo(0);
            softAssertions.assertThat(estimateShareInfo.getExteriorColorName()).isEqualTo("그라파이트 그레이 메탈릭");
            softAssertions.assertThat(estimateShareInfo.getInteriorCarImageUrl())
                    .isEqualTo(serverPath.attachImageServerPath("palisade/interior/YJY.png"));
            softAssertions.assertThat(estimateShareInfo.getInteriorColorName()).isEqualTo("쿨그레이");
            softAssertions.assertAll();
        }

        @Test
        @DisplayName("존재하지 않는 견적서 식별자를 전달 했을 때 null을 반환해야 한다.")
        void returnNull() {
            //given
            //when
            EstimateShareInfoDto estimateShareInfo = estimateQueryRepository.findEstimateShareInfoByEstimateId(-1L);

            //then
            assertThat(estimateShareInfo).isNull();
        }
    }

    @Nested
    @DisplayName("견적서 모델 타입 옵션 식별자 조회 세스트")
    class findEstimateModelTypeIdsByEstimateIdTest {
        @Test
        @DisplayName("적절한 견적서 식별자를 전달 했을 때 견적서 모델 타입 옵션을 반환해야 한다.")
        void test() {
            //given

            //when
            List<Long> estimateModelTypeIds = estimateQueryRepository.findEstimateModelTypeIdsByEstimateId(103L);

            //then
            softAssertions.assertThat(estimateModelTypeIds.size()).isEqualTo(3);
            softAssertions.assertThat(estimateModelTypeIds.containsAll(List.of(1L, 3L, 6L))).isTrue();
            softAssertions.assertAll();
        }
    }

    @Nested
    @DisplayName("getEstimatePrice 테스트")
    class getEstimatePriceTest {
        //TODO
    }
}
