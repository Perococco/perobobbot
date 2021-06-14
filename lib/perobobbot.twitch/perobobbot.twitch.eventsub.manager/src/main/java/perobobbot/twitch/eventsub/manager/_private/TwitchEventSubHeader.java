package perobobbot.twitch.eventsub.manager._private;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RequiredArgsConstructor
public enum TwitchEventSubHeader {
    TWITCH_EVENTSUB_MESSAGE_ID("Twitch-Eventsub-Message-Id"),
    TWITCH_EVENTSUB_MESSAGE_RETRY("Twitch-Eventsub-Message-Retry"),
    TWITCH_EVENTSUB_MESSAGE_TYPE("Twitch-Eventsub-Message-Type"),
    TWITCH_EVENTSUB_MESSAGE_SIGNATURE("Twitch-Eventsub-Message-Signature"),
    TWITCH_EVENTSUB_MESSAGE_TIMESTAMP("Twitch-Eventsub-Message-Timestamp"),
    TWITCH_EVENTSUB_SUBSCRIPTION_TYPE("Twitch-Eventsub-Subscription-Type"),
    TWITCH_EVENTSUB_SUBSCRIPTION_VERSION("Twitch-Eventsub-Subscription-Version"),
    ;

    private final @NonNull String headerKey;

    public @NonNull Optional<String> getHeader(@NonNull HttpServletRequest response) {
        return Optional.ofNullable(response.getHeader(this.headerKey));
    }

}
