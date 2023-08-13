package softeer.wantcar.cartalog.trim.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;
import softeer.wantcar.cartalog.global.ServerPaths;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimColorQueryRepository;
import softeer.wantcar.cartalog.trim.service.TrimColorService;

import javax.websocket.server.PathParam;
import java.util.Map;

@Api(tags = {"트림 색상 API"})
@RestController
@RequestMapping("/models/trims")
@RequiredArgsConstructor
@Slf4j
public class TrimColorController {
    private final TrimColorService trimColorService;
    private final TrimColorQueryRepository trimColorQueryRepository;
    private final ServerPaths serverPaths;

    @ApiOperation(
            value = "트림 외부 색상 조회",
            notes = "해당 트림이 선택 가능한 외부 색상을 조회한다.")
    @ApiImplicitParam(
            name = "trimId", value = "트림 식별자", required = true,
            dataType = "Long", paramType = "query", defaultValue = "None", example = "1")
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 식별자입니다.")})
    @GetMapping(value = "/exterior-colors")
    public ResponseEntity<TrimExteriorColorListResponseDto> searchTrimExteriorColorList(@PathParam("trimId") Long trimId) {
        TrimExteriorColorListResponseDto trimExteriorColorListResponseDto = trimColorQueryRepository.findTrimExteriorColorByTrimId(trimId);
        if (trimExteriorColorListResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trimExteriorColorListResponseDto, HttpStatus.OK);
    }

    @GetMapping(value = "/interior-colors")
    public ResponseEntity<TrimInteriorColorListResponseDto> searchTrimInteriorColorList(@PathParam("trimId") Long trimId,
                                                                                  @PathParam("exteriorColorCode") String exteriorColorCode) {
        TrimInteriorColorListResponseDto trimInteriorColorListResponseDto = trimColorQueryRepository.findTrimInteriorColorByTrimIdAndExteriorColorCode(trimId, exteriorColorCode);
        if (trimInteriorColorListResponseDto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trimInteriorColorListResponseDto, HttpStatus.OK);
    }
}
