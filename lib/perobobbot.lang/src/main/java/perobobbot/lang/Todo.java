package perobobbot.lang;

import lombok.NonNull;

public class Todo {

    public static <T> T TODO() {
        throw new PerobobbotException("Not implemented yet");
    }

    public static <T> T TODO(@NonNull Class<T> returnType) {
        throw new PerobobbotException("Not implemented yet");
    }
}
