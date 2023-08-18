package softeer.wantcar.cartalog.estimate.controller;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.estimate.dto.EstimateRequestDto;
import softeer.wantcar.cartalog.estimate.service.EstimateService;

@Slf4j
@Api(tags = {"견적서 API"})
@RestController
@RequestMapping("/estimates")
@RequiredArgsConstructor
public class EstimateController {
    private final EstimateService estimateService;

    @PostMapping("")
    public ResponseEntity<Long> registerOrGetEstimate(@RequestBody EstimateRequestDto estimateRequestDto) {
        try{
            Long estimateId = estimateService.saveOrFindEstimateId(estimateRequestDto);
            log.info(String.valueOf(estimateId));
            return ResponseEntity.ok().body(estimateId);
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.badRequest().build();
        }
    }
}
