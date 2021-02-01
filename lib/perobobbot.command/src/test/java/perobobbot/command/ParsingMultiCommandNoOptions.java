package perobobbot.command;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import perobobbot.access.AccessRule;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.MessageContext;
import perobobbot.lang.Role;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ParsingMultiCommandNoOptions {

    private CommandRegistry commandRegistry;

    private Map<UUID,Data> dataMap = new HashMap<>();

    @Value
    private static class Data {
        @NonNull String id;
        String p1;
        String p2;
    }

    @BeforeEach
    void setUp() {
        commandRegistry = CommandRegistry.create();
        final var factory = CommandDefinition.factory("test");

        final var commandDefinitions = ImmutableList.of(
                factory.create("play", AccessRule.create(Role.ANY_USER, Duration.ZERO), createAction("PLAY1")),
                factory.create("play {user} {amount}", AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO), createAction("PLAY2"))
        );

        commandRegistry.addCommandDefinitions(commandDefinitions);
    }
    private CommandAction createAction(@NonNull String id) {
        return (p,c) -> {
            final var data = new Data(id, p.findParameter("user").orElse(null),p.findParameter("amount").orElse(null));
            this.dataMap.put(c.getBotId(),data);
        };
    }


    @Test
    public void allCommandShouldHaveBeenAdded() {
        Assertions.assertTrue(commandRegistry.findCommand("play").isPresent());
        Assertions.assertTrue(commandRegistry.findCommand("play toto 10").isPresent());
    }

    @Test
    public void playCommandShouldBeFound() {
        final UUID id = UUID.randomUUID();
        commandRegistry.findCommand("play").get().execute(new TestExecutionContext(id));
        var data = dataMap.get(id);
        Assertions.assertNotNull(data);
        Assertions.assertEquals("PLAY1",data.id);
        Assertions.assertNull(data.p1);
        Assertions.assertNull(data.p2);
    }

    @Test
    public void playWithParameterCommandShouldBeFound() {
        final UUID id = UUID.randomUUID();
        commandRegistry.findCommand("play toto 100").get().execute(new TestExecutionContext(id));
        var data = dataMap.get(id);
        Assertions.assertNotNull(data);
        Assertions.assertEquals("PLAY2",data.id);
        Assertions.assertEquals("toto", data.p1);
        Assertions.assertEquals("100",data.p2);
    }

    @RequiredArgsConstructor
    private static class TestExecutionContext implements ExecutionContext {

        private final @NonNull UUID botId;
        @Override
        public @NonNull MessageContext getMessageContext() {
            throw new RuntimeException("NOT IMPLEMENTED");
        }

        @Override
        public @NonNull String getCommand() {
            throw new RuntimeException("NOT IMPLEMENTED");
        }

        @Override
        public @NonNull UUID getBotId() {
            return botId;
        }
    }
}
