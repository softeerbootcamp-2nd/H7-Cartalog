package softeer.wantcar.cartalog.trim.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import softeer.wantcar.cartalog.trim.dto.OptionDetailResponseDto;
import softeer.wantcar.cartalog.trim.dto.TrimOptionListResponseDto;
import softeer.wantcar.cartalog.trim.repository.TrimOptionQueryRepository;
import softeer.wantcar.cartalog.trim.service.TrimOptionService;

import javax.websocket.server.PathParam;
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

    @ApiOperation(
            value = "트림 옵션, 패키지 상세 조회",
            notes = "옵션, 패키지의 상세 내용을 조회한다.")
    @ApiImplicitParam(
            name = "optionId", value = "옵션 혹은 패키지 식별자", required = true,
            dataType = "java.lang.String", paramType = "query", example = "O1")
    @ApiResponses({
            @ApiResponse(code = 400, message = "허용되지 않은 입력입니다. 옵션 조회시 'O#', 패키지 조회시 'P#'을 입력하세요."),
            @ApiResponse(code = 404, message = "존재하지 않는 세부 트림 식별자입니다."),
            @ApiResponse(code = 500, message = "서버에서 처리하지 못했습니다. 관리자에게 문의하세요.")})
    @GetMapping("/detail")
    public ResponseEntity<OptionDetailResponseDto> getOptionDetail(@RequestParam("optionId") String optionId) {
        boolean isPackage;
        Long id;
        try {
            isPackage = isPackage(optionId);
            id = Long.parseLong(optionId.substring(1));
        } catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        OptionDetailResponseDto responseDto = isPackage ?
                trimOptionService.getTrimPackageDetail(id) :
                trimOptionService.getTrimOptionDetail(id);

        return Objects.isNull(responseDto) ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    private boolean isPackage(String optionId) {
        char packageCharacter = optionId.charAt(0);
        if ((packageCharacter != 'O' && packageCharacter != 'P')) {
            throw new IllegalArgumentException();
        }
        return packageCharacter == 'P';
    }
}
