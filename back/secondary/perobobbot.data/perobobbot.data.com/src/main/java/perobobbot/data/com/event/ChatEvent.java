package perobobbot.data.com.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ApplicationEvent;
import perobobbot.lang.Platform;

@RequiredArgsConstructor
public class ChatEvent implements ApplicationEvent {

    @Getter
    private final @NonNull Platform platform;
}
