package softeer.wantcar.cartalog.estimate.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.repository.EstimateQueryRepository;
import softeer.wantcar.cartalog.estimate.service.EstimateService;

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

    @ApiOperation(
            value = "견적서 식별자 조회",
            notes = "견적서 식별자를 조회한다. 만약 없을 경우 견적서를 생성하고 해당 식별자를 조회한다. 선택 옵션, 패키지는 다음과 같은 입력을 받습니다. 옵션: O#, 패키지: P#")
    @ApiImplicitParam(
            name = "estimateRequestDto", value = "견적서 조합", required = true, dataTypeClass = String.class, paramType = "body",
            example = "{\n  \"detailTrimId\": 9,\n  \"exteriorColorCode\": \"A2B\",\n  \"interiorColorCode\": \"I49\",\n  \"selectOptionOrPackageIds\": [\n  ]\n}")
    @ApiResponses({
            @ApiResponse(code = 400, message = "불가능한 조합입니다."),
    })
    @PostMapping("")
    public ResponseEntity<Long> registerOrGetEstimate(@RequestBody EstimateRequestDto estimateRequestDto) {
        Long estimateId = estimateService.saveOrFindEstimateId(estimateRequestDto);
        return ResponseEntity.ok().body(estimateId);
    }
}
