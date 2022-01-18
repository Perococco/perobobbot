package perobobbot.discord.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.lang.RandomString;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TestRoleTagDeserialisation {

    protected ObjectMapper objectMapper;

    public static Stream<Arguments> serializedRoleTags() {
        return Stream.concat(
                Stream.of(
                        createOneTestSample("hal", "abcdef"),
                        createOneTestSample("Has", "12345")
                ),
                IntStream.range(0, 20).mapToObj(i -> createOneTestSample(RandomString.createWithLength(10), RandomString.createWithLength(10)))
        );
    }

    @BeforeEach
    public void setUp() {
        this.objectMapper = new JsonMapper();
    }

    @ParameterizedTest
    @MethodSource("serializedRoleTags")
    public void deserializeRoleTag(@NonNull String serializedRoleTag, @NonNull String expectedBotId, @NonNull String expectedIntegrationId) throws JsonProcessingException {
        final var tags = this.objectMapper.readValue(serializedRoleTag, RoleTag.class);

        Assertions.assertNotNull(tags);
        Assertions.assertEquals(expectedBotId, tags.getBotId());
        Assertions.assertEquals(expectedIntegrationId, tags.getIntegrationId());

    }

    private static Arguments createOneTestSample(@NonNull String botId, @NonNull String integrationId) {
        final var content = """
                 {
                 "bot_id":"%s",
                 "integration_id":"%s"
                 }
                """.formatted(botId, integrationId);

        return Arguments.of(content, botId, integrationId);
    }
}
