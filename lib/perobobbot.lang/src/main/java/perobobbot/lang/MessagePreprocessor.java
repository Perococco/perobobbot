package perobobbot.lang;

import lombok.NonNull;

/**
 * Preprocess message before handling it.
 */
public interface MessagePreprocessor {

    default int priority() {
        return 100;
    }

    /**
     * @param messageContext the incoming message
     * @return the preprocessed message
     */
    MessageContext processMessage(@NonNull MessageContext messageContext);
}
