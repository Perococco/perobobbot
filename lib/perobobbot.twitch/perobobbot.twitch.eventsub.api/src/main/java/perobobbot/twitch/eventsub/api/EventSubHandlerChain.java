package perobobbot.twitch.eventsub.api;

import lombok.NonNull;

public interface EventSubHandlerChain<T> {

    void callNext(@NonNull T parameter);

}
