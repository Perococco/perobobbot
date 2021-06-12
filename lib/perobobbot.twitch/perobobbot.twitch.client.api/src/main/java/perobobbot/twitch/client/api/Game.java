package perobobbot.twitch.client.api;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

@Value
public class Game {

    @NonNull String id;
    @NonNull String name;
    @JsonAlias("box_art_url")
    @NonNull String boxArtUrl;
}
