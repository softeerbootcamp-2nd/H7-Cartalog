package softeer.wantcar.cartalog.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import softeer.wantcar.cartalog.entity.color.TrimExteriorColor;

import java.util.List;

@Slf4j
@Service
public class MockColorService implements ColorService {

    private volatile TrimExteriorColor trimExteriorColor;

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

    @Override
    public List<TrimExteriorColor> findTrimExteriorColorListByTrimId(Long trimId) {
        if (trimId == 1) {
            log.debug("mock 객체가 호출되었습니다. :{}", TrimExteriorColor.class);
            return List.of(getMockData());
        } else {
            throw new RuntimeException("존재하지 않은 trimId 입니다.");
        }
    }

}
