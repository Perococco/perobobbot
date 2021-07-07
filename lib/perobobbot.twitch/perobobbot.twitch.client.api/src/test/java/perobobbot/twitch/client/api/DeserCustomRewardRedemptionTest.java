package perobobbot.twitch.client.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.twitch.client.api.channelpoints.CustomRewardRedemption;

import java.util.stream.Stream;

public class DeserCustomRewardRedemptionTest extends AbstractDesetTest {

    public static Stream<String> samples() {
        return readContent("CustomRewardRedemption");
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void shouldDeserialize(String content) throws JsonProcessingException {
        final var customRewards = objectMapper.readValue(content, CustomRewardRedemption.class);
        Assertions.assertNotNull(customRewards);
    }


}
