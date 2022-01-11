package perobobbot.lang;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

public class PlatformUserDeSerTest {


    private ObjectMapper mapper;

    public static Stream<PlatformUser> platformUsers() {
        return Stream.of(
                createDiscordUser(),
                createDiscordUser(),
                createDiscordUser(),
                createDiscordUser(),
                createTwitchUser(),
                createTwitchUser(),
                createTwitchUser(),
                createTwitchUser(),
                createTwitchUser()
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
    }

    @ParameterizedTest
    @MethodSource("platformUsers")
    public void testSerialization(@NonNull PlatformUser platformUser) throws JsonProcessingException {
        final var serialized = mapper.writeValueAsString(platformUser);
        System.out.println(serialized);
        final var deserialized = mapper.readValue(serialized,PlatformUser.class);

        Assertions.assertEquals(platformUser,deserialized);
    }

    private static DiscordUser createDiscordUser() {
        return DiscordUser.builder()
                .id(UUID.randomUUID())
                .discriminator(RandomString.createWithLength(4))
                .login(RandomString.createWithLength(12))
                .userId(RandomString.createWithLength(10))
                .build();
    }
    private static TwitchUser createTwitchUser() {
        return TwitchUser.builder()
                .id(UUID.randomUUID())
                .login(RandomString.createWithLength(12))
                .pseudo(RandomString.createWithLength(11))
                .userId(RandomString.createWithLength(10))
                .build();
    }
}
