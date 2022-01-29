package perobobbot.discord.resources;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
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

public class TestRoleTagSerialization {

    private ObjectMapper objectMapper;

    public static Stream<RoleTag> roleTags() {
        return IntStream.range(0,20).mapToObj(i -> new RoleTag(
                RandomString.createWithLength(10+i),
                RandomString.createWithLength(10+i),
                null));
    }

    @BeforeEach
    public void setUp() {
        this.objectMapper = new JsonMapper();
    }

    @ParameterizedTest
    @MethodSource("roleTags")
    public void shouldHaveSnakeCasedBotIdFieldName(@NonNull RoleTag roleTag) throws JsonProcessingException {
        testFieldIsPresent(roleTag, "bot_id");
    }

    @ParameterizedTest
    @MethodSource("roleTags")
    public void shouldHaveSnakeCasedIntegerationIdFieldName(@NonNull RoleTag roleTag) throws JsonProcessingException {
        testFieldIsPresent(roleTag, "integration_id");
    }

    private void testFieldIsPresent(@NonNull RoleTag roleTag, @NonNull String fieldName) throws JsonProcessingException {
        final var serialized = this.objectMapper.writeValueAsString(roleTag);
        final var jsonNode = this.objectMapper.readTree(serialized);
        final var fieldValue = jsonNode.findValue(fieldName);
        Assertions.assertNotNull(fieldValue);
    }
}
