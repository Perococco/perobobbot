package perobobbot.data.service.event;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import perobobbot.lang.Event;

@RequiredArgsConstructor
@ToString
public class ExtensionEvent implements Event {

    public static @NonNull ExtensionEvent create(@NonNull String data) {
        return new ExtensionEvent(data);
    }

    private final @NonNull String data;

}
