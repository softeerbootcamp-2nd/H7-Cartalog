package softeer.wantcar.cartalog.trim.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;

import javax.websocket.server.PathParam;
import java.util.List;


@Slf4j
@RestController
@RequestMapping("/models/trims/options")
@RequiredArgsConstructor
public class TrimOptionController {
    @Value("${env.imageServerPath}")
    private String imageServerPath = "example-url";

    @GetMapping("")
    public ResponseEntity<TrimOptionListResponseDto> getOptionInfos(@PathParam("trimId") Long trimId) {
        if(trimId < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        TrimOptionListResponseDto trimOptionListResponseDto = TrimOptionListResponseDto.builder()
                .multipleSelectParentCategory(List.of("상세 품목", "악세사리"))
                .selectOptions(List.of(getTrimOptionDto(11L,
                        "2열 통풍시트",
                        "상세품목",
                        "시트",
                        "2_cool_seat.jpg",
                        350000)))
                .defaultOptions(List.of(getTrimOptionDto(5L,
                        "디젤 2.2 엔진",
                        null,
                        "파워트레인/성능",
                        "/palisade/le-blanc/options/dieselengine2.2_s.jpg",
                        0)))
                .build();
        return new ResponseEntity<>(trimOptionListResponseDto, HttpStatus.OK);
    }

    private TrimOptionListResponseDto.TrimOptionDto getTrimOptionDto(
            Long id, String name, String parentCategory, String childCategory, String imageUrl, int price) {
        return TrimOptionListResponseDto.TrimOptionDto.builder()
                .id(id)
                .name(name)
                .parentCategory(parentCategory)
                .childCategory(childCategory)
                .imageUrl(imageServerPath + imageUrl)
                .price(price)
                .chosen(38)
                .hashTags(List.of("장거리 운전"))
                .build();
    }
}
