package perobobbot.twitch.oauth;


import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.oauth.OAuthModule;
import perobobbot.twitch.oauth.impl.TwitchValidation;

import java.util.stream.Stream;

public class TwitchValidationDeserializationTest {

    private ObjectMapper mapper;

    private static Stream<String> samples() {
        return Stream.of(
                """
                        {
                          "client_id": "m01e1fb0emhtr0toc6eydvl9zkuecu",
                          "login": "perococco",
                          "scopes": [
                            "user:read:follows"
                          ],
                          "user_id": "211307900",
                          "expires_in": 15262
                        }
                        """,
                """
                        {
                          "client_id": "wbmytr93xzw8zbg0p1izqyzzc5mbiz",
                          "login": "twitchdev",
                          "scopes": [
                            "channel:read:subscriptions"
                          ],
                          "user_id": "141981764",
                          "expires_in": 5520838
                        }
                        """
        );
    }

    @BeforeEach
    public void setUp() {
        this.mapper = new ObjectMapper();
        mapper.setVisibility(mapper.getSerializationConfig().getDefaultVisibilityChecker()
                                   .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                                   .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withIsGetterVisibility(JsonAutoDetect.Visibility.NONE)
                                   .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        mapper.registerModules(
                new GuavaModule(), new Jdk8Module(), new OAuthModule());
    }

    @ParameterizedTest
    @MethodSource("samples")
    public void shouldBeDeserialized(@NonNull String serializeValue) throws JsonProcessingException {
        final var result = this.mapper.readValue(serializeValue, TwitchValidation.class);

        Assertions.assertNotNull(result);


    }

}
