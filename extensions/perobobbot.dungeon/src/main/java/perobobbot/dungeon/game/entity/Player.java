package perobobbot.dungeon.game.entity;

import lombok.NonNull;
import perobobbot.rendering.Renderable;
import perococco.jdgen.api.Position;

public class Player extends EntityBase {

    public static @NonNull Player create(@NonNull Position position, @NonNull Renderable graphic) {
        final var player = new Player();
        player.setGraphics(graphic);
        player.setPosition(position);
        return player;
    }

    public Player() {}


}
