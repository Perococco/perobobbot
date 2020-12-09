package perococco.perobobbot.chat.core;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.chat.core.ChatPlatform;
import perobobbot.chat.core.DispatchSlip;
import perobobbot.chat.core.IO;
import perobobbot.chat.core.UnknownChatPlatform;
import perobobbot.lang.ChannelInfo;
import perobobbot.lang.DispatchContext;
import perobobbot.lang.Platform;
import perobobbot.lang.fp.Function1;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
@Log4j2
public class WithMapIO implements IO {

    @NonNull
    private final ImmutableMap<Platform, ChatPlatform> ioByPlatform;

    @Override
    public CompletionStage<? extends DispatchSlip> send(@NonNull String nick,
                                                        @NonNull ChannelInfo channelInfo,
                                                        @NonNull Function1<? super DispatchContext, ? extends String> messageBuilder) {
        final String channelName = channelInfo.getChannelName();
        final Platform platform = channelInfo.getPlatform();

        final ChatPlatform chatPlatform = ioByPlatform.get(platform);

        if (chatPlatform != null) {
            return chatPlatform.getChannelIO(nick, channelName)
                               .thenCompose(c -> c.send(messageBuilder));
        } else {
            LOG.warn("No IO for platform {}", channelInfo.getPlatform());
            return CompletableFuture.failedFuture(new UnknownChatPlatform(platform));
        }
    }

    @Override
    public Optional<ChatPlatform> findPlatform(@NonNull Platform platform) {
        return Optional.ofNullable(ioByPlatform.get(platform));
    }

}
