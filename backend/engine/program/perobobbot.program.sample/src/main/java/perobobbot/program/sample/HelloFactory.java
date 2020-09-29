package perobobbot.program.sample;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.IO;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactory;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.sample.hello.HelloGreeterTask;
import perococco.perobobbot.program.sample.hello.HelloIdentity;
import perococco.perobobbot.program.sample.hello.command.HelloMessageHandler;

public class HelloFactory implements ProgramFactory {

    public static final String NAME = "Greeter";

    @Override
    public @NonNull String programName() {
        return NAME;
    }

    @Override
    public @NonNull Program create(@NonNull Services services) {
        return Program.builder(new HelloIdentity())
                      .setName(NAME)
                      .setServices(services)
                      .setBackgroundTask(HelloGreeterTask::new)
                      .setMessageHandler(HelloMessageHandler::new)
                      .build();
    }

    @Override
    public @NonNull ImmutableSet<Class<?>> requiredServices() {
        return ImmutableSet.of(IO.class);
    }
}
