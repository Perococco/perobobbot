package perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import perobobbot.chat.advanced.DispatchContext;
import perobobbot.common.lang.User;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Function1;
import perobobbot.program.core.ExecutionContext;
import perobobbot.twitch.chat.event.ReceivedMessage;
import perobobbot.twitch.chat.message.from.PrivMsgFromTwitch;

import java.time.Instant;

public class TwitchExecutionContext implements ExecutionContext {

    @NonNull
    public static Function1<ReceivedMessage<PrivMsgFromTwitch>, ExecutionContext> factoryFromPrivateMessage(@NonNull TwitchChatIO twitchChatIO) {
        return r -> new TwitchExecutionContext(twitchChatIO,r);
    }

    @NonNull
    public static PrivMsgFromTwitchListener toListener(@NonNull TwitchChatIO twitchChatIO, @NonNull Consumer1<ExecutionContext> consumer) {
        final Function1<ReceivedMessage<PrivMsgFromTwitch>, ExecutionContext> converter = factoryFromPrivateMessage(twitchChatIO);
        return reception -> consumer.accept(converter.f(reception));
    }

    @NonNull
    private final TwitchChatIO twitchChatIO;

    @NonNull
    private final ReceivedMessage<PrivMsgFromTwitch> reception;


    @Getter
    private final User executingUser;

    public TwitchExecutionContext(@NonNull TwitchChatIO twitchChatIO, @NonNull ReceivedMessage<PrivMsgFromTwitch> reception) {
        this.twitchChatIO = twitchChatIO;
        this.reception = reception;
        this.executingUser = TwitchUser.createFromPrivateMessage(reception.getMessage());
    }

    @Override
    public @NonNull Instant getReceptionTime() {
        return reception.getReceptionTime();
    }

    @Override
    public @NonNull String getRawPayload() {
        return reception.getRawMessage();
    }

    @Override
    public @NonNull String getMessage() {
        return reception.getMessage().getPayload();
    }

    @Override
    public void print(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        twitchChatIO.message(reception.getMessage().getChannel(), messageBuilder);
    }
}
