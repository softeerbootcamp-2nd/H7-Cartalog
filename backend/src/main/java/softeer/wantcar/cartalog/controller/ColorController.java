package softeer.wantcar.cartalog.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.dto.TrimExteriorColorListResponseDto;
import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;
import softeer.wantcar.cartalog.service.ColorService;

import java.util.List;

@Slf4j
@RestController
@AllArgsConstructor
public class ColorController {

    private final ColorService colorService;

    @GetMapping(value = "/models/trims/{trimId}/exterior-colors")
    public ResponseEntity<TrimExteriorColorListResponseDto> trimExteriorColorList(@PathVariable(value = "trimId") Long id) {
        try {
            List<TrimExteriorColor> trimExteriorColorList = colorService.findTrimExteriorColorListByTrimId(id);
            return new ResponseEntity<>(TrimExteriorColorListResponseDto.from(trimExteriorColorList), HttpStatus.OK);
        } catch (Exception exception) {
            log.error(exception.getMessage());
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

}
