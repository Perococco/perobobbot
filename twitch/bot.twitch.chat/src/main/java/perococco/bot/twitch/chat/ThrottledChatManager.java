package perococco.bot.twitch.chat;

import bot.chat.core.ChatListener;
import bot.chat.core.ChatManager;
import bot.chat.core.MessagePostingFailure;
import bot.common.lang.Subscription;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;

import java.util.concurrent.atomic.AtomicReference;

@Log4j2
public class ThrottledChatManager implements ChatManager {

    @NonNull
    private final ChatManager delegate;

    @NonNull
    private final Bucket bucket;

    private final AtomicReference<TwitchBandwidth> bandwidthType = new AtomicReference<>(TwitchBandwidth.REGULAR);

    public ThrottledChatManager(@NonNull ChatManager delegate) {
        this.delegate = delegate;
        this.bucket = bandwidthType.get().addLimits(Bucket4j.builder()).build();
    }

    @Synchronized
    public void updateBandwidthType(@NonNull TwitchBandwidth bandwidthType) {
        final TwitchBandwidth oldType = this.bandwidthType.getAndSet(bandwidthType);
        if (oldType == bandwidthType) {
            return;
        }
        bucket.replaceConfiguration(
                bandwidthType.addLimits(Bucket4j.configurationBuilder()).build()
        );
    }

    @Override
    public void start() {
        delegate.start();
    }

    @Override
    public boolean isRunning() {
        return delegate.isRunning();
    }

    @Override
    public void requestStop() {
        delegate.requestStop();
    }

    @Override
    public void postMessage(@NonNull String message) {
        try {
            bucket.asScheduler().consume(1);
            try {
                delegate.postMessage(message);
                LOG.debug("Throttling : {} tokens left",bucket.getAvailableTokens());
            } catch (Exception e) {
                bucket.addTokens(1);
                throw e;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new MessagePostingFailure("Post has been interrupted",e);
        }

    }

    @Override
    public @NonNull Subscription addChatListener(@NonNull ChatListener listener) {
        return delegate.addChatListener(listener);
    }
}
