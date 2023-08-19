package softeer.wantcar.cartalog.estimate.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;

@Api(tags = {"견적서 API"})
@RestController
@RequestMapping("/estimates")
@RequiredArgsConstructor
public class EstimateController {
    private final EstimateQueryRepository estimateQueryRepository;

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
}
