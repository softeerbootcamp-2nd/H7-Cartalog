package softeer.wantcar.cartalog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MockColorService implements ColorService {

    private volatile TrimExteriorColor trimExteriorColor;
    private volatile TrimInteriorColor trimInteriorColor;

    public TrimExteriorColor getMockData() {
        if (trimExteriorColor == null) {
            synchronized (MockColorService.class) {
                if (trimExteriorColor == null) {
                    log.debug("mock 객체가 생성되었습니다. :{}", TrimExteriorColor.class);
                    trimExteriorColor = TrimExteriorColor.builder()
                            .id(1L)
                            .name("어비스 블랙 펄")
                            .code("141423")
                            .price(0)
                            .chosen(38)
                            .build();
                }
            }
        }
        return trimExteriorColor;
    }

    public TrimInteriorColor getMockData2() {
        if (trimInteriorColor == null) {
            synchronized (MockColorService.class) {
                if (trimInteriorColor == null) {
                    log.debug("mock 객체가 생성되었습니다. :{}", TrimInteriorColor.class);
                    trimInteriorColor = TrimInteriorColor.builder()
                            .id(1L)
                            .name("퀼팅천연(블랙)")
                            .imageUrl("https://want-car.s3.ap-northeast-2.amazonaws.com/colors/interior/I49.png")
                            .price(0)
                            .chosen(38)
                            .build();
                }
            }
        }
        return trimInteriorColor;
    }

    @Override
    public List<TrimExteriorColor> findTrimExteriorColorListByTrimId(Long trimId) {
        if (trimId == 1) {
            log.debug("mock 객체가 호출되었습니다. :{}", TrimExteriorColor.class);
            return List.of(getMockData());
        } else {
            throw new RuntimeException("존재하지 않은 트림 식별자 입니다.");
        }
    }

    @Override
    public List<TrimInteriorColor> findTrimInteriorColorListByTrimId(Long trimId, Long exteriorColorId) {
        if (trimId == 1 && exteriorColorId == 1) {
            log.debug("mock 객체가 호출되었습니다. :{}", TrimInteriorColor.class);
            return List.of(getMockData2());
        } else {
            throw new RuntimeException("존재하지 않은 트림 및 외장 색상 식별자 조합 입니다.");
        }
    }
}
