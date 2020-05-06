package perococco.bot.twitch.chat;

import bot.chat.advanced.event.Connection;
import bot.chat.advanced.event.Disconnection;
import bot.chat.advanced.event.Error;
import bot.chat.advanced.event.PostedMessage;
import bot.chat.advanced.event.ReceivedMessage;
import bot.chat.advanced.event.*;
import bot.common.lang.CastTool;
import bot.common.lang.Nil;
import bot.common.lang.fp.Consumer1;
import bot.common.lang.fp.Function2;
import bot.twitch.chat.TwitchChat;
import bot.twitch.chat.TwitchChatState;
import bot.twitch.chat.event.TwitchChatEvent;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.MessageToTwitch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.twitch.chat.state.StateUpdater;

import java.time.Instant;
import java.util.Optional;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class EventBridge implements AdvancedChatEventVisitor<MessageFromTwitch,Nil> {

    @NonNull
    private final Consumer1<TwitchChatEvent> twitchChatEventConsumer;

    private final StateUpdater stateUpdater;

    @Override
    public @NonNull Nil visit(@NonNull Connection<MessageFromTwitch> event) {
        return handle(event, (s,e) -> new bot.twitch.chat.event.Connection(s));
    }

    @Override
    public @NonNull Nil visit(@NonNull Disconnection<MessageFromTwitch> event) {
        return handle(event, (s,e) -> new bot.twitch.chat.event.Disconnection(s));
    }

    @Override
    public @NonNull Nil visit(@NonNull PostedMessage<MessageFromTwitch> event) {
        return handle(event, (s,e) -> {
            final Optional<MessageToTwitch> message = CastTool.cast(MessageToTwitch.class, event.postedMessage());
            return message.map(m -> new bot.twitch.chat.event.PostedMessage(s,event.dispatchingTime(),m))
                    .orElse(null);
        });
    }

    @Override
    public @NonNull Nil visit(@NonNull ReceivedMessage<MessageFromTwitch> event) {
        return handle(event, (s,e) -> new bot.twitch.chat.event.ReceivedMessage<>(s,e.receptionTime(),e.message()));
    }

    @Override
    public @NonNull Nil visit(@NonNull Error<MessageFromTwitch> event) {
        return handle(event,(s,e) -> new bot.twitch.chat.event.Error(s,e.error()));
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
