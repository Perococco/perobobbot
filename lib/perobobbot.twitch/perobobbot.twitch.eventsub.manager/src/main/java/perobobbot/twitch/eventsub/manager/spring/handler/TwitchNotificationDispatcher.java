package perobobbot.twitch.eventsub.manager.spring.handler;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MessageGateway;
import perobobbot.lang.Nil;
import perobobbot.lang.Handler;
import perobobbot.twitch.eventsub.api.EventSubNotification;

import java.util.Optional;

@RequiredArgsConstructor
public class TwitchNotificationDispatcher implements Handler<EventSubNotification, Nil> {

    private final @NonNull MessageGateway messageGateway;

    @Override
    public @NonNull Optional<Nil> handle(@NonNull EventSubNotification input) {
        messageGateway.sendPlatformNotification(input.getEvent());
        return Optional.empty();
    }
}
