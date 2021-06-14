package perobobbot.twitch.eventsub.api.notification;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.twitch.eventsub.api.EventSubNotification;
import perobobbot.twitch.eventsub.api.deser.EventSubModule;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestNotificationDeser {

    private ObjectMapper objectMapper;


    @BeforeEach
    public void setUp() {
        this.objectMapper = new ObjectMapper();
        EventSubModule.provider().getJsonModules().forEach(objectMapper::registerModule);
    }

    public static @NonNull Stream<String> samples() {
        return IntStream.iterate(0, i -> i+1)
                 .mapToObj(i -> "EventSubNotification_"+i+".json")
                 .map(TestNotificationDeser.class::getResource)
                 .takeWhile(Objects::nonNull)
                 .map(TestNotificationDeser::readResource);
    }

    private static @NonNull String readResource(@NonNull URL url) {
        try(var inputStream = new BufferedInputStream(url.openStream())) {
            final var bytes = inputStream.readAllBytes();
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }



    @ParameterizedTest
    @MethodSource("samples")
    public void testDeserialization(@NonNull String content) throws JsonProcessingException {
        final var notification = objectMapper.readValue(content, EventSubNotification.class);

        Assertions.assertNotNull(notification);
        final var eventType = notification.getSubscription().getEventType();
        Assertions.assertEquals(eventType, notification.getEvent().getClass());
    }
}
