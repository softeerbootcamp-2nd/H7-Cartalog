package softeer.wantcar.cartalog.model.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.model.dto.ModelTypeListResponseDto;
import softeer.wantcar.cartalog.model.repository.ModelOptionQueryRepository;

import javax.websocket.server.PathParam;

@Api(tags = {"모델 관련 API"})
@RestController
@RequestMapping("/models")
@RequiredArgsConstructor
@Slf4j
public class ModelController {
    private final ModelOptionQueryRepository modelOptionQueryRepository;

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
        ModelTypeListResponseDto dto = modelOptionQueryRepository.findByModelTypeOptionsByBasicModelId(trimId);

        if (dto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }
}
