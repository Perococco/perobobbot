package perobobbot.server.sse.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import perobobbot.server.config.RestObjectMapper;
import perobobbot.server.sse.MessageToSSEventTransformer;
import perobobbot.server.sse.SSEvent;

import java.io.UncheckedIOException;

@Component
@RequiredArgsConstructor
@Log4j2
public class MessageToSSEventSerializer implements MessageToSSEventTransformer {

    private final @NonNull RestObjectMapper mapper;

    @Override
    public @NonNull SSEvent transform(@NonNull Object event) {
        try {
            return new SSEvent(getEventName(event), createPayload(event));
        } catch (JsonProcessingException j) {
            throw new UncheckedIOException(j);
        }
    }

    private @NonNull String createPayload(@NonNull Object event) throws JsonProcessingException {
        return mapper.writeValueAsString(event);
    }

    private @NonNull String getEventName(@NonNull Object event) {
        return "message";
    }
}
