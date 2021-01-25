package perococco.perobobbot.chat.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.chat.core.DisposableIO;
import perobobbot.chat.core.UnknownChatPlatform;
import perobobbot.lang.ChatConnectionInfo;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Function1;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Log4j2
public class WithMapIO implements DisposableIO {

    @NonNull
    private final ImmutableMap<Platform, ChatPlatform> ioByPlatform;

    @Override
    public void dispose() {
        ioByPlatform.forEach((p, c) -> c.dispose());
    }

    @Override
    public @NonNull CompletionStage<? extends DispatchSlip> send(@NonNull ChatConnectionInfo chatConnectionInfo, @NonNull String channelName, @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final Platform platform = chatConnectionInfo.getPlatform();

        final ChatPlatform chatPlatform = ioByPlatform.get(platform);

        if (chatPlatform != null) {
            return chatPlatform.getChannelIO(chatConnectionInfo, channelName)
                               .thenCompose(c -> c.send(messageBuilder))
                               .whenComplete((dispatchSlip, error) -> {
                                   if (error != null) {
                                       displaySendingError(platform, channelName, error);
                                   }
                               });
        } else {
            LOG.warn("No IO for platform {}", chatConnectionInfo.getPlatform());
            return CompletableFuture.failedFuture(new UnknownChatPlatform(platform));
        }
    }

    private void displaySendingError(@NonNull Platform platform, @NonNull String channelName, @NonNull Throwable error) {
        LOG.error("Could not send message to channel {} on platform {}", channelName, platform, error);
    }

    @Override
    public Optional<ChatPlatform> findPlatform(@NonNull Platform platform) {
        return Optional.ofNullable(ioByPlatform.get(platform));
    }

}
