package perobobbot.oauth;

import lombok.NonNull;

public class OAuthContextHolder {

    private static final ThreadLocal<OAuthContext> AUTH_CONTEXTS = ThreadLocal.withInitial(OAuthContext::new);

    public static @NonNull OAuthContext getContext() {
        return AUTH_CONTEXTS.get();
    }

    public static void remove()  {
        AUTH_CONTEXTS.remove();
    }


    private OAuthContextHolder() {
        throw new RuntimeException("Cannot be instantiate");
    }

}
