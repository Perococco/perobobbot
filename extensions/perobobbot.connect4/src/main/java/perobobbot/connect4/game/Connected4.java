package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.Value;
import perobobbot.connect4.GridPosition;
import perobobbot.connect4.NotAConnect4;
import perobobbot.connect4.Team;
import perobobbot.lang.fp.Function1;

@Value
public class Connected4 {

    public static @NonNull Connected4 create(@NonNull Team type,
                                             @NonNull GridPosition start,
                                             @NonNull GridPosition end) {
        final Function1<Function1<GridPosition,Integer>,Integer> distance = f -> Math.abs(f.f(start)-f.f(end));

        if (distance.f(GridPosition::getColumnIdx) < 4 && distance.f(GridPosition::getRowIdx) < 4) {
            throw new NotAConnect4(start, end);
        }
        return new Connected4(type, start, end);

    }

    @NonNull Team winningTeam;
    @NonNull GridPosition start;
    @NonNull GridPosition end;
}
