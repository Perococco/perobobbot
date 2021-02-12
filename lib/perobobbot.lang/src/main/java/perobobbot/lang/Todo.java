package perobobbot.lang;

import lombok.NonNull;
import perobobbot.lang.fp.Consumer1;

public class Todo {

    public static <T> T TODO() {
        throw new PerobobbotException("Not implemented yet");
    }

    public static <T> T TODO(@NonNull Class<T> returnType) {
        throw new PerobobbotException("Not implemented yet");
    }

    public static <T> T TO_REMOVE() {
        throw new PerobobbotException("This must be removed");
    }


}
