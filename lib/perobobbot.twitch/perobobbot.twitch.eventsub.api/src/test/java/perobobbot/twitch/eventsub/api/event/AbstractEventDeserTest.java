package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import perobobbot.twitch.eventsub.api.deser.EventSubModule;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AbstractEventDeserTest {

    protected ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        EventSubModule.provider().getJsonModules().forEach(objectMapper::registerModule);
    }

    protected static @NonNull Stream<String> readObjects(String prefix) {
        return IntStream.iterate(0,i -> i+1)
                        .mapToObj(i -> prefix+"_" + i + ".json")
                        .map(TestChannelUnbanEventDeser.class::getResource)
                        .takeWhile(Objects::nonNull)
                        .map(r -> {
                            try (var i = new BufferedInputStream(r.openStream())) {
                                return new String(i.readAllBytes(), StandardCharsets.UTF_8);
                            } catch (IOException io) {
                                throw new UncheckedIOException(io);
                            }
                        });

    }
}
