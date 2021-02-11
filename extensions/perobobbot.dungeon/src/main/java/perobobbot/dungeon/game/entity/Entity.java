package perobobbot.dungeon.game.entity;

import lombok.NonNull;
import perobobbot.lang.Temporal;
import perobobbot.rendering.Renderable;

public interface Entity extends Temporal {

    @NonNull Renderable getGraphics();

}
