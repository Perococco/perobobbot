package perobobbot.server.sse.transformer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.UncheckedIOException;

@RequiredArgsConstructor
@Component
public class DefaultPayloadConstructor {

    private final @NonNull ObjectMapper mapper;

    public @NonNull String createPayload(@NonNull Object event) {
        try {
            return mapper.writeValueAsString(event);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e);
        }
    }

}
