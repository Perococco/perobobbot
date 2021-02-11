package perobobbot.dungeon.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.jdgen.api.*;

import java.util.stream.Stream;

@RequiredArgsConstructor
public class DungeonMap implements Map<DungeonCell> {

    private final @NonNull Map<DungeonCell> map;

    @Override
    public @NonNull DungeonMap duplicate() {
        return new DungeonMap(map.duplicate());
    }

    @Override
    public @NonNull Size getSize() {
        return map.getSize();
    }

    @Override
    public @NonNull Stream<Position> allMapPositions() {
        return map.allMapPositions();
    }

    @Override
    public @NonNull DungeonCell getCellAt(@NonNull Position position) {
        return map.getCellAt(position);
    }

    @Override
    public void setCellAt(@NonNull DungeonCell cell, @NonNull Position position) {
        map.setCellAt(cell,position);
    }

    @Override
    public boolean isOutside(@NonNull Position position) {
        return map.isOutside(position);
    }

    @Override
    public @NonNull DungeonMap setTransformation(@NonNull Transformation transformation) {
        return new DungeonMap(map.setTransformation(transformation));
    }

    @Override
    public @NonNull DungeonMap clearTransformation() {
        return new DungeonMap(map.clearTransformation());
    }

    public @NonNull CellType getCellTypeAt(@NonNull Position position) {
        if (isOutside(position)) {
            return CellType.EMPTY;
        }
        return map.getCellAt(position).getType();
    }
}
