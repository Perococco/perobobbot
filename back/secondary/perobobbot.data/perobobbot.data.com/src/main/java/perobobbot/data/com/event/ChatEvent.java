package perobobbot.data.com.event;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.Event;
import perobobbot.lang.Platform;

@RequiredArgsConstructor
public class ChatEvent implements Event {

    @Getter
    private final @NonNull Platform platform;
}
