package perobobbot.twitch.eventsub.manager.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.MessageGateway;
import perobobbot.lang.Nil;
import perobobbot.lang.chain.Chain;
import perobobbot.lang.chain.Link;
import perobobbot.twitch.eventsub.api.EventSubNotification;
import perobobbot.twitch.eventsub.api.EventSubRequest;
import perobobbot.twitch.eventsub.api.TwitchRequestContent;

@RequiredArgsConstructor
public class TwitchRequestContentDispatcher implements Link<TwitchRequestContent<EventSubRequest>, Nil> {

    private final @NonNull MessageGateway messageGateway;

    @Override
    public @NonNull Nil call(@NonNull TwitchRequestContent<EventSubRequest> parameter, @NonNull Chain<TwitchRequestContent<EventSubRequest>, Nil> chain) {
        if (parameter.content() instanceof EventSubNotification notification) {
            messageGateway.sendPlatformNotification(notification.getEvent());
        }
        return Nil.NIL;
    }
}
