package perobobbot.program.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ChannelInfo;
import perobobbot.common.lang.MessageContext;
import perobobbot.service.core.Services;

@RequiredArgsConstructor
public class ProxyProgramExecutor implements ProgramExecutor {

    @NonNull
    private final ProgramExecutor programExecutor;


    public static ProgramExecutor create(@NonNull Services services) {
        return ProgramExecutor.create(services);
    }

    @Override
    public void handleMessage(@NonNull MessageContext messageContext) {
        programExecutor.handleMessage(messageContext);
    }

    @Override
    public void stop() {
        programExecutor.stop();
    }

    @Override
    public void start() {
        programExecutor.start();
    }

    @Override
    public void startProgram(@NonNull String programName) {
        programExecutor.startProgram(programName);
    }

    @Override
    public void stopProgram(@NonNull String programName) {
        programExecutor.stopProgram(programName);
    }

    @Override
    public void startAllPrograms() {
        programExecutor.startAllPrograms();
    }

    @Override
    public void stopAllPrograms() {
        programExecutor.stopAllPrograms();
    }

    @Override
    public @NonNull ImmutableSet<String> getNamesOfEnabledPrograms() {
        return programExecutor.getNamesOfEnabledPrograms();
    }

    @Override
    public boolean isEnabled(@NonNull String programName) {
        return programExecutor.isEnabled(programName);
    }

    @Override
    public void warnForUnknownProgram(@NonNull ChannelInfo channelInfo, @NonNull String unknownProgramName) {
        programExecutor.warnForUnknownProgram(channelInfo, unknownProgramName);
    }

    @Override
    public boolean isKnownProgram(@NonNull String programName) {
        return programExecutor.isKnownProgram(programName);
    }

    @Override
    public void displayAllProgram(@NonNull ChannelInfo channelInfo) {
        programExecutor.displayAllProgram(channelInfo);
    }
}
