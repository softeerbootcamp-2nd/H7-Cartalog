package softeer.wantcar.cartalog.trim.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.service.TrimColorService;

import javax.websocket.server.PathParam;

@Api(tags = {"트림 색상 API"})
@RestController
@RequestMapping("/models/trims")
@RequiredArgsConstructor
@Slf4j
public class TrimColorController {
    private final TrimColorService trimColorService;

    @ApiOperation(
            value = "트림 외장 색상 조회",
            notes = "해당 트림이 선택 가능한 외부 색상을 조회한다.")
    @ApiImplicitParam(
            name = "trimId", value = "트림 식별자", required = true,
            dataType = "java.lang.Long", paramType = "query", example = "2")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 식별자입니다.")})
    @GetMapping(value = "/exterior-colors")
    public ResponseEntity<TrimExteriorColorListResponseDto> searchTrimExteriorColorList(@PathParam("trimId") Long trimId) {
        try {
            return ResponseEntity.ok(trimColorService.findTrimExteriorColor(trimId));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }

    @ApiOperation(
            value = "트림 내장 색상 조회",
            notes = "해당 트림이 선택 가능한 내장 색상을 조회한다.")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "trimId", value = "트림 식별자", required = true,
                    dataType = "java.lang.Long", paramType = "query", example = "2"),
            @ApiImplicitParam(
                    name = "exteriorColorCode", value = "외장 색상 코드", required = true,
                    dataType = "java.lang.String", paramType = "query", example = "A2B")
    })
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 식별자입니다.")})
    @GetMapping(value = "/interior-colors")
    public ResponseEntity<TrimInteriorColorListResponseDto> searchTrimInteriorColorList(@PathParam("trimId") Long trimId,
                                                                                        @PathParam("exteriorColorCode") String exteriorColorCode) {
        try {
            return ResponseEntity.ok(trimColorService.findTrimInteriorColor(trimId, exteriorColorCode));
        } catch (IllegalArgumentException exception) {
            return ResponseEntity.notFound().build();
        }
    }
}
