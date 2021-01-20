package perobobbot.http;

import lombok.NonNull;

public interface WebHookObservable {

    boolean isDisabled();

    @NonNull WebHookSubscription addListener(@NonNull String path, @NonNull WebHookListener listener);

}
