package perobobbot.http;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.lang.URIResolver;
import perococco.perobobbot.http.DisabledWebHookDispatcher;
import perococco.perobobbot.http.PerobobbotWebHookDispatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public interface WebHookDispatcher extends WebHookManager {

    static @NonNull WebHookDispatcher create(@NonNull URIResolver webhookURIResolver, @NonNull URIResolver oauthURIResolver) {
        return new PerobobbotWebHookDispatcher(webhookURIResolver,oauthURIResolver);
    }

    static @NonNull WebHookDispatcher disabled() {
        return new DisabledWebHookDispatcher();
    }

    void dispatch(@NonNull String path, @NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws ServletException,IOException;
}
