package perococco.bot.twitch.chat.actions;

import bot.common.lang.Nil;
import bot.twitch.chat.Channel;
import bot.twitch.chat.message.to.Join;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perococco.bot.twitch.chat.IO;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@RequiredArgsConstructor
public class JoinChannels implements IOAction<Nil> {

    @NonNull
    private final ImmutableSet<Channel> channels;

    @Override
    public @NonNull CompletionStage<Nil> evaluate(@NonNull IO io) {
        final CompletableFuture<?>[] futures = channels.stream()
                                                       .map(Join::new)
                                                       .map(io::sendToChat)
                                                       .map(c -> c.handle((r,e) -> Nil.NIL))
                                                       .map(CompletionStage::toCompletableFuture)
                                                       .map(f -> (CompletableFuture<?>)f)
                                                       .toArray(CompletableFuture<?>[]::new);

        return CompletableFuture.allOf(futures).thenApply(v -> Nil.NIL);
    }
}
