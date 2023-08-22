package softeer.wantcar.cartalog.chosen.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ChosenDto {
    private String idCode;
    private int chosen;

    public static ChosenDto findChosenDtoById(List<ChosenDto> chosenDtoList, String id) {
        return chosenDtoList.stream()
                .filter(chosenDto -> chosenDto.getIdCode().equals(id))
                .findAny()
                .orElseThrow(RuntimeException::new);
    }
}
