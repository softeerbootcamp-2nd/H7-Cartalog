package softeer.wantcar.cartalog.similarity.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.estimate.dto.EstimateCountInfoDto;
import softeer.wantcar.cartalog.estimate.service.EstimateService;
import softeer.wantcar.cartalog.similarity.dto.*;
import softeer.wantcar.cartalog.similarity.service.SimilarityService;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"유사 견적 관련 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/similarity")
public class SimilarityController {
    private final SimilarityService similarityService;
    private final EstimateService estimateService;

    @ApiOperation(
            value = "유사 견적 조회",
            notes = "견적 식별자와 유사한 견적 정보를 제공한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "estimateId", value = "견적 식별자", required = true,
                    dataType = "java.lang.Long", paramType = "query", example = "1"),
            @ApiImplicitParam(
                    name = "similarEstimateId", value = "유사 견적 식별자", required = true,
                    dataType = "java.lang.Long", paramType = "query", example = "2")})
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 견적 식별자입니다."),
            @ApiResponse(code = 500, message = "적절하지 않은 데이터가 있어 요청을 처리할 수 없습니다. 관리자에게 문의하세요.")})
    @GetMapping("/releases")
    public ResponseEntity<SimilarEstimateResponseDto> findSimilarEstimates(@RequestParam("estimateId") Long estimateId,
                                                                           @RequestParam("similarEstimateId") Long similarEstimateId) {
        SimilarEstimateResponseDto similarEstimateResponseDto =
                similarityService.getSimilarEstimateInfo(estimateId, similarEstimateId);
        if (similarEstimateResponseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(similarEstimateResponseDto);
    }

    @ApiOperation(
            value = "유사 견적 개수 조회",
            notes = "내 견적의 출고 개수와 유사 견적들의 출고 개수, 식별자를 제공한다.")
    @ApiImplicitParam(
            name = "estimateId", value = "내 견적 식별자", required = true,
            dataType = "java.lang.Long", paramType = "query", example = "1")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 견적 식별자입니다."),
            @ApiResponse(code = 500, message = "적절하지 않은 데이터가 있어 요청을 처리할 수 없습니다. 관리자에게 문의하세요.")})
    @GetMapping("")
    public ResponseEntity<SimilarEstimateCountResponseDto> findSimilarEstimateCounts(@RequestParam("estimateId") Long estimateId) {
        SimilarEstimateCountResponseDto similarEstimateCounts = similarityService.getSimilarEstimateCounts(estimateId);
        if (similarEstimateCounts == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(similarEstimateCounts);
    }

    @ApiOperation(
            value = "유사 견적 개수 및 세부 사항 조회",
            notes = "내 견적의 출고 개수와 유사 견적들의 출고 개수, 유사 견적 정보를 제공한다.")
    @ApiImplicitParam(
            name = "estimateId", value = "내 견적 식별자", required = true,
            dataType = "java.lang.Long", paramType = "query", example = "1")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 견적 식별자입니다."),
            @ApiResponse(code = 500, message = "적절하지 않은 데이터가 있어 요청을 처리할 수 없습니다. 관리자에게 문의하세요.")})
    @GetMapping("/countInfo")
    public ResponseEntity<SimilarEstimateCountInfoResponseDto> findSimilarEstimateCountAndInfo(@RequestParam("estimateId") Long estimateId) {
        SimilarEstimateCountResponseDto similarEstimateCounts = similarityService.getSimilarEstimateCounts(estimateId);
        if (similarEstimateCounts == null) {
            return ResponseEntity.notFound().build();
        }

        List<EstimateCountInfoDto> estimateCountInfoList = similarEstimateCounts.getSimilarEstimateCounts().stream()
                .map(counts -> EstimateCountInfoDto.builder()
                        .estimateId(counts.getEstimateId())
                        .count(counts.getCount())
                        .estimateInfo(estimateService.findEstimateByEstimateId(counts.getEstimateId()))
                        .build()
                )
                .collect(Collectors.toUnmodifiableList());

        return ResponseEntity.ok().body(SimilarEstimateCountInfoResponseDto.builder()
                .myEstimateCount(similarEstimateCounts.getMyEstimateCount())
                .similarEstimateCounts(estimateCountInfoList)
                .build());
    }

    @ApiOperation(
            value = "모든 유사 견적 조회",
            notes = "모든 견적 식별자와 유사한 견적 정보를 제공한다.")
    @ApiImplicitParam(
            name = "estimateId", value = "내 견적 식별자", required = true,
            dataType = "java.lang.Long", paramType = "query", example = "1")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 견적 식별자입니다.")})
    @GetMapping("/releasesAll")
    public ResponseEntity<SimilarEstimateListResponseDto> findAllSimilarEstimates(@RequestParam("estimateId") Long estimateId) {
        SimilarEstimateCountResponseDto similarEstimateCounts = similarityService.getSimilarEstimateCounts(estimateId);
        if (similarEstimateCounts == null) {
            return ResponseEntity.notFound().build();
        }

        List<SimilarEstimateWithSideImageResponseDto> similarEstimateResponseDtoList = similarEstimateCounts.getSimilarEstimateCounts().stream()
                .map(estimateCountDto -> similarityService.getSimilarEstimateInfo2(estimateId, estimateCountDto.getEstimateId()))
                .collect(Collectors.toUnmodifiableList());

        SimilarEstimateListResponseDto build = SimilarEstimateListResponseDto.builder()
                .similarEstimates(similarEstimateResponseDtoList)
                .build();

        return ResponseEntity.ok().body(build);
    }
}
