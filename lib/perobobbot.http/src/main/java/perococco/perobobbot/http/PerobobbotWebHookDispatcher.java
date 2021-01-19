package perococco.perobobbot.http;

import com.google.common.collect.ImmutableMap;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Synchronized;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookDispatcher;
import perobobbot.http.WebHookListener;
import perobobbot.http.WebHookSubscription;
import perobobbot.lang.Exec;
import perobobbot.lang.MapTool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@RequiredArgsConstructor
public class PerobobbotWebHookDispatcher implements WebHookDispatcher {

    private final @NonNull URI webhookURI;
    private final @NonNull URI oauthURI;

    private ImmutableMap<String, WebHookListener> listeners = ImmutableMap.of();

    @Override
    public void dispatch(@NonNull String path, @NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException {
        Exec.with(path.trim())
            .map(this::removeTrailingSlash)
            .apply(p -> listeners.getOrDefault(p,NOT_FOUND))
            .onCall(path,method,request,response);
    }

    @Override
    @Synchronized
    public @NonNull WebHookSubscription addListener(@NonNull String path, @NonNull WebHookListener listener) {
        return Exec.with(path.trim())
                   .map(this::removeTrailingSlash)
                   .checkIsNot(String::isEmpty, p -> new IllegalArgumentException("Invalid webhook path. Path='" + p + "'"))
                   .checkNotIn(listeners.keySet(), p -> new IllegalArgumentException("Webhook exists for this path already. Path='" + p + "'"))
                   .apply(p -> {
                       final var webHookCallbackURI = webhookURI.resolve(p);
                       final var oauthCallbackURI = oauthURI.resolve(p);
                       this.listeners = MapTool.add(this.listeners, p, listener);
                       return new WebHookSubscription(webHookCallbackURI,oauthCallbackURI, () -> remove(p));
                   });
    }

    @Synchronized
    private void remove(@NonNull String path) {
        this.listeners = MapTool.remove(this.listeners, path);
    }

    private @NonNull String removeTrailingSlash(@NonNull String path) {
        if (path.endsWith("/")) {
            return path.substring(0, path.length() - 1);
        }
        return path;
    }

    private static final WebHookListener NOT_FOUND = (path, method, request, response) -> response.sendError(HttpStatus.NOT_FOUND.value());
}
