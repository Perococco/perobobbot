package perococco.perobobbot.http;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMethod;
import perobobbot.http.WebHookDispatcher;
import perobobbot.http.WebHookListener;
import perobobbot.http.WebHookSubscription;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Optional;

@Log4j2
public class DisabledWebHookDispatcher implements WebHookDispatcher {

    private static final URI BLACK_HOLE_URI = URI.create("https://localhost:9");

    @Override
    public void dispatch(@NonNull String path, @NonNull RequestMethod method, @NonNull HttpServletRequest request, @NonNull HttpServletResponse response) {}

    @Override
    public boolean isDisabled() {
        return true;
    }

    @Override
    public @NonNull Optional<WebHookSubscription> addListener(@NonNull String path, @NonNull WebHookListener listener) {
        LOG.error("Webhook is not available. Check your configuration. Listener to {} will never receive any notification",path);
        return Optional.empty();
    }
}
