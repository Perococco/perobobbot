package perobobbot.connect4.drawing;

import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import java.util.IntSummaryStatistics;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Value
@Builder
public class Position {


    int columnIdx;
    int rowIdx;

    public Position(int columnIdx, int rowIdx) {
        this.columnIdx = columnIdx;
        this.rowIdx = rowIdx;
    }

    public @NonNull Position displace(@NonNull Displacement displacement) {
        return Position.builder()
                       .columnIdx(columnIdx + displacement.getDeltaColumn())
                       .rowIdx(rowIdx + displacement.getDeltaRow())
                       .build();
    }

    public @NonNull Stream<Position> getPositionAlong(@NonNull Displacement displacement) {
        final int deltaColumn = displacement.getDeltaColumn();
        final int deltaRow = displacement.getDeltaRow();
        return IntStream.iterate(0,i -> i+1)
                        .mapToObj(i -> new Position(columnIdx+i*deltaColumn, rowIdx + i*deltaRow));
    }
}
