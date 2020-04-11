package perococco.bot.chat.advanced;

import bot.chat.advanced.Message;
import lombok.NonNull;

import java.util.Optional;
import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface PostData<A> {

    @NonNull
    Message message();

    @NonNull
    CompletionStage<A> completionStage();

    @NonNull
    Optional<RequestPostData<?>> asRequestPostData();

    void onMessagePosted();

    void onMessagePostFailure(@NonNull Throwable t);

    @NonNull
    String messagePayload();

}
