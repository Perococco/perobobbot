package perobobbot.twitch.eventsub.api.event;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import perobobbot.lang.fp.Value2;
import perobobbot.twitch.eventsub.api.SubscriptionType;
import perobobbot.twitch.eventsub.api.deser.EventSubModule;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EventSubEventDeserTest {

    protected ObjectMapper objectMapper;


    private Set<Class<? extends EventSubEvent>> allEvents() {
        return Arrays.stream(SubscriptionType.values())
                     .map(SubscriptionType::getEventType)
                     .collect(Collectors.toSet());
    }

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        EventSubModule.provider().getJsonModules().forEach(objectMapper::registerModule);
    }

    @TestFactory
    public Collection<DynamicTest> dynamicDeserializationTest() {
        final var allEvents = allEvents();

        final var typeTests = allEvents.stream().flatMap(t -> {
            final var header = "[" + t.getSimpleName() + "]";
            final var tests = createDynamicTest(t);
            return Stream.concat(
                    Stream.of(DynamicTest.dynamicTest(header + " Should have some test",
                                                      () -> Assertions.assertNotEquals(0, tests.size(),
                                                                                       "Should have some test"))),
                    tests.stream()
            );
        });

        return Stream.concat(
                Stream.of(DynamicTest.dynamicTest("All events are used",
                                                  () -> Assertions.assertEquals(SubscriptionType.values().length,
                                                                                allEvents.size()))),
                typeTests
        ).collect(Collectors.toList());
    }

    private Collection<DynamicTest> createDynamicTest(@NonNull Class<?> type) {
        final var header = "[" + type.getSimpleName() + "]";
        return getSamples(type.getSimpleName())
                .map(pair -> DynamicTest.dynamicTest(header + " Deserialize '" + pair.getFirst() + "'",
                                                     () -> performDeserializationTest(pair.getSecond(), type)))
                .collect(Collectors.toList());
    }

    private void performDeserializationTest(@NonNull URL url, @NonNull Class<?> type) throws JsonProcessingException {
        final var content = readFile(url);
        final var value = objectMapper.readValue(content, type);
        Assertions.assertNotNull(value);
    }

    protected static @NonNull Stream<Value2<String, URL>> getSamples(String prefix) {
        return IntStream.iterate(0, i -> i + 1)
                        .mapToObj(i -> prefix + "_" + i + ".json")
                        .map(file -> {
                            final var url = EventSubEventDeserTest.class.getResource(file);
                            return Optional.ofNullable(url).map(u -> Value2.of(file, url));
                        })
                        .takeWhile(Optional::isPresent)
                        .flatMap(Optional::stream);
    }

    private @NonNull String readFile(@NonNull URL url) {
        try (var i = new BufferedInputStream(url.openStream())) {
            return new String(i.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException io) {
            throw new UncheckedIOException(io);
        }
    }
}
