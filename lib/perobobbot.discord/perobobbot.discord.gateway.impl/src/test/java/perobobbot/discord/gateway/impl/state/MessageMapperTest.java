package perobobbot.discord.gateway.impl.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import perobobbot.discord.gateway.impl.GatewayMessage;
import perobobbot.discord.gateway.impl.MessageMapper;
import perobobbot.discord.gateway.impl.message.DefaultMessageMapper;

import java.util.stream.Stream;

public class MessageMapperTest {

    private MessageMapper messageMapper;

    @BeforeEach
    void setUp() {
        this.messageMapper = new DefaultMessageMapper(new ObjectMapper());
    }


    public static @NonNull Stream<String> contents() {
        return Stream.of("{\"t\":null,\"s\":null,\"op\":10,\"d\":{\"heartbeat_interval\":41250,\"_trace\":[\"[\\\"gateway-prd-main-r9rq\\\",{\\\"micros\\\":0.0}]\"]}}");
    }

    @ParameterizedTest
    @MethodSource("contents")
    public void name(@NonNull String content) {
        final GatewayMessage<?> map = messageMapper.map(content);
        Assertions.assertNotNull(map);
    }
}
