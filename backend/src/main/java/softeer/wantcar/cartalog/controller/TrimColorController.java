package softeer.wantcar.cartalog.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.entity.model.ModelExteriorColor;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;
import softeer.wantcar.cartalog.service.TrimColorService;

import javax.websocket.server.PathParam;
import java.util.Map;

@Slf4j
@RestController
@AllArgsConstructor
public class TrimColorController {

    private final TrimColorService trimColorService;

    @GetMapping(value = "/models/trims/exterior-colors")
    public ResponseEntity<TrimExteriorColorListResponseDto> trimExteriorColorList(@PathParam("trimId") Long id) {
        try {
            Map<ModelExteriorColor, Integer> exteriorColorInfo = trimColorService.findTrimExteriorColorListByTrimId(id);
            return new ResponseEntity<>(TrimExteriorColorListResponseDto.from(exteriorColorInfo), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/models/trims/interior-colors")
    public ResponseEntity<TrimInteriorColorListResponseDto> trimInteriorColorList(@PathParam("trimId") Long trimId,
                                                                                  @PathParam(value = "exteriorColorId") Long exteriorColorId) {
        try {
            Map<ModelInteriorColor, Integer> interiorCOlorInfo = trimColorService.findTrimInteriorColorListByTrimId(trimId, exteriorColorId);
            return new ResponseEntity<>(TrimInteriorColorListResponseDto.from(interiorCOlorInfo), HttpStatus.OK);
        } catch (RuntimeException exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
