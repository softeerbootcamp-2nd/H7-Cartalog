package softeer.wantcar.cartalog.model.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import softeer.wantcar.cartalog.global.dto.HMGDataDto;
import softeer.wantcar.cartalog.global.dto.HMGDataDtoInterface;
import softeer.wantcar.cartalog.global.dto.PowerTrainHMGDataDto;

@Builder
@AllArgsConstructor
@Getter
public class SimpleModelOptionMapper {
    private Long modelOptionId;
    private String name;
    private String childCategory;
    private String imageUrl;
    private String description;
    private String hmgDataName;
    private String hmgDataValue;
    private String hmgDataMeasure;
    private int price;

    /**
     * @throws AssertionError            HMGData가 존재하나 value 값이 존재하지 않을 경우 발생한다.
     * @throws IndexOutOfBoundsException 엔진의 HMGData의 값이 number/number 형식이 아닐 경우 발생한다.
     */
    public HMGDataDtoInterface toHMGDataDto() {
        HMGDataDtoInterface hmgDataDto;
        if (hmgDataName == null && hmgDataValue == null && hmgDataMeasure == null) {
            return null;
        }
        if ("파워트레인/성능".equals(childCategory)) {
            assert hmgDataValue != null;
            String[] valueAndRpm = hmgDataValue.split("/", 2);
            hmgDataDto = PowerTrainHMGDataDto.builder()
                    .name(hmgDataName)
                    .value(Float.parseFloat(valueAndRpm[0]))
                    .rpm(valueAndRpm[1])
                    .measure(hmgDataMeasure)
                    .build();
        } else {
            hmgDataDto = HMGDataDto.builder()
                    .name(hmgDataName)
                    .value(hmgDataValue)
                    .measure(hmgDataMeasure)
                    .build();
        }
        return hmgDataDto;
    }
}
