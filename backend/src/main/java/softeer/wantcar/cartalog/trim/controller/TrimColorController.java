package softeer.wantcar.cartalog.trim.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.entity.model.ModelExteriorColor;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;
import softeer.wantcar.cartalog.trim.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimInteriorColorListResponseDto;
import softeer.wantcar.cartalog.trim.service.TrimColorService;

import javax.websocket.server.PathParam;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/models/trims")
@RequiredArgsConstructor
public class TrimColorController {
    @Value("${env.imageServerPath}")
    private String imageServerPath = "example-url";

    private final TrimColorService trimColorService;

    @GetMapping(value = "/exterior-colors")
    public ResponseEntity<TrimExteriorColorListResponseDto> trimExteriorColorList(@PathParam("trimId") Long id) {
        try {
            Map<ModelExteriorColor, Integer> exteriorColorInfo = trimColorService.findTrimExteriorColorListByTrimId(id);
            return new ResponseEntity<>(TrimExteriorColorListResponseDto.from(exteriorColorInfo, imageServerPath), HttpStatus.OK);
        } catch (Exception exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/interior-colors")
    public ResponseEntity<TrimInteriorColorListResponseDto> trimInteriorColorList(@PathParam("trimId") Long trimId,
                                                                                  @PathParam(value = "exteriorColorId") Long exteriorColorId) {
        try {
            Map<ModelInteriorColor, Integer> interiorCOlorInfo = trimColorService.findTrimInteriorColorListByTrimId(trimId, exteriorColorId);
            return new ResponseEntity<>(TrimInteriorColorListResponseDto.from(interiorCOlorInfo, imageServerPath), HttpStatus.OK);
        } catch (RuntimeException exception) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
}
