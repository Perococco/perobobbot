package perobobbot.twitch.client.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.twitch.client.api.channelpoints.UpdateCustomRewardParameter;

import java.util.stream.Stream;

public class SerUpdateCustomRewardParameter extends AbstractDesetTest {

    public static Stream<UpdateCustomRewardParameter> samples() {
        return Stream.of(UpdateCustomRewardParameter.builder().enabled(true).build())
                ;
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void shouldDeserialize(UpdateCustomRewardParameter value) throws JsonProcessingException {
        final var customRewards = objectMapper.writeValueAsString(value);
        System.out.println(customRewards);
        Assertions.assertNotNull(customRewards);
    }


}
