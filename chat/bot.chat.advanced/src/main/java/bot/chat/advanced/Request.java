package bot.chat.advanced;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface Request<A> extends Message {

    @NonNull
    Class<A> answerType();

}
