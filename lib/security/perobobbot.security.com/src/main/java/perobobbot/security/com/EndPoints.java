package perobobbot.security.com;

import lombok.NonNull;

/**
 * @author perococco
 */
public interface EndPoints {

    String CONTEXT_PATH = "/api";

    String CURRENT_USER = "/user";
    String SIGN_IN = "/signin";
    String OAUTH = "/oauth";
    String SIGN_UP = "/signup";
    String CHANGE_PASSWORD = "/password";

    @NonNull
    static String fullPath(@NonNull String path) {
        if (path.startsWith("/")) {
            return CONTEXT_PATH+path;
        } else {
            return CONTEXT_PATH+"/"+path;
        }
    }
}
