package perobobbot.program.sample;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.IO;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactory;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.sample.echo.EchoAction;
import perococco.perobobbot.program.sample.echo.command.EchoCommand;

import java.time.Duration;

public class EchoFactory implements ProgramFactory {

    public static final String NAME = "Echo";

    @Override
    public @NonNull String programName() {
        return NAME;
    }

    @Override
    public @NonNull Program create(@NonNull Services services) {
        return Program.builder(EchoAction::create)
                      .setName(NAME)
                      .setServices(services)
                      .attachChatCommand("!echo", EchoCommand::new, ExecutionPolicy.withGlobalCooldown(Duration.ofSeconds(10)))
                      .build();
    }

    @Override
    public @NonNull ImmutableSet<Class<?>> requiredServices() {
        return ImmutableSet.of(IO.class);
    }
}
