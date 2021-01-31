package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.connect4.Team;
import perobobbot.lang.fp.Function2;

@RequiredArgsConstructor
public class SimplePlayerFactor implements Player.Factory {

    public static @NonNull Player.Factory forAI(@NonNull Function2<? super Team, ? super Connect4OverlayController, ? extends Player> constructor) {
        return new SimplePlayerFactor(constructor,true);
    }
    public static @NonNull Player.Factory forViewer(@NonNull Function2<? super Team, ? super Connect4OverlayController, ? extends Player> constructor) {
        return new SimplePlayerFactor(constructor,false);
    }

    private final @NonNull Function2<? super Team, ? super Connect4OverlayController, ? extends Player> constructor;

    private final boolean ai;

    @Override
    public @NonNull Player create(@NonNull Team team, @NonNull Connect4OverlayController controller) {
        return constructor.f(team,controller);
    }

    @Override
    public boolean isAI() {
        return ai;
    }
}
