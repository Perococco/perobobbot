package perococco.perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchDispatchSlip;
import perobobbot.twitch.chat.message.to.PrivMsg;
import perococco.perobobbot.twitch.chat.state.mutator.OperatorToSendCommand;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class TwitchMessageChannelIO implements MessageChannelIO {

    @Getter
    private final @NonNull Channel channel;

    private final @NonNull TwitchChatConnection identity;

    private final @NonNull ChannelInfo channelInfo;

    @Override
    public CompletionStage<? extends DispatchSlip> send(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final PrivMsg privMsg = new PrivMsg(channel,messageBuilder);
        return identity.operate(OperatorToSendCommand.create(privMsg))
                                 .thenApply(this::convertSlip);
    }

    @NonNull
    private TwitchDispatchSlip convertSlip(@NonNull TwitchIO.DispatchSlip slip) {
        return new TwitchDispatchSlip(new ChannelInfo(Platform.TWITCH,channel.getName()), slip.getSentCommand(), slip.getDispatchingTime());
    }

}
