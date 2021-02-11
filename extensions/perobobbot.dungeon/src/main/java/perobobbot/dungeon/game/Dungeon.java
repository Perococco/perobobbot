package perobobbot.dungeon.game;

import lombok.*;
import perobobbot.dungeon.game.entity.Entity;
import perobobbot.dungeon.game.entity.Player;
import perobobbot.lang.Temporal;
import perococco.jdgen.api.Map;
import perococco.jdgen.api.Position;

import java.util.stream.Stream;

@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Dungeon implements Temporal {

    public static @NonNull Dungeon create(@NonNull DungeonMap map, @NonNull Player player) {
        final var dungeon = new Dungeon();
        dungeon.setMap(map);
        dungeon.setPlayer(player);
        return dungeon;
    }

    private @NonNull DungeonMap map;

    private @NonNull Player player;

    @Override
    public void initialize(double t0) {
        player.initialize(t0);
    }

    @Override
    public void update(double dt) {
        player.update(dt);
    }

    public @NonNull Stream<Entity> entities() {
        return Stream.of(player);
    }

    public @NonNull Position getPlayerPosition() {
        return player.getPosition();
    }
}
