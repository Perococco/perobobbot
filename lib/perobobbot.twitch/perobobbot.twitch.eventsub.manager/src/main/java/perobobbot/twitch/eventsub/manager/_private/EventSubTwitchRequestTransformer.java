package perobobbot.twitch.eventsub.manager._private;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.twitch.eventsub.api.EventSubRequest;
import perobobbot.twitch.eventsub.api.EventSubRequestHandler;
import perobobbot.twitch.eventsub.api.EventSubVerification;
import perobobbot.twitch.eventsub.api.TwitchRequestContent;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2
public class EventSubTwitchRequestTransformer implements EventSubRequestHandler {

    private static final boolean SAVE_NOTIFICATION = Boolean.getBoolean("twitch.notification.save");

    private final TwitchRequestSaver twitchRequestSaver = new TwitchRequestSaver();

    private final @NonNull ObjectMapper objectMapper;

    @Override
    public @NonNull Optional<TwitchRequestContent<EventSubRequest>> handleCallback(@NonNull HttpServletRequest request,
                                                             @NonNull HttpServletResponse response,
                                                             @NonNull String secret) throws IOException, ServletException {
        return new Executor(request, response, secret).execute();
    }

    @RequiredArgsConstructor
    private class Executor {

        private final @NonNull HttpServletRequest request;
        private final @NonNull HttpServletResponse response;
        private final @NonNull String secret;

        private TwitchRequestContent<byte[]> requestContent;
        private EventSubRequest deserializeContent;

        private @NonNull Optional<TwitchRequestContent<EventSubRequest>> execute() throws ServletException, IOException {
            this.validateRequest();
            if (requestIsNotValid()) {
                this.responseWithBadRequestStatus();
                return Optional.empty();
            }
            this.responseWithOKStatus();
            this.saveBodyContent();
            this.deserializeBodyContent();
            this.handleEvenSubVerification();
            return Optional.ofNullable(deserializeContent).map(requestContent::with);
        }

        private void validateRequest() throws ServletException, IOException {
            requestContent = TwitchRequestValidator.validate(request, secret).orElse(null);
        }

        private boolean requestIsNotValid() {
            return requestContent == null;
        }

        private void responseWithBadRequestStatus() {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

        private void responseWithOKStatus() {
            response.setStatus(HttpServletResponse.SC_OK);
        }

        private void saveBodyContent() {
            if (SAVE_NOTIFICATION) {
                twitchRequestSaver.saveBody(requestContent.content());
            }
        }

        private void deserializeBodyContent() {
            deserializeContent = TwitchRequestDeserializer.deserialize(objectMapper, requestContent.type(),
                                                                       requestContent.content())
                                                          .orElse(null);

        }

        private void handleEvenSubVerification() {
            if (deserializeContent instanceof EventSubVerification verification) {
                LOG.info("Send challenge : {}", verification.getChallenge());
                try {
                    response.getOutputStream().print(verification.getChallenge());
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }

        }

    }
}
