package bot.twitch.chat.event;

import bot.chat.advanced.event.Connection;
import bot.chat.advanced.event.Disconnection;
import bot.chat.advanced.event.Error;
import bot.chat.advanced.event.PostedMessage;
import bot.chat.advanced.event.ReceivedMessages;
import bot.chat.advanced.event.*;
import bot.common.lang.CastTool;
import bot.common.lang.Listeners;
import bot.twitch.chat.TwitchChatListener;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.MessageToTwitch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class EventBridge implements AdvancedChatEventVisitor<MessageFromTwitch> {

    @NonNull
    private final Listeners<TwitchChatListener> listeners;

    private void warnListeners(@NonNull TwitchChatEvent event) {
        listeners.warnListeners(TwitchChatListener::onTwitchChatEvent, event);
    }

    @Override
    public void visit(@NonNull Connection<MessageFromTwitch> event) {
        warnListeners(bot.twitch.chat.event.Connection.create());
    }

    @Override
    public void visit(@NonNull Disconnection<MessageFromTwitch> event) {
        warnListeners(bot.twitch.chat.event.Disconnection.create());
    }

    @Override
    public void visit(@NonNull PostedMessage<MessageFromTwitch> event) {
        final Optional<MessageToTwitch> message = CastTool.cast(MessageToTwitch.class, event.postedMessage());
        message.map(m -> new bot.twitch.chat.event.PostedMessage(event.dispatchingTime(), m))
               .ifPresent(this::warnListeners);

    }

    @Override
    public void visit(@NonNull ReceivedMessages<MessageFromTwitch> event) {
        warnListeners(new bot.twitch.chat.event.ReceivedMessages(event.receptionTime(),event.messages()));
    }

    @Override
    public void visit(@NonNull Error<MessageFromTwitch> event) {
        warnListeners(new bot.twitch.chat.event.Error(event.error()));
    }
}
