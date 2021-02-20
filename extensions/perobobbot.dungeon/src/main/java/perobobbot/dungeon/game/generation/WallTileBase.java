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
import java.util.Optional;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public abstract class WallTileBase {

    private final @NonNull WallTiles placeHolder;

    protected WallTileBase(DungeonTile placeHolderTile) {
        this.placeHolder = WallTiles.placeHolder(placeHolderTile);
    }


    public @NonNull Function0<WallTiles> placeHolder() {
        return () -> placeHolder;
    }


    protected abstract @NonNull DungeonCell getCell();

//    protected void showPosition(String header) {
//        final var cell = getDungeonMap().getCellAt(getPosition());
//        final var pi = new MissingPosition(getClass().getSimpleName(),getPosition(),flagToString(cell));
//        this.missingPositions.add(pi);
//    }

    protected String flagToString(DungeonCell cell) {
        final IntFunction<String> toBin = v -> Integer.toString((v&0b111)+8,2).substring(1);
        final var flag = cell.getFlag();
        return IntStream.of(flag>>6,flag>>3,flag)
                        .mapToObj(toBin)
                        .collect(Collectors.joining("_","0b"," "+cell.getExtraFlag().name()));
    }

    protected @NonNull Function0<WallTiles> with(@NonNull Tile... tiles) {
        return () -> WallTiles.with(tiles);
    }

    protected @NonNull WallTiles extraCases(@NonNull Function0<WallTiles> case00,
                                            @NonNull Function0<WallTiles> case01,
                                            @NonNull Function0<WallTiles> case10,
                                            @NonNull Function0<WallTiles> case11,
                                            @NonNull Function0<WallTiles> override
    ) {
        return override.f();
    }

    protected @NonNull WallTiles extraCases(@NonNull Function0<WallTiles> case00,
                                            @NonNull Function0<WallTiles> case01,
                                            @NonNull Function0<WallTiles> case10,
                                            @NonNull Function0<WallTiles> case11
    ) {
        final var flag = getCell().getExtraFlag();
        return switch (flag) {
            case WW -> case00.f();
            case W_ -> case01.f();
            case __ -> case11.f();
            case _W -> case10.f();
        };
    }

}
