package perobobbot.dungeon.game.generation;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.dungeon.game.DungeonTile;
import perobobbot.rendering.Animation;

@RequiredArgsConstructor
public enum PlayerGraphics implements CharacterGraphic {
    ELF_F(DungeonTile.ELF_F_IDLE_ANIM_0,DungeonTile.ELF_F_RUN_ANIM_0),
    ELF_M(DungeonTile.ELF_M_IDLE_ANIM_0,DungeonTile.ELF_M_RUN_ANIM_0)
    ;

    @Getter
    private final @NonNull Animation idleAnimation;
    @Getter
    private final @NonNull Animation movingAnimation;

    PlayerGraphics(@NonNull DungeonTile tileInIdleAnim, @NonNull DungeonTile tileInMovingAnim) {
        this(DungeonTile.animationContaining(tileInIdleAnim), DungeonTile.animationContaining(tileInMovingAnim));
    }



}
