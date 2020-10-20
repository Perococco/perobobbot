package perobobbot.program.sample;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.IO;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactory;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.sample.ping.PingAction;
import perococco.perobobbot.program.sample.ping.command.PingExecution;

import java.time.Duration;

public class PingFactory implements ProgramFactory {

    public static final String NAME = "Ping";

    @Override
    public @NonNull String programName() {
        return NAME;
    }

    @Override
    public @NonNull Program create(@NonNull Services services) {
        return Program.builder(PingAction::create)
                      .setName(NAME)
                      .setServices(services)
                      .attachChatCommand("!myping", PingExecution::new, ExecutionPolicy.withGlobalCooldown(Duration.ofSeconds(10)))
                      .build();
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }

    @Override
    public @NonNull ImmutableSet<Class<?>> requiredServices() {
        return ImmutableSet.of(IO.class);
    }

}
