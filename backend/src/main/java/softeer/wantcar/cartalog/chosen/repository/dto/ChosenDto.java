package softeer.wantcar.cartalog.chosen.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChosenDto<T> {
    private T idCode;
    private long totalRecords;
    private long recentRecords;

    public int getChosen() {
        return Math.round((float) recentRecords * 100 / totalRecords);
    }
}
