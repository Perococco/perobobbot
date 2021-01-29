package perobobbot.connect4;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GridPosition {

    int rowIdx;
    int columnIdx;

    public GridPosition(int rowIdx, int columnIdx) {
        this.columnIdx = columnIdx;
        this.rowIdx = rowIdx;
    }

}
