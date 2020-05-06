package perococco.bot.twitch.chat;

import bot.chat.advanced.AdvancedChat;
import bot.twitch.chat.message.from.MessageFromTwitch;
import bot.twitch.chat.message.to.CommandToTwitch;
import bot.twitch.chat.message.to.RequestToTwitch;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class IOWithAdvancedChat implements IO {

    @NonNull
    private final AdvancedChat<MessageFromTwitch> advancedChat;

    @Override
    public @NonNull CompletionStage<DispatchSlip> sendToChat(@NonNull CommandToTwitch command) {
        return advancedChat.sendCommand(command)
                           .thenApply(d -> IO.DispatchSlip.builder()
                                                          .io(this)
                                                          .dispatchingTime(d.dispatchingTime())
                                                          .sentCommand(command)
                                                          .build());
    }

    @Override
    public @NonNull <A> CompletionStage<ReceiptSlip<A>> sendToChat(@NonNull RequestToTwitch<A> request) {
        return advancedChat.sendRequest(request)
                           .thenApply(d -> IO.ReceiptSlip.<A>builder()
                                   .io(this)
                                   .dispatchingTime(d.dispatchingTime())
                                   .receptionTime(d.receptionTime())
                                   .sentRequest(request)
                                   .answer(d.answer())
                                   .build()
                           );
    }

    @Override
    public void timeout(Duration duration) {
        advancedChat.timeout(duration);
    }
}
