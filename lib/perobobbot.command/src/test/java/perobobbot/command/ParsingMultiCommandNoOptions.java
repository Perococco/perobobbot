package perobobbot.command;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import perobobbot.access.AccessRule;
import perobobbot.lang.Role;

import java.time.Duration;

public class ParsingMultiCommandNoOptions {



    @Test
    public void name() {
        final var commandRegistry = CommandRegistry.create();
        final var factory = CommandDefinition.factory("test");

        final var commandDefinitions = ImmutableList.of(
                factory.create("play", AccessRule.create(Role.ANY_USER, Duration.ZERO),()-> System.out.println("PLAY")),
                factory.create("play {user} {amount}", AccessRule.create(Role.ADMINISTRATOR, Duration.ZERO),()-> System.out.println("SET"))
        );

        commandRegistry.addCommandDefinitions(commandDefinitions);

        Assertions.assertTrue(commandRegistry.findCommand("play").isPresent());
        Assertions.assertTrue(commandRegistry.findCommand("play toto 10").isEmpty());
    }
}
