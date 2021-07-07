package perobobbot.twitch.client.api;

import perobobbot.twitch.client.api.evensub.TwitchServiceEventSub;
import perobobbot.twitch.client.api.evensub.TwitchServiceEventSubWithToken;
import perobobbot.twitch.client.api.games.TwitchServiceGames;
import perobobbot.twitch.client.api.games.TwitchServiceGamesWithToken;

public interface TwitchServiceWithToken extends
        TwitchServiceEventSubWithToken,
        TwitchServiceGamesWithToken {


}
