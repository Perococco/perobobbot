package perobobbot.data.com.event;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import perobobbot.lang.ApplicationEvent;

@RequiredArgsConstructor
@ToString
public class ExtensionEvent implements ApplicationEvent {

    public static @NonNull ExtensionEvent create(@NonNull String data) {
        return new ExtensionEvent(data);
    }

    private final @NonNull String data;

}
