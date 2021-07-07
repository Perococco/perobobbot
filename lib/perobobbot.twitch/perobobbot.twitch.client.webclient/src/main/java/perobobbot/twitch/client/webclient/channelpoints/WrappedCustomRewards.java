package perobobbot.twitch.client.webclient.channelpoints;

import lombok.NonNull;
import lombok.Value;
import org.springframework.web.bind.annotation.GetMapping;
import perobobbot.twitch.api.Pagination;
import perobobbot.twitch.client.api.channelpoints.CustomReward;
import perobobbot.twitch.client.api.games.Game;

import java.util.Optional;

@Value
public class WrappedCustomRewards {

    @NonNull CustomReward[] data;

}
