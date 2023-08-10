package softeer.wantcar.cartalog.model.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(
            value = "모델 타입 조회",
            notes = "모델에서 사용가능한 모델 옵션과 세부 정보를 조회한다.")
    @ApiImplicitParam(
            name = "basicModelId", value = "모델 식별자", required = true, dataType = "Long", paramType = "query", defaultValue = "None")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 식별자입니다."),
            @ApiResponse(code = 500, message = "적절하지 않은 데이터가 있어 요청을 처리할 수 없습니다. 관리자에게 문의하세요.")})
    @GetMapping("/types")
    public ResponseEntity<ModelTypeListResponseDto> searchModelType(@PathParam("basicModelId") Long basicModelId) {
        ModelTypeListResponseDto dto;
        try {
            dto = modelOptionQueryRepository.findByModelTypeOptionsByBasicModelId(basicModelId);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
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
