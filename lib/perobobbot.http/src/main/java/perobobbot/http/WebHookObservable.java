package perobobbot.http;

import lombok.NonNull;

public interface WebHookObservable {

    int VERSION = 1;

    /**
     * @return true if this webhook is disabled
     */
    boolean isDisabled();

    /**
     * @param path
     * @param listener
     * @return
     */
    @NonNull WebHookSubscription addListener(@NonNull String path, @NonNull WebHookListener listener);

}
