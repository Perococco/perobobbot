package perobobbot.http;

import lombok.NonNull;

public interface WebHookManager {

    int VERSION = 1;

    /**
     * @return true if this webhook is disabled
     */
    boolean isDisabled();

    /**
     * @param path the path of the URL the external server should call. The host is provided by this manager.
     * @param listener a listener the request from the server will be dispatched to.
     * @return a subscription that can be used to remove this subscription. It also contains the complete URL
     * to pass to the external server when subscribing to a webhook to this server
     */
    @NonNull WebHookSubscription addListener(@NonNull String path, @NonNull WebHookListener listener);

}
