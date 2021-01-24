package perobobbot.http;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMethod;
import perococco.perobobbot.http.DisabledWebHookDispatcher;
import perococco.perobobbot.http.PerobobbotWebHookDispatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

public interface WebHookDispatcher extends WebHookObservable {

    static @NonNull WebHookDispatcher create(@NonNull URI webhookURI,@NonNull URI oauthURI) {
        return new PerobobbotWebHookDispatcher(webhookURI,oauthURI);
    }

    static @NonNull WebHookDispatcher disabled() {
        return new DisabledWebHookDispatcher();
    }

    void dispatch(@NonNull String path, @NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException;
}