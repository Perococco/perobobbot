package perobobbot.lang;

import lombok.NonNull;

public class Todo {

    public static <T> T TODO() {
        throw new PerobobbotException("Not implemented yet");
    }

    public static <T> T TODO(@NonNull Class<T> returnType) {
        throw new PerobobbotException("Not implemented yet");
    }

    public static <T> T TODO(@NonNull String message) {
        throw new PerobobbotException("Not implemented yet :"+message);
    }

    public static <T> T TO_REMOVE() {
        throw new PerobobbotException("This must be removed");
    }


}
