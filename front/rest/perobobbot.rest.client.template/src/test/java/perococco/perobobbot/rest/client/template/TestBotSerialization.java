package perococco.perobobbot.rest.client.template;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.lang.Bot;
import perobobbot.lang.Credential;
import perobobbot.lang.Platform;
import perobobbot.lang.Secret;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

public class TestBotSerialization {

    private Bot input;

    private Object deserialized;

    @BeforeEach
    void setUp() throws JsonProcessingException {
        final var mapper  = RestObjectMapper.create();

        input = new Bot(UUID.randomUUID(), "bruce", "bobot", ImmutableMap.of());

        final String serialized = mapper.writeValueAsString(input);
        this.deserialized = mapper.readValue(serialized, Bot.class);
    }

    @Test
    public void deserializedShouldNotBeNull() {
        Assertions.assertNotNull(deserialized);
    }

    @Test
    public void deserializedShouldBeABot() {
        Assertions.assertTrue(deserialized instanceof Bot);
    }

    @Test
    public void deserializedShouldNotHaveAnyCredential() {
        final var bot = (Bot) deserialized;
        final var nbCredentials = Arrays.stream(Platform.values())
                                        .map(bot::findCredential)
                                        .flatMap(Optional::stream)
                                        .count();

        Assertions.assertEquals(0L,nbCredentials);

    }
}
