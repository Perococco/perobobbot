package perobobbot.twitch.client.webclient.games;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.client.api.games.Game;

@Value
public class GetGamesResponse {

    @NonNull Game[] data;
}
