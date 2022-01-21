package perobobbot.discord.gateway.impl.state;

import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.net.URI;
import java.util.stream.Stream;

public class GatewayUriBuilderTest {

    public static Stream<Arguments> gatewayURIs() {
        return Stream.of(
                Arguments.of("wss://gateway.discord.gg/", 1, "json", "wss://gateway.discord.gg/?v=1&encoding=json"),
                Arguments.of("wss://gateway.discord.gg/", 10, "etf", "wss://gateway.discord.gg/?v=10&encoding=etf"),
                Arguments.of("wss://gateway.discord.gg/", -1, "toto", "wss://gateway.discord.gg/?v=-1&encoding=toto"),
                Arguments.of("wss://gateway.discord.gg/", 3, "etf", "wss://gateway.discord.gg/?v=3&encoding=etf"),
                Arguments.of("wss://gateway.discord.gg/", 6, "json", "wss://gateway.discord.gg/?v=6&encoding=json"),
                Arguments.of("wss://gateway.discord.gg/", 12, "hello", "wss://gateway.discord.gg/?v=12&encoding=hello"),
                Arguments.of("wss://gateway.discord.gg", 1, "json", "wss://gateway.discord.gg/?v=1&encoding=json"),
                Arguments.of("wss://gateway.discord.gg", 10, "etf", "wss://gateway.discord.gg/?v=10&encoding=etf"),
                Arguments.of("wss://gateway.discord.gg", -1, "toto", "wss://gateway.discord.gg/?v=-1&encoding=toto"),
                Arguments.of("wss://gateway.discord.gg", 3, "etf", "wss://gateway.discord.gg/?v=3&encoding=etf"),
                Arguments.of("wss://gateway.discord.gg", 6, "json", "wss://gateway.discord.gg/?v=6&encoding=json"),
                Arguments.of("wss://gateway.discord.gg", 12, "hello", "wss://gateway.discord.gg/?v=12&encoding=hello")
        );
    }

    @ParameterizedTest
    @MethodSource("gatewayURIs")
    public void shouldMatchExpectedURI(@NonNull String url, int version, @NonNull String encoding, @NonNull URI expectedURI) {
        final var actualURI = GatewayUriBuilder.INSTANCE.createGatewayUri(url, version, encoding);
        Assertions.assertEquals(expectedURI,actualURI);
    }
}
