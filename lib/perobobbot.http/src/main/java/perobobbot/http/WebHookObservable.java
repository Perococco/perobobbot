package perobobbot.http;

import lombok.NonNull;

public interface WebHookObservable {

    @NonNull WebHookSubscription addListener(@NonNull String path, @NonNull WebHookListener listener);

}
