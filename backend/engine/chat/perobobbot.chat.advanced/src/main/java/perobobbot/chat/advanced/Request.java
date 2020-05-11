package perobobbot.chat.advanced;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface Request<A> extends Message {

    @NonNull
    Class<A> getAnswerType();
}
