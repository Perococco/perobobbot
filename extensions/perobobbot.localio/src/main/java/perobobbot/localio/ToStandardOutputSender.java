package perobobbot.localio;

import lombok.NonNull;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.fp.Function1;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class ToStandardOutputSender implements LocalSender {

    @Override
    public @NonNull CompletionStage<? extends DispatchSlip> send(@NonNull ChatConnectionInfo chatConnectionInfo,
                                                                 @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final String message = messageBuilder.apply((DispatchContext) Instant::now);
        System.out.format("%-15s : %s%n",chatConnectionInfo.getBotName(),message);
        return CompletableFuture.completedFuture(DispatchSlip.with(LocalChat.CONSOLE_CHANNEL_INFO, Instant.now()));

    }
}
