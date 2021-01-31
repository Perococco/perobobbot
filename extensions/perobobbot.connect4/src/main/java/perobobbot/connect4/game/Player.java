package perobobbot.connect4.game;

import lombok.NonNull;
import perobobbot.connect4.Team;

import java.util.concurrent.CompletionStage;

public interface Player {

    @NonNull Team getTeam();

    @NonNull CompletionStage<Integer> getPlayerMove(@NonNull Connect4State currentState);

    void dispose();

    interface Factory {
        @NonNull Player create(@NonNull Team team, @NonNull Connect4OverlayController controller);
    }
}
