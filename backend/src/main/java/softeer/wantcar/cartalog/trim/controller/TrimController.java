package softeer.wantcar.cartalog.trim.controller;


import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.trim.dto.TrimListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimQueryRepository;

import javax.websocket.server.PathParam;

@Slf4j
@Api(tags = {"트림 관련 API"})
@RestController
@RequestMapping("/models/trims")
@RequiredArgsConstructor
public class TrimController {
    private final TrimQueryRepository trimQueryRepository;

    @ApiOperation(
            value = "트림 목록 조회",
            notes = "모델의 트림 목록과 트림의 기본 정보를 조회한다")
    @ApiImplicitParam(
            name = "basicModelId", value = "기초 모델 식별자", required = true,
            dataType = "Long", paramType = "query", defaultValue = "None")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 식별자입니다."),
            @ApiResponse(code = 500, message = "서버에서 처리하지 못했습니다. 관리자에게 문의하세요.")})
    @GetMapping("")
    public ResponseEntity<TrimListResponseDto> searchTrimList(@PathParam("basicModelId") Long basicModelId) {
        TrimListResponseDto trimListResponseDto = trimQueryRepository.findTrimsByBasicModelId(basicModelId);
        if (trimListResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trimListResponseDto, HttpStatus.OK);
    }
}
