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

public class ParsingMultiCommandOptionalOptions {

    private CommandRegistry commandRegistry;

    private Map<UUID,Data> dataMap = new HashMap<>();

    @Value
    private static class Data {
        @NonNull String id;
        String p1;
    }

    @BeforeEach
    void setUp() {
        commandRegistry = CommandRegistry.create();
        final var factory = CommandDefinition.factory("test");

        final var commandDefinitions = ImmutableList.of(
                factory.create("c4 [name]", AccessRule.create(Role.ANY_USER, Duration.ZERO), createAction("START")),
                factory.create("c4 stop [time]", AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO), createAction("STOP")),
                factory.create("c4 stop now", AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO), createAction("STOP_NOW"))
        );

        commandRegistry.addCommandDefinitions(commandDefinitions);
    }
    private CommandAction createAction(@NonNull String id) {
        return (p,c) -> {
            final var data = new Data(id, p.findParameter("name").orElse(null));
            this.dataMap.put(c.getBotId(),data);
        };
    }


    @Test
    public void allCommandShouldHaveBeenAdded() {
        Assertions.assertTrue(commandRegistry.findCommand("c4").isPresent());
        Assertions.assertTrue(commandRegistry.findCommand("c4 stop [time]").isPresent());
        Assertions.assertTrue(commandRegistry.findCommand("c4 stop now").isPresent());
    }

    @Test
    public void c4CommandShouldBeFound() {
        final UUID id = UUID.randomUUID();
        commandRegistry.findCommand("c4").get().execute(new TestExecutionContext(id));
        var data = dataMap.get(id);
        Assertions.assertNotNull(data);
        Assertions.assertEquals("START",data.id);
        Assertions.assertNull(data.p1);
    }

    @Test
    public void c4StopCommandShouldBeFound() {
        final UUID id = UUID.randomUUID();
        commandRegistry.findCommand("c4 stop").get().execute(new TestExecutionContext(id));
        var data = dataMap.get(id);
        Assertions.assertNotNull(data);
        Assertions.assertEquals("STOP",data.id);
        Assertions.assertNull(data.p1);
    }

    @Test
    public void c4StopNowCommandShouldBeFound() {
        final UUID id = UUID.randomUUID();
        commandRegistry.findCommand("c4 stop now").get().execute(new TestExecutionContext(id));
        var data = dataMap.get(id);
        Assertions.assertNotNull(data);
        Assertions.assertEquals("STOP_NOW",data.id);
        Assertions.assertNull(data.p1);
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
