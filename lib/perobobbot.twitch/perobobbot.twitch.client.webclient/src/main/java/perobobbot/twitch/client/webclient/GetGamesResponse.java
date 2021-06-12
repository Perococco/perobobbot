package perobobbot.twitch.client.webclient;

import lombok.NonNull;
import lombok.Value;
import perobobbot.twitch.client.api.Game;

@Value
public class GetGamesResponse {

    @NonNull Game[] data;
}
