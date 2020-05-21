package perobobbot.server;

import lombok.NonNull;

/**
 * @author Perococco
 */
public interface EndPoints {

    String CONTEXT_PATH = "/api";

    String CURRENT_USER = "/user";
    String LOGIN = "/login";
    String SIGN_UP = "/signup";

    @NonNull
    static String fullPath(@NonNull String path) {
        if (path.startsWith("/")) {
            return CONTEXT_PATH+path;
        } else {
            return CONTEXT_PATH+"/"+path;
        }
    }
}
