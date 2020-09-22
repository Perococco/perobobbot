package perococco.perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import perobobbot.common.lang.ListTool;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.SetTool;
import perobobbot.program.core.Program;

import java.util.Map;
import java.util.Optional;

public class ManagerState {

    public static final ManagerState EMPTY = new ManagerState(ImmutableList.of(), ImmutableSet.of());

    @NonNull
    private final ImmutableList<ProgramWithPolicyHandling> programs;

    @NonNull
    private final ImmutableMap<String, ProgramWithPolicyHandling> programByName;

    @NonNull
    @Getter
    private final ImmutableSet<String> namesOfEnabledPrograms;

    @NonNull
    @Getter
    private final ImmutableList<Program> enabledPrograms;

    @Builder(toBuilder = true)
    public ManagerState(@NonNull ImmutableList<ProgramWithPolicyHandling> programs, @NonNull ImmutableSet<String> namesOfEnabledPrograms) {
        this.programs = programs;
        this.programByName = programs.stream().collect(ImmutableMap.toImmutableMap(ProgramWithPolicyHandling::getName, p -> p));
        this.namesOfEnabledPrograms = namesOfEnabledPrograms;
        this.enabledPrograms = programs.stream()
                                       .filter(p -> namesOfEnabledPrograms.contains(p.getName()))
                                       .collect(ImmutableList.toImmutableList());
    }

    public void cleanUp() {
        programs.forEach(ProgramWithPolicyHandling::cleanup);
    }

    public boolean isKnownProgram(@NonNull String programName) {
        return programByName.containsKey(programName);
    }

    public boolean isEnabled(@NonNull String programName) {
        return namesOfEnabledPrograms.contains(programName);
    }

    @NonNull
    public Optional<ProgramWithPolicyHandling> findProgram(@NonNull String programName) {
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
    public ManagerState addProgram(@NonNull ProgramWithPolicyHandling program) {
        final ImmutableList<ProgramWithPolicyHandling> newPrograms = ListTool.addLast(programs, program);
        if (programs == newPrograms) {
            return this;
        }
        return this.toBuilder().programs(newPrograms).build();
    }


    @NonNull
    public ImmutableSet<String> programNames() {
        return programByName.keySet();
    }

}
