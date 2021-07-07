package perobobbot.twitch.client.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.twitch.client.api.channelpoints.CustomReward;
import perobobbot.twitch.client.api.deser.TwitchApiSubModule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.URL;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DeserCustomRewardsTest extends AbstractDesetTest {



    public static Stream<String> samples() {
        return readContent("CustomRewards");
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void shouldDeserialize(String content) throws JsonProcessingException {
        final var customRewards = objectMapper.readValue(content, CustomReward.class);
        Assertions.assertNotNull(customRewards);
    }




}
