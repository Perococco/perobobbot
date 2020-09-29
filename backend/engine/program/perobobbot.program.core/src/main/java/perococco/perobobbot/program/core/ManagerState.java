package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.program.core.Program;

import java.util.Optional;

public class ManagerState {

    @NonNull
    public static ManagerState noneStarted(@NonNull ImmutableList<Program> programs)  {
        return new ManagerState(programs, ImmutableSet.of());
    }

    @NonNull
    private final ImmutableList<Program> programs;

    @NonNull
    private final ImmutableMap<String, Program> programByName;

    @NonNull
    @Getter
    private final ImmutableSet<String> namesOfEnabledPrograms;

    @NonNull
    @Getter
    private final ImmutableList<Program> enabledPrograms;

    @Builder(toBuilder = true)
    public ManagerState(@NonNull ImmutableList<Program> programs, @NonNull ImmutableSet<String> namesOfEnabledPrograms) {
        this.programs = programs;
        this.programByName = programs.stream().collect(ImmutableMap.toImmutableMap(Program::getName, p -> p));
        this.namesOfEnabledPrograms = namesOfEnabledPrograms;
        this.enabledPrograms = programs.stream()
                                       .filter(p -> namesOfEnabledPrograms.contains(p.getName()))
                                       .collect(ImmutableList.toImmutableList());
    }

    public void cleanUp() {
        programs.forEach(Program::cleanUp);
    }

    public boolean isKnownProgram(@NonNull String programName) {
        return programByName.containsKey(programName);
    }

    public boolean isEnabled(@NonNull String programName) {
        return namesOfEnabledPrograms.contains(programName);
    }

    @NonNull
    public Optional<Program> findProgram(@NonNull String programName) {
        return Optional.ofNullable(programByName.get(programName));
    }

    @NonNull
    public ImmutableList<Program> getDisabledPrograms() {
        return programByName.keySet()
                            .stream()
                            .filter(n -> !enabledPrograms.contains(n))
                            .map(programByName::get)
                            .collect(ImmutableList.toImmutableList());
    }


    @NonNull
    public ImmutableSet<String> getProgramNames() {
        return programByName.keySet();
    }

}
