package perococco.perobobbot.chat.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.*;
import perobobbot.lang.*;
import perobobbot.lang.fp.Function1;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Log4j2
public class WithMapIO implements MutableIO {

    @NonNull
    private ImmutableMap<Platform, ChatPlatform> ioByPlatform = ImmutableMap.of();

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

    @Override
    @Synchronized
    public @NonNull Optional<Subscription> addPlatform(@NonNull ChatPlatform chatPlatform) {
        var platform = chatPlatform.getPlatform();
        if (this.ioByPlatform.containsKey(platform)) {
            LOG.warn("A chat platform for '{}' is available already",platform);
            return Optional.empty();
        } else {
            this.ioByPlatform = MapTool.add(this.ioByPlatform,platform,chatPlatform);
            return Optional.of(() -> removePlatform(platform));
        }
    }

    @Synchronized
    private void removePlatform(@NonNull Platform platform) {
        this.ioByPlatform = MapTool.remove(this.ioByPlatform,platform);
    }
}
