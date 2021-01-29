package perobobbot.connect4.game;

import lombok.NonNull;
import perobobbot.connect4.TokenType;

import java.util.concurrent.CompletionStage;

public interface Player {

    @NonNull TokenType getTeam();

    @NonNull CompletionStage<Integer> getPlayerMove(@NonNull Connect4State currentState);

    void dispose();
}
