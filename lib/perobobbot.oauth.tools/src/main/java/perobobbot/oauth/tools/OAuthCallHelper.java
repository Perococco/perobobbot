package perobobbot.oauth.tools;

public interface OAuthCallHelper<T> {

    T call() throws Throwable;
}
