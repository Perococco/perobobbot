package perococco.perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.AdvancedChat;
import perobobbot.chat.advanced.DispatchSlip;
import perobobbot.chat.advanced.ReceiptSlip;
import perobobbot.chat.core.IO;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class TwitchIOWithAdvancedChat implements TwitchIO {

    @NonNull
    private final AdvancedChat<MessageFromTwitch> advancedChat;

    /**
     * send a command to the chat, a command does not expect an answer
     * @param command the command to send
     * @return a {@link CompletionStage} that completes when the command has been sent
     */
    @Override
    public @NonNull CompletionStage<DispatchSlip> sendToChat(@NonNull CommandToTwitch command) {
        return advancedChat.sendCommand(command)
                           .thenApply(d -> buildTwitchDispatchSlip(command,d));
    }

    /**
     * send a request to the chat, a request expects an answer
     * @param request the request to send
     * @param <A> the type of the expected answer
     * @return a {@link CompletionStage} that completes when the answer is received
     */
    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A>> sendToChat(@NonNull RequestToTwitch<A> request) {
        return advancedChat.sendRequest(request)
                           .thenApply(r -> buildTwitchReceiptSlip(request, r));
    }

    /**
     * @param duration the duration to use for the timeout used when performing the request-answer matching
     */
    @Override
    public void timeout(Duration duration) {
        advancedChat.setTimeout(duration);
    }

    private @NonNull DispatchSlip buildTwitchDispatchSlip(@NonNull CommandToTwitch command, @NonNull perobobbot.chat.advanced.DispatchSlip dispatchSlip) {
        return TwitchIO.DispatchSlip.builder()
                                    .io(this)
                                    .dispatchingTime(dispatchSlip.getDispatchingTime())
                                    .sentCommand(command)
                                    .build();
    }

    private @NonNull <A> TwitchIO.ReceiptSlip<A> buildTwitchReceiptSlip(@NonNull RequestToTwitch<A> request,
                                                                        @NonNull perobobbot.chat.advanced.ReceiptSlip<A> receiptSlip) {
        return TwitchIO.ReceiptSlip.<A>builder()
                .io(this)
                .dispatchingTime(receiptSlip.getDispatchingTime())
                .receptionTime(receiptSlip.getReceptionTime())
                .sentRequest(request)
                .answer(receiptSlip.getAnswer())
                .build();
    }
}
