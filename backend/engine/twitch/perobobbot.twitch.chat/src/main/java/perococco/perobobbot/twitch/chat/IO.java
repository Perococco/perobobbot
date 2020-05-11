package perococco.perobobbot.twitch.chat;

import perobobbot.twitch.chat.message.to.CommandToTwitch;
import perobobbot.twitch.chat.message.to.RequestToTwitch;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import perococco.perobobbot.twitch.chat.actions.IOAction;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.CompletionStage;

public interface IO {

    @NonNull CompletionStage<IO.DispatchSlip> sendToChat(@NonNull CommandToTwitch command);

    @NonNull <A> CompletionStage<IO.ReceiptSlip<A>> sendToChat(@NonNull RequestToTwitch<A> request);

    void timeout(Duration duration);


    @NonNull
    default <T> CompletionStage<T> execute(@NonNull IOAction<T> action) {
        return action.evaluate(this);
    }


    @RequiredArgsConstructor
    @Builder
    class DispatchSlip implements IO {

        @NonNull
        @Delegate
        private final IO io;

        @NonNull
        @Getter
        private final CommandToTwitch sentCommand;

        @NonNull
        @Getter
        private final Instant dispatchingTime;

    }

    @RequiredArgsConstructor
    @Builder
    class ReceiptSlip<A> implements IO {

        @NonNull
        @Delegate
        private final IO io;

        @NonNull
        @Getter
        private final Instant dispatchingTime;

        @NonNull
        @Getter
        private final Instant receptionTime;

        @NonNull
        @Getter
        private final RequestToTwitch<A> sentRequest;

        @NonNull
        @Getter
        private final A answer;
    }


}
