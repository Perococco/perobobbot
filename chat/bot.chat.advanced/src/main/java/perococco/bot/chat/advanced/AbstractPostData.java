package perococco.bot.chat.advanced;

import bot.chat.advanced.Message;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
@RequiredArgsConstructor
public abstract class AbstractPostData<A> implements PostData<A> {

    @NonNull
    private final Message message;

    @NonNull
    private final CompletableFuture<A> completableFuture = new CompletableFuture<>();

    @Override
    public @NonNull Message message() {
        return message;
    }

    @Override
    public @NonNull CompletionStage<A> completionStage() {
        return completableFuture;
    }

    protected void completeWith(@NonNull A value) {
        completableFuture.complete(value);
    }

    protected void completeExceptionallyWith(@NonNull Throwable error) {
        completableFuture.completeExceptionally(error);
    }

    @Override
    public void onMessagePostFailure(@NonNull Throwable t) {
        completableFuture.completeExceptionally(t);
    }

    @Override
    public @NonNull String messagePayload() {
        return message.payload();
    }

    public boolean isCompleted() {
        return completableFuture.isDone();
    }

}
