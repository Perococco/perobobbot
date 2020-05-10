package perococco.bot.twitch.chat.actions;

import bot.twitch.chat.message.to.Ping;
import lombok.NonNull;
import perococco.bot.twitch.chat.IO;

import java.time.Duration;
import java.util.concurrent.CompletionStage;

public class SetupTimeout implements IOAction<Void> {

    @NonNull
    public static SetupTimeout create() {
        return Holder.INSTANCE;
    }

    /**
     * Twitch handle command by batch every 10 seconds. We need
     * to take this into account to compute the timeout
     */
    public static final Duration _10_SECONDS = Duration.ofSeconds(10);

    private SetupTimeout() {}

    @NonNull
    public CompletionStage<Void> evaluate(@NonNull IO io) {
        return io.sendToChat(new Ping())
                 .thenApply(this::evaluateTimeout)
                 .thenAccept(io::timeout);
    }

    private Duration evaluateTimeout(@NonNull IO.ReceiptSlip<?> receiptSlip) {
        final Duration dialogDuration = Duration.between(receiptSlip.getDispatchingTime(), receiptSlip.getReceptionTime());
        return dialogDuration.multipliedBy(2).plus(Duration.ofSeconds(10));
    }

    private static class Holder {
        public static final SetupTimeout INSTANCE = new SetupTimeout();
    }
}
