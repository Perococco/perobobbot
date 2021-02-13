package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import perobobbot.dungeon.game.DungeonCell;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.dungeon.game.ExtraFlag;
import perobobbot.lang.fp.Function0;
import perobobbot.rendering.tile.Tile;
import perococco.jdgen.api.Position;

import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class WallTileBase {

    private final @NonNull DungeonTile defaultTile;
    private final @NonNull DungeonTile placeholderTile;

    private final List<MissingPosition> missingPositions;

    public WallTileBase(@NonNull DungeonTile defaultTile, @NonNull DungeonTile placeholderTile, @NonNull List<MissingPosition> missingPositions) {
        this.defaultTile = defaultTile;
        this.placeholderTile = placeholderTile;
        this.missingPositions = missingPositions;
    }

    protected abstract Position getPosition();
    protected abstract DungeonMap getDungeonMap();

    public @NonNull Function0<Stream<Tile>> placeHolder() {
        return () -> {
            showPosition("P");
            return Stream.of(placeholderTile);
        };
    }

    public Stream<Tile> getDefault() {
        showPosition("D");
        return Stream.of(defaultTile);
    }


    protected void showPosition(String header) {
        final var cell = getDungeonMap().getCellAt(getPosition());
        final var pi = new MissingPosition(getClass().getSimpleName(),getPosition(),flagToString(cell));
        this.missingPositions.add(pi);
    }

    protected String flagToString(DungeonCell cell) {
        final IntFunction<String> toBin = v -> Integer.toString((v&0b111)+8,2).substring(1);
        final var flag = cell.getFlag();
        return IntStream.of(flag>>6,flag>>3,flag)
                        .mapToObj(toBin)
                        .collect(Collectors.joining("_","0b"," "+cell.getExtraFlag().name()));
    }

    protected @NonNull Function0<Stream<Tile>> with(@NonNull Tile... tiles) {
        return () -> Stream.of(tiles);
    }

    protected @NonNull Stream<Tile> extraCases(@NonNull DungeonCell cell,
                                               @NonNull Function0<Stream<Tile>> case00,
                                               @NonNull Function0<Stream<Tile>> case01,
                                               @NonNull Function0<Stream<Tile>> case10,
                                               @NonNull Function0<Stream<Tile>> case11,
                                               @NonNull Function0<Stream<Tile>> override
    ) {
        return override.f();
    }

    protected @NonNull Stream<Tile> extraCases(@NonNull DungeonCell cell,
                                               @NonNull Function0<Stream<Tile>> case00,
                                               @NonNull Function0<Stream<Tile>> case01,
                                               @NonNull Function0<Stream<Tile>> case10,
                                               @NonNull Function0<Stream<Tile>> case11
    ) {
        if (cell.getFlag() == 0b100_001_001 && false && cell.getExtraFlag() == ExtraFlag.W_) {
            System.out.println("#### "+getPosition());
        }

        return switch (cell.getExtraFlag()) {
            case WW -> case00.f();
            case W_ -> case01.f();
            case __ -> case11.f();
            case _W -> case10.f();
        };
    }

    protected @NonNull Stream<Tile> extraCases(@NonNull DungeonCell cell,
                                               @NonNull Function0<Stream<Tile>> case00,
                                               @NonNull Function0<Stream<Tile>> case01,
                                               @NonNull Function0<Stream<Tile>> case1X
    ) {
        return extraCases(cell, case00, case01, case1X, case1X);

    }
}
