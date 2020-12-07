package perococco.perobobbot.twitch.chat;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.chat.advanced.AdvancedChat;
import perobobbot.chat.core.IO;
import perobobbot.twitch.chat.message.from.MessageFromTwitch;
import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class IOWithAdvancedChat implements TwitchIO {

    @NonNull
    private final AdvancedChat<MessageFromTwitch> advancedChat;

    @Override
    public @NonNull CompletionStage<DispatchSlip> sendToChat(@NonNull CommandToTwitch command) {
        return advancedChat.sendCommand(command)
                           .thenApply(d -> TwitchIO.DispatchSlip.builder()
                                                                .io(this)
                                                                .dispatchingTime(d.getDispatchingTime())
                                                                .sentCommand(command)
                                                                .build());
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A>> sendToChat(@NonNull RequestToTwitch<A> request) {
        return advancedChat.sendRequest(request)
                           .thenApply(d -> TwitchIO.ReceiptSlip.<A>builder()
                                   .io(this)
                                   .dispatchingTime(d.getDispatchingTime())
                                   .receptionTime(d.getReceptionTime())
                                   .sentRequest(request)
                                   .answer(d.getAnswer())
                                   .build()
                           );
    }

    @Override
    public void timeout(Duration duration) {
        advancedChat.setTimeout(duration);
    }
}
