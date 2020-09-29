package perococco.perobobbot.program.core.manager;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.IO;
import perobobbot.program.core.ProgramAction;
import perococco.perobobbot.program.core.ManagerIdentity;
import perococco.perobobbot.program.core.manager.mutation.StartAllProgramMutation;
import perococco.perobobbot.program.core.manager.mutation.StopAllProgramMutation;
import perococco.perobobbot.program.core.manager.mutation.StopProgramMutation;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class PerococcoProgramAction implements ProgramAction {

    @NonNull
    private final IO io;

    @NonNull
    private final ManagerIdentity managerIdentity;

    @Override
    public void startProgram(@NonNull String programName) {
        managerIdentity.mutate(new StartAllProgramMutation());
    }

    @Override
    public void stopProgram(@NonNull String programName) {
        managerIdentity.mutate(new StopProgramMutation(programName));
    }

    @Override
    public void startAllPrograms() {
        managerIdentity.mutate(new StartAllProgramMutation());
    }

    @Override
    public void stopAllPrograms() {
        managerIdentity.mutate(new StopAllProgramMutation());
    }

    @Override
    public @NonNull ImmutableSet<String> getNamesOfEnabledPrograms() {
        return managerIdentity.getNamesOfEnabledPrograms();
    }

    @Override
    public boolean isKnownProgram(@NonNull String programName) {
        return managerIdentity.getProgramNames().contains(programName);
    }

    @Override
    public boolean isEnabled(@NonNull String programName) {
        return managerIdentity.isEnabled(programName);
    }

    @Override
    public void warnForUnknownProgram(@NonNull ChannelInfo channelInfo, @NonNull String unknownProgramName) {
        io.forChannelInfo(channelInfo).print("Unknown program '" + unknownProgramName + "'");
    }

    @Override
    public void displayAllProgram(@NonNull ChannelInfo channelInfo) {
        final String message = managerIdentity.getProgramNames()
                                              .stream()
                                              .map(n -> n + (this.isEnabled(n) ? "*" : ""))
                                              .collect(Collectors.joining(", ", "Available programs: ", "."));
        io.forChannelInfo(channelInfo).print(message);
    }
}
