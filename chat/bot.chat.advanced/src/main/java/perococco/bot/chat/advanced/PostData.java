package perococco.bot.chat.advanced;

import bot.chat.advanced.Message;
import lombok.NonNull;

import java.time.Instant;
import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface PostData<A,M> {

    @NonNull
    Message message();

    @NonNull
    CompletionStage<A> completionStage();

    @NonNull
    Optional<RequestPostData<?,M>> asRequestPostData();

    void onMessagePosted(@NonNull Instant dispatchingTime);

    void onMessagePostFailure(@NonNull Throwable t);

    @NonNull
    String messagePayload();

}
