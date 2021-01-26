package perobobbot.connect4.game;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.function.IntFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Value
@Builder
public class GridPosition {

    int columnIdx;
    int rowIdx;

    public GridPosition(int columnIdx, int rowIdx) {
        this.columnIdx = columnIdx;
        this.rowIdx = rowIdx;
    }

    public static @NonNull IntFunction<GridPosition> factoryForColumn(int columnIdx) {
        return rowIdx -> new GridPosition(columnIdx,rowIdx);
    }

    public @NonNull Stream<GridPosition> getGridPositionAlong(@NonNull Direction direction) {
        final int deltaColumn = direction.getDeltaColumn();
        final int deltaRow = direction.getDeltaRow();
        return IntStream.iterate(0,i -> i+1)
                        .mapToObj(i -> new GridPosition(columnIdx+i*deltaColumn, rowIdx + i*deltaRow));
    }
}
