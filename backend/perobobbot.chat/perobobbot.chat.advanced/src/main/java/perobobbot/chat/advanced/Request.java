package perobobbot.chat.advanced;

import lombok.NonNull;

/**
 * @author perococco
 **/
public interface Request<A> extends Message {

    /**
     * @return the type of the answer of this request.
     */
    @NonNull
    Class<A> getAnswerType();
}
