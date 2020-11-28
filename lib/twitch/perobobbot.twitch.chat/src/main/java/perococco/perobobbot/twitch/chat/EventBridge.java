package perococco.perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.event.Error;
import perobobbot.chat.advanced.event.*;
import perobobbot.lang.CastTool;
import perobobbot.lang.Nil;
import perobobbot.lang.fp.Consumer1;
import perobobbot.twitch.chat.TwitchChatState;
import perobobbot.twitch.chat.event.TwitchChatEvent;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.MessageToTwitch;
import perococco.perobobbot.twitch.chat.state.StateUpdater;

import java.util.Optional;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class EventBridge implements AdvancedChatEventVisitor<MessageFromTwitch,Nil> {

    @NonNull
    private final Consumer1<TwitchChatEvent> twitchChatEventConsumer;

    private final StateUpdater stateUpdater;

    @Override
    public @NonNull Nil visit(@NonNull Connection<MessageFromTwitch> event) {
        return handle(event, (s,e) -> new perobobbot.twitch.chat.event.Connection(s));
    }

    @Override
    public @NonNull Nil visit(@NonNull Disconnection<MessageFromTwitch> event) {
        return handle(event, (s,e) -> new perobobbot.twitch.chat.event.Disconnection(s));
    }

    @Override
    public @NonNull Nil visit(@NonNull PostedMessage<MessageFromTwitch> event) {
        return handle(event, (s,e) -> {
            final Optional<MessageToTwitch> message = CastTool.cast(MessageToTwitch.class, event.getPostedMessage());
            return message.map(m -> new perobobbot.twitch.chat.event.PostedMessage(s, event.getDispatchingTime(), m))
                    .orElse(null);
        });
    }

    @Override
    public @NonNull Nil visit(@NonNull ReceivedMessage<MessageFromTwitch> event) {
        return handle(event, (s,e) -> new perobobbot.twitch.chat.event.ReceivedMessage<>(s, e.getReceptionTime(), e.getMessage()));
    }

    @Override
    public @NonNull Nil visit(@NonNull Error<MessageFromTwitch> event) {
        return handle(event,(s,e) -> new perobobbot.twitch.chat.event.Error(s, e.getError()));
    }

    private <E extends AdvancedChatEvent<MessageFromTwitch>> Nil handle(@NonNull E event,
                                                                        @NonNull BiFunction<? super TwitchChatState, ? super E, ? extends TwitchChatEvent> converter) {
        final TwitchChatState state = stateUpdater.updateWith(event);
        final TwitchChatEvent twitchChatEvent = converter.apply(state,event);
        if (twitchChatEvent != null) {
            twitchChatEventConsumer.accept(twitchChatEvent);
        }
        return Nil.NIL;
    }
}
