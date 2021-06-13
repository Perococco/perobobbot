package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.NonNull;
import lombok.Value;

@Value
public class ChannelRaidEvent implements EventSubEvent {

    @JsonAlias("form_broadcaster")
    @NonNull UserInfo from;
    @JsonAlias("to_broadcaster")
    @NonNull UserInfo to;
    int viewers;
}
