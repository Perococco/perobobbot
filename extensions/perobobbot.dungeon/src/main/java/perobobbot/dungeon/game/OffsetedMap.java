package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.jdgen.api.Cell;
import perococco.jdgen.api.IntPoint;
import perococco.jdgen.api.Map;
import perococco.jdgen.api.Size;

import java.util.function.IntUnaryOperator;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class OffsetedMap<C extends Cell> implements Map<C> {

    private final @NonNull Map<C> map;
    private final IntUnaryOperator xOperator;
    private final IntUnaryOperator yOperator;

    @Override
    public @NonNull Map<C> duplicate() {
        return new OffsetedMap<>(map, xOperator, yOperator);
    }

    @Override
    public @NonNull C getCellAt(int x, int y) {
        return map.getCellAt(xOperator.applyAsInt(x), yOperator.applyAsInt(y));
    }

    @Override
    public @NonNull Size getSize() {
        return map.getSize();
    }

    @Override
    public @NonNull Stream<IntPoint> allMapPositions() {
        return this.map.allMapPositions().map((p) -> new Position(xOperator.applyAsInt(p.getX()), yOperator.applyAsInt(p.getY())));
    }

    @Override
    public boolean isOutside(int x, int y) {
        return map.isOutside(xOperator.applyAsInt(x), yOperator.applyAsInt(y));
    }

    @Override
    public void setCellAt(@NonNull C cell, int x, int y) {
        map.setCellAt(cell, xOperator.applyAsInt(x), yOperator.applyAsInt(y));
    }
}
