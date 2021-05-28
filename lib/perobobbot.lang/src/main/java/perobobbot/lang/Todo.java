package perobobbot.lang;

import lombok.NonNull;

public class Todo {

    public static <T> T TODO() {
        return _TODO("Not implemented yet");
    }

    public static <T> T TODO(@NonNull Class<T> returnType) {
        return _TODO("Not implemented yet");
    }

    public static <T> T TODO(@NonNull String message) {
        return _TODO("Not implemented yet :"+message);
    }

     private static  <T> T _TODO(@NonNull String message) {
        throw new PerobobbotException(message);
    }

    public static <T> T TO_REMOVE() {
        throw new PerobobbotException("This must be removed");
    }


}
