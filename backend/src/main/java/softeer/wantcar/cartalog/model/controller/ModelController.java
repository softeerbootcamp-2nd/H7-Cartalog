package softeer.wantcar.cartalog.model.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.model.dto.ModelPerformanceDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;

import javax.websocket.server.PathParam;

@Slf4j
@RestController
@RequestMapping("/models")
@RequiredArgsConstructor
public class ModelController {
    private final ModelOptionQueryRepository modelOptionQueryRepository;

    @GetMapping("/{modelId}/types")
    public ResponseEntity<ModelTypeListResponseDto> searchModelType(@PathVariable("modelId") Long modelId) {
        try {
            ModelTypeListResponseDto dto = modelOptionQueryRepository.findByModelId(modelId);

            if (dto.modelTypeSize() == 0) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{modelId}/performance")
    public ResponseEntity<ModelPerformanceDto> getModelPerformance(@PathVariable("modelId") Long modelId,
                                                                   @PathParam("powerTrainId") Long powerTrainId,
                                                                   @PathParam("WDId") Long wdId) {
        if (modelId < 0 || powerTrainId < 0 || wdId < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        ModelPerformanceDto modelPerformance = new ModelPerformanceDto(2199, 12);
        return new ResponseEntity<>(modelPerformance, HttpStatus.OK);
    }
}
