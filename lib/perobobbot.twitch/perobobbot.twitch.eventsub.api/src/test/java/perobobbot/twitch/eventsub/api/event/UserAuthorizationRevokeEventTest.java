package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.twitch.eventsub.api.deser.EventSubModule;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class UserAuthorizationRevokeEventTest {

    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        EventSubModule.provider().getJsonModules().forEach(objectMapper::registerModule);
    }

    public static @NonNull Stream<URL> samples() {
        return IntStream.iterate(0,i -> i+1)
                .mapToObj(i -> "UserAuthorizationRevokeEvent_"+i+".json")
                .map(UserAuthorizationRevokeEventTest.class::getResource)
                .takeWhile(Objects::nonNull);
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void name(@NonNull URL url) throws IOException {
        final var data = readUrl(url);
        final var object = objectMapper.readValue(data,UserAuthorizationRevokeEvent.class);
        Assertions.assertNotNull(object);
        Assertions.assertNotNull(object.getUser());
        Assertions.assertNotNull(object.getUser().getName());
    }

    private @NonNull String readUrl(@NonNull URL url) throws IOException {
        try (var i = new BufferedInputStream(url.openStream())) {
            return new String(i.readAllBytes(), StandardCharsets.UTF_8);
        }
    }
}
