package perococco.perobobbot.twitch.chat;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.chat.core.MessageChannelIO;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.MessageListener;
import perobobbot.lang.Subscription;
import perobobbot.lang.fp.Function1;
import perobobbot.twitch.chat.Channel;
import perobobbot.twitch.chat.TwitchDispatchSlip;
import perobobbot.twitch.chat.TwitchReceiptSlip;
import perobobbot.twitch.chat.message.to.PrivMsg;
import perococco.perobobbot.twitch.chat.state.mutator.OperatorToSendCommand;

import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class TwitchMessageChannelIO implements MessageChannelIO {

    @Getter
    private final @NonNull Channel channel;

    private final @NonNull TwitchChatConnection identity;

    @Override
    public CompletionStage<? extends DispatchSlip> send(@NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final PrivMsg privMsg = new PrivMsg(channel,messageBuilder);
        return identity.operate(OperatorToSendCommand.create(privMsg))
                                 .thenApply(this::convertSlip);
    }

    @Override
    public @NonNull Subscription addMessageListener(@NonNull MessageListener listener) {
        return identity.addMessageListener(listener);
    }


    @NonNull
    private TwitchDispatchSlip convertSlip(@NonNull TwitchIO.DispatchSlip slip) {
        return new TwitchDispatchSlip(this, slip.getSentCommand(), slip.getDispatchingTime());
    }

    @NonNull
    private <A> TwitchReceiptSlip<A> convertSlip(@NonNull TwitchIO.ReceiptSlip<A> slip) {
        return TwitchReceiptSlip.<A>builder()
                .twitchChatIO(this)
                .dispatchingTime(slip.getDispatchingTime())
                .receptionTime(slip.getReceptionTime())
                .sentRequest(slip.getSentRequest())
                .answer(slip.getAnswer())
                .build();
    }


}
