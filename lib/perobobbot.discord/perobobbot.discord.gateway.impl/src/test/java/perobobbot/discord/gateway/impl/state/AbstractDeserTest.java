package perobobbot.discord.gateway.impl.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JSR310Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import perobobbot.discord.gateway.impl.MessageMapper;
import perobobbot.discord.gateway.impl.message.DefaultMessageMapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractDeserTest {

    public static Stream<String> availableResources() {
        return Stream.of(
                "dispatch_guild_member_update_01.json",
                "dispatch_message_create_01.json",
                "dispatch_message_create_02.json",
                "dispatch_message_create_03.json",
                "dispatch_message_create_04.json",
                "dispatch_message_create_05.json",
                "dispatch_message_create_06.json",
                "dispatch_ready_01.json",
                "heartbeatAck_02.json",
                "heartbeatAck_01.json",
                "hello_01.json"
        );
    }


    public static String readResourceContent(@NonNull String resourceName) {
        try (BufferedReader bi = new BufferedReader(new InputStreamReader(MessageCreateDeserTest.class.getResourceAsStream(resourceName)))) {
            return bi.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    protected MessageMapper messageMapper;

    @BeforeEach
    void setUp() {
        this.messageMapper = new DefaultMessageMapper(new TestMapper());
    }

    private static class TestMapper extends ObjectMapper {
        public TestMapper() {
            this.registerModule(new JavaTimeModule());
        }
    }
}
