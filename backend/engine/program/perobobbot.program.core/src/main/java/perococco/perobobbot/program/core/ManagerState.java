package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.SetTool;
import perobobbot.program.core.Program;

import java.util.Map;
import java.util.Optional;

public class ManagerState {

    public static final ManagerState EMPTY = new ManagerState(ImmutableMap.of(), ImmutableSet.of());

    @NonNull
    private final ImmutableMap<String, ProgramWithPolicyHandling> programs;

    @NonNull
    private final ImmutableSet<String> namesOfEnabledPrograms;

    @NonNull
    @Getter
    private final ImmutableList<Program> enabledPrograms;

    @Builder(toBuilder = true)
    public ManagerState(@NonNull ImmutableMap<String, ProgramWithPolicyHandling> programs, @NonNull ImmutableSet<String> namesOfEnabledPrograms) {
        this.programs = programs;
        this.namesOfEnabledPrograms = namesOfEnabledPrograms;
        this.enabledPrograms = programs.entrySet()
                                       .stream()
                                       .filter(e -> namesOfEnabledPrograms.contains(e.getKey()))
                                       .map(Map.Entry::getValue)
                                       .collect(ImmutableList.toImmutableList());
    }

    @NonNull
    public ManagerState addProgram(@NonNull ProgramWithPolicyHandling program) {
        final ImmutableMap<String, ProgramWithPolicyHandling> newPrograms = MapTool.add(programs, program.getName(), program);
        if (programs == newPrograms) {
            return this;
        }
        return this.toBuilder().programs(newPrograms).build();
    }

    public void cleanUp() {
        programs.values().forEach(ProgramWithPolicyHandling::cleanup);
    }

    public boolean isKnownProgram(@NonNull String programName) {
        return programs.containsKey(programName);
    }

    public boolean isEnabled(@NonNull String programName) {
        return namesOfEnabledPrograms.contains(programName);
    }

    @NonNull
    public Optional<ProgramWithPolicyHandling> findProgram(@NonNull String programName) {
        return Optional.ofNullable(programs.get(programName));
    }

    @NonNull
    public ManagerState enableProgram(@NonNull String programName) {
        if (!programs.containsKey(programName) || namesOfEnabledPrograms.contains(programName)) {
            return this;
        }
        return this.toBuilder()
                   .namesOfEnabledPrograms(SetTool.add(namesOfEnabledPrograms, programName))
                   .build();
    }

    @NonNull
    public ManagerState disableProgram(@NonNull String programName) {
        if (!programs.containsKey(programName) || !namesOfEnabledPrograms.contains(programName)) {
            return this;
        }
        return this.toBuilder()
                   .namesOfEnabledPrograms(SetTool.remove(namesOfEnabledPrograms, programName))
                   .build();
    }

    @NonNull
    public ImmutableSet<String> programNames() {
        return programs.keySet();
    }

    @NonNull
    public ManagerState startAll() {
        return toBuilder().namesOfEnabledPrograms(programs.keySet()).build();
    }

    @NonNull
    public ManagerState stopAll() {
        return toBuilder().namesOfEnabledPrograms(ImmutableSet.of()).build();
    }
}
