package softeer.wantcar.cartalog.trim.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import softeer.wantcar.cartalog.entity.Color;
import softeer.wantcar.cartalog.entity.model.ModelExteriorColor;
import softeer.wantcar.cartalog.entity.model.ModelInteriorColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class MockTrimColorService implements TrimColorService {

    private volatile ModelExteriorColor modelExteriorColor;
    private volatile ModelInteriorColor modelInteriorColor;

    public ModelExteriorColor getMockModelExteriorColor() {
        if (modelExteriorColor == null) {
            synchronized (MockTrimColorService.class) {
                if (modelExteriorColor == null) {
                    log.debug("mock 객체가 생성되었습니다. :{}", ModelExteriorColor.class);
                    modelExteriorColor = ModelExteriorColor.builder()
                            .id(1L)
                            .color(Color.builder()
                                    .id("A2B")
                                    .name("어비스 블랙 펄")
                                    .code("141423")
                                    .build())
                            .price(0)
                            .build();
                }
            }
        }
        return modelExteriorColor;
    }

    public ModelInteriorColor getMockModelInteriorColor() {
        if (modelInteriorColor == null) {
            synchronized (MockTrimColorService.class) {
                if (modelInteriorColor == null) {
                    log.debug("mock 객체가 생성되었습니다. :{}", ModelInteriorColor.class);
                    modelInteriorColor = ModelInteriorColor.builder()
                            .id("A22")
                            .name("퀼팅천연(블랙)")
                            .price(0)
                            .imageUrl("example-url/colors/interior/I49.png")
                            .build();
                }
            }
        }
        return modelInteriorColor;
    }

    @Override
    public Map<ModelExteriorColor, Integer> findTrimExteriorColorListByTrimId(Long trimId) {
        if (trimId == 1) {
            log.debug("mock 객체가 호출되었습니다. :{}", ModelExteriorColor.class);
            Map<ModelExteriorColor, Integer> modelExteriorColorAndChosen = new HashMap<>();
            for (ModelExteriorColor exteriorColor : List.of(getMockModelExteriorColor())) {
                modelExteriorColorAndChosen.put(exteriorColor, 38);
            }
            return modelExteriorColorAndChosen;
        } else {
            throw new RuntimeException("존재하지 않은 트림 식별자 입니다.");
        }
    }

    @Override
    public Map<ModelInteriorColor, Integer> findTrimInteriorColorListByTrimId(Long trimId, Long exteriorColorId) {
        if (trimId == 1 && exteriorColorId == 1) {
            log.debug("mock 객체가 호출되었습니다. :{}", ModelInteriorColor.class);
            Map<ModelInteriorColor, Integer> modelInteriorColorAndChosen = new HashMap<>();
            for (ModelInteriorColor interiorColor : List.of(getMockModelInteriorColor())) {
                modelInteriorColorAndChosen.put(interiorColor, 38);
            }

            return modelInteriorColorAndChosen;
        } else {
            throw new RuntimeException("존재하지 않은 트림 및 외장 색상 식별자 조합 입니다.");
        }
    }
}
