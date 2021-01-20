package perococco.perobobbot.http;

import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookDispatcher;
import perobobbot.http.WebHookListener;
import perobobbot.http.WebHookSubscription;
import perobobbot.lang.PerobobbotException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DisabledWebHookDispatcher implements WebHookDispatcher {

    @Override
    public void dispatch(@NonNull String path, @NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) throws IOException {}

    @Override
    public boolean isDisabled() {
        return true;
    }

    @Override
    public @NonNull WebHookSubscription addListener(@NonNull String path, @NonNull WebHookListener listener) {
        throw new PerobobbotException("Webhook is not available. Check your configuration");
    }
}
