package perobobbot.dungeon.game.generation;

import lombok.NonNull;
import perobobbot.rendering.Animation;

public interface CharacterGraphic  {

    @NonNull Animation getIdleAnimation();
    @NonNull Animation getMovingAnimation();

}
