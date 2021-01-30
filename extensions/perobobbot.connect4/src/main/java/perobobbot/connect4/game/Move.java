package perobobbot.connect4.game;

import lombok.NonNull;
import lombok.Value;
import perobobbot.connect4.TokenType;

@Value
public class Move {

    @NonNull TokenType team;

    int columnIndex;

}
