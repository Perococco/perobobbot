package perobobbot.discord.gateway.impl.state;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.discord.resources.Message;

import java.util.stream.Stream;

public class MessageCreateDeserTest  extends AbstractDeserTest{


    public static Stream<Arguments> contents() {
        return availableResources().filter(s -> s.startsWith("dispatch_message_create"))
                     .map(resourceName -> Arguments.of(resourceName, readResourceContent(resourceName)));
    }


    @ParameterizedTest
    @MethodSource("contents")
    public void shouldBeDeserialized(@NonNull String resourceName, @NonNull String content) {
        final var object = messageMapper.map(content);
        Assertions.assertTrue(object.getEvent() instanceof Message);
    }
}
