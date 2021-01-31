package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.Value;
import perobobbot.connect4.Team;

@Value
public class Move {

    @NonNull Team team;

    int columnIndex;

}
