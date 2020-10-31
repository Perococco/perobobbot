package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.SubscriptionHolder;
import perobobbot.common.messaging.CommandBundle;
import perobobbot.common.messaging.CommandBundleFactory;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramInfo;
import perobobbot.program.core.ProgramManager;
import perobobbot.program.core.UnknownProgram;

@Log4j2
public class PerococcoProgramManager implements ProgramManager {

    @NonNull
    private final ImmutableMap<String, Program> programs;

    private final CommandBundle commandBundle;

    private final SubscriptionHolder commandSubscriptionHolder = new SubscriptionHolder();

    public PerococcoProgramManager(@NonNull ImmutableMap<String, Program> programs, @NonNull CommandBundleFactory<ProgramManager> bundleFactory) {
        this.programs = programs;
        this.commandBundle = bundleFactory.create(this);
    }

    @Synchronized
    public void enable() {
        commandSubscriptionHolder.replaceWith(commandBundle::attachCommandsToChat);
    }

    @Synchronized
    public void disable() {
        commandSubscriptionHolder.unsubscribe();
        stopAll();
    }

    @Override
    public void startProgram(@NonNull String programName) {
        getProgram(programName).enable();
    }

    @Override
    public void stopProgram(@NonNull String programName) {
        getProgram(programName).disable();
    }

    @Override
    public void stopAll() {
        programs.values().forEach(Program::disable);
    }

    @Override
    public void startAll() {
        programs.values().forEach(Program::enable);
    }

    @NonNull
    private Program getProgram(@NonNull String programName) {
        final Program program = programs.get(programName);
        if (program == null) {
            throw new UnknownProgram(programName);
        }
        return program;
    }

    @Override
    public @NonNull ImmutableSet<ProgramInfo> programInfo() {
        return programs.values()
                       .stream()
                       .map(Program::getInfo)
                       .collect(ImmutableSet.toImmutableSet());
    }


}
