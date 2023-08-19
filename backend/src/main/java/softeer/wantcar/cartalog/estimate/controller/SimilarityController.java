package softeer.wantcar.cartalog.estimate.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateCountResponseDto;
import softeer.wantcar.cartalog.estimate.dto.SimilarEstimateResponseDto;
import softeer.wantcar.cartalog.estimate.service.SimilarityService;

@Api(tags = {"유사 견적 관련 API"})
@RestController
@RequiredArgsConstructor
public class SimilarityController {
    private final SimilarityService similarityService;

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
            notes = "견적 식별자와 유사한 견적의 개수를 제공한다.")
    @ApiImplicitParam(
            name = "estimateId", value = "견적 식별자", required = true,
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
}
