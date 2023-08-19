package softeer.wantcar.cartalog.estimate.controller;

import io.swagger.annotations.*;
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

    @GetMapping("distribution")
    public ResponseEntity<Long> getDistribution(@RequestParam("trimId") Long trimId) {
        return ResponseEntity.ok(estimateQueryRepository.findAveragePrice(trimId));
    }
}
