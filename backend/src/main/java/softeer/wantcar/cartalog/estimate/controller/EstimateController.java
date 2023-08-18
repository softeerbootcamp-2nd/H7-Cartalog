package softeer.wantcar.cartalog.estimate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;

@Slf4j
@Api(tags = {"견적서 API"})
@RestController
@RequestMapping("/estimates")
@RequiredArgsConstructor
public class EstimateController {
    private final EstimateQueryRepository estimateQueryRepository;
    private final EstimateService estimateService;

    @ApiOperation(
            value = "트림 평균 출고 가격 조회",
            notes = "실제 출고 견적에서 특정 트림의 평균 출고 가격을 조회합니다.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "trimId", value = "트림 식별자", required = true,
                    dataTypeClass = Long.class, paramType = "query", example = "2")})
    @GetMapping("distribution")
    public ResponseEntity<Long> getDistribution(@RequestParam("trimId") Long trimId) {
        return ResponseEntity.ok(estimateQueryRepository.findAveragePrice(trimId));
    }

    @PostMapping("")
    public ResponseEntity<Long> registerOrGetEstimate(@RequestBody EstimateRequestDto estimateRequestDto) {
        try{
            Long estimateId = estimateService.saveOrFindEstimateId(estimateRequestDto);
            return ResponseEntity.ok().body(estimateId);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
