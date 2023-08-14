package softeer.wantcar.cartalog.trim.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.trim.dto.OptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimPackageDetailResponseDto;
import softeer.wantcar.cartalog.trim.service.TrimOptionService;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Objects;


@Slf4j
@Api(tags = {"트림 옵션 관련 API"})
@RestController
@RequestMapping("/models/trims/options")
@RequiredArgsConstructor
public class TrimOptionController {
    private final TrimOptionService trimOptionService;

    @ApiOperation(
            value = "트림 옵션 목록 조회",
            notes = "트림의 옵션 목록과 복수 선택 가능 카테고리 목록을 조회한다")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "detailTrimId", value = "세부 트림 식별자", required = true,
                    dataType = "java.lang.Long", paramType = "query", example = "9"),
            @ApiImplicitParam(
                    name = "interiorColorCode", value = "내부 색상 코드", required = true,
                    dataType = "java.lang.String", paramType = "query", example = "I50")})
    @ApiResponses({
            @ApiResponse(code = 404, message = "존재하지 않는 세부 트림 식별자입니다."),
            @ApiResponse(code = 500, message = "서버에서 처리하지 못했습니다. 관리자에게 문의하세요.")})
    @GetMapping("")
    public ResponseEntity<TrimOptionListResponseDto> getOptionInfos(@PathParam("detailTrimId") Long detailTrimId,
                                                                    @PathParam("interiorColorCode") String interiorColorCode) {
        TrimOptionListResponseDto trimOptionList = trimOptionService.getTrimOptionList(detailTrimId, interiorColorCode);
        if (Objects.isNull(trimOptionList)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(trimOptionList, HttpStatus.OK);
    }

    //TODO: NumberFormatException 처리 필요
    @GetMapping("/{optionId}")
    public ResponseEntity<OptionDetailResponseDto> getOptionDetail(@PathVariable("optionId") String optionId,
                                                                   @PathParam("trimId") Long trimId) {
        int id = Integer.parseInt(optionId.substring(1));
        if ((optionId.charAt(0) != 'O' && optionId.charAt(0) != 'P')) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (id < 0 || trimId < 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        boolean isPackage = optionId.charAt(0) == 'P';
        if (isPackage) {
            TrimPackageDetailResponseDto trimPackageDetail = TrimPackageDetailResponseDto.builder()
                    .options(List.of(TrimPackageDetailResponseDto.PackageOptionDetailDto.builder()
                            .name("패키지 이름")
                            .description("패키지 설명")
                            .hashTags(List.of("장거리 운전"))
                            .hmgData(List.of(new HMGDataDto("구매자 절반 이상이", "2384", "최근 90일 동안")))
                            .build()))
                    .build();
            return new ResponseEntity<>(trimPackageDetail, HttpStatus.OK);
        }

        TrimOptionDetailResponseDto trimOptionDetail = TrimOptionDetailResponseDto.builder()
                .name("2열 통풍시트")
                .description("대강 좋다는 내용")
                .hashTags(List.of("장거리 운전"))
                .hmgData(List.of(new HMGDataDto("주행 중 실제로", "73.2", "15,000km 당")))
                .build();
        return new ResponseEntity<>(trimOptionDetail, HttpStatus.OK);
    }
}
