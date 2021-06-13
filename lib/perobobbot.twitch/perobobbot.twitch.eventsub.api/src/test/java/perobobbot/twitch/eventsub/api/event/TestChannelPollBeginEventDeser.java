package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

public class TestChannelPollBeginEventDeser extends AbstractEventDeserTest {

    public static Stream<String> samples() {
        return readObjects("channel_poll_begin_event");
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void testDeserialization(@NonNull String data) throws JsonProcessingException {
        final var event = objectMapper.readValue(data, ChannelPoolBeginEvent.class);
        Assertions.assertNotNull(event);
    }

}
