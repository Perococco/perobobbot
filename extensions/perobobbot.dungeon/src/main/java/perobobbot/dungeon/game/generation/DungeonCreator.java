package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.dungeon.game.Dungeon;
import perobobbot.dungeon.game.DungeonCell;
import perobobbot.dungeon.game.DungeonMap;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.dungeon.game.entity.Player;
import perobobbot.rendering.Animation;
import perobobbot.rendering.Renderable;
import perococco.gen.generator.DungeonGenerator;
import perococco.jdgen.api.CellFactory;
import perococco.jdgen.api.JDGenConfiguration;
import perococco.jdgen.api.Position;

import java.util.Optional;
import java.util.Random;

@Log4j2
@RequiredArgsConstructor
public class DungeonCreator {

    public static @NonNull Optional<Dungeon> create(@NonNull JDGenConfiguration configuration) {
        return new DungeonCreator(configuration).create();
    }

    public final @NonNull JDGenConfiguration configuration;

    private Random random;
    private DungeonGenerator generator;
    private DungeonMap map;
    private Position playPosition;
    private Animation playerGraphic;
    private Dungeon dungeon;

    private @NonNull Optional<Dungeon> create() {
        createRandomGenerator();
        createGenerator();
        generateMap();
        if (generationFailed()) {
            return Optional.empty();
        }
        initializeTiles();
        pickPlayerGraphic();
        pickPlayerPosition();
        createDungeon();
        return Optional.ofNullable(dungeon);
    }

    private void createRandomGenerator() {
        this.random = new Random(configuration.getSeed()*31+112);
    }

    private void createGenerator() {
        this.generator = DungeonGenerator.create();
    }

    private void generateMap() {
        for (int i = 0; i < 10 && !Thread.currentThread().isInterrupted() && map==null; i++) {
            try {
                this.map = new DungeonMap(generator.generate(configuration, CellFactory.with(DungeonCell::new, DungeonCell[]::new)));
            } catch (RuntimeException error) {
                LOG.debug("Fail to generate dungeon : {}",error.getMessage());
            }
        }
    }

    private boolean generationFailed() {
        return map == null;
    }

    private void initializeTiles() {
        assert map != null;
        DungeonFlagComputer.compute(map);
        DungeonTileInitializer.initializeTiles(map,random);
    }

    private void pickPlayerPosition() {
        final var size = map.getSize();
        this.playPosition = new Position(size.getWidth()/2+3,size.getHeight()/2-30);

    }
    private void pickPlayerGraphic() {
        this.playerGraphic = DungeonTile.animationContaining(DungeonTile.BIG_DEMON_RUN_ANIM_0);
    }


    private void createDungeon() {
        this.dungeon = Dungeon.create(map, Player.create(playPosition,Renderable.NOP));
    }

}
