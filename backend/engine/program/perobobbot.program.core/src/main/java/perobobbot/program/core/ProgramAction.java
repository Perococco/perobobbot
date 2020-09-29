package perobobbot.program.core;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.common.lang.ChannelInfo;

public interface ProgramAction {

    void startProgram(@NonNull String programName);

    void stopProgram(@NonNull String programName);

    void startAllPrograms();

    void stopAllPrograms();

    @NonNull
    ImmutableSet<String> getNamesOfEnabledPrograms();

    boolean isEnabled(@NonNull String programName);

    void warnForUnknownProgram(@NonNull ChannelInfo channelInfo, @NonNull String unknownProgramName);

    boolean isKnownProgram(@NonNull String programName);

    void displayAllProgram(@NonNull ChannelInfo channelInfo);
}
