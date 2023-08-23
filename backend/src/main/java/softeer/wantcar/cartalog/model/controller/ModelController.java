package softeer.wantcar.cartalog.model.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.model.dto.EstimateImageDto;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.repository.ModelQueryRepository;
import softeer.wantcar.cartalog.model.service.ModelOptionService;

import javax.websocket.server.PathParam;

@Api(tags = {"모델 관련 API"})
@RestController
@RequestMapping("/api/models")
@RequiredArgsConstructor
@Slf4j
public class ModelController {
    private final ModelQueryRepository modelQueryRepository;
    private final ModelOptionService modelOptionService;

    @ApiOperation(
            value = "모델 타입 조회",
            notes = "모델에서 사용가능한 모델 옵션과 세부 정보를 조회한다.")
    @ApiImplicitParam(
            name = "trimId", value = "트림 식별자", required = true,
            dataType = "java.lang.Long", paramType = "query", example = "1")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 식별자입니다."),
            @ApiResponse(code = 500, message = "적절하지 않은 데이터가 있어 요청을 처리할 수 없습니다. 관리자에게 문의하세요.")})
    @GetMapping("/types")
    public ResponseEntity<ModelTypeListResponseDto> searchModelType(@PathParam("trimId") Long trimId) {
        try {
            return ResponseEntity.ok(modelOptionService.findModelTypeListByTrimId(trimId));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(
            value = "외부 옆면 및 내부 사진 조회",
            notes = "외/내장 색상에 맞는 차량 외부 옆면 및 내부 사진을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "exteriorColorCode", value = "외부 색상 코드", required = true,
                    dataType = "java.lang.String", paramType = "query", example = "A2B"),
            @ApiImplicitParam(
                    name = "interiorColorCode", value = "내부 색상 코드", required = true,
                    dataType = "java.lang.String", paramType = "query", example = "I50")})
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 식별자입니다."),
            @ApiResponse(code = 500, message = "적절하지 않은 데이터가 있어 요청을 처리할 수 없습니다. 관리자에게 문의하세요.")})
    @GetMapping("/images")
    public ResponseEntity<EstimateImageDto> findSideExteriorAndInteriorImage(@RequestParam("exteriorColorCode") String exteriorColorCode,
                                                                             @RequestParam("interiorColorCode") String interiorColorCode) {
        EstimateImageDto estimateImageDto = modelQueryRepository.findCarSideExteriorAndInteriorImage(exteriorColorCode, interiorColorCode);
        if (estimateImageDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(estimateImageDto, HttpStatus.OK);
    }
}
