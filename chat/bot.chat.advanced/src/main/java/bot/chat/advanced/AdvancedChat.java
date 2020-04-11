package bot.chat.advanced;

import bot.common.lang.Nil;
import bot.common.lang.Subscription;
import lombok.NonNull;

import java.util.concurrent.CompletionStage;

/**
 * @author perococco
 **/
public interface AdvancedChat {

    @NonNull
    CompletionStage<Nil> sendCommand(@NonNull Command command);

    @NonNull
    <A> CompletionStage<A> sendRequest(@NonNull Request<A> request);

    @NonNull
    Subscription addChatListener(@NonNull AdvancedChatListener listener);
}
