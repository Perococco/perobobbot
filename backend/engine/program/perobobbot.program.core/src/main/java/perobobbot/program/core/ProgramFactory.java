package perobobbot.program.core;

import com.google.common.collect.ImmutableList;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.core.PolicyManager;
import perobobbot.common.messaging.Command;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

public interface ProgramFactory {

    @NonNull
    String getProgramName();

    @NonNull
    Requirement getRequirement();

    @NonNull
    ProgramFactory.Result create(@NonNull Services services, @NonNull PolicyManager policyManager);

    default boolean isAutoStart() {
        return true;
    }

    @RequiredArgsConstructor
    @Getter
    class Result {

        @NonNull
        private final Program program;

        @NonNull
        private final ImmutableList<Command> commands;

        public @NonNull String getProgramName() {
            return program.getName();
        }

        @NonNull
        public static ProgramFactory.Result withoutCommands(@NonNull Program program) {
            return new Result(program, ImmutableList.of());
        }

        @NonNull
        public static ProgramFactory.Result withOneCommand(@NonNull Program program, @NonNull Command command) {
            return new Result(program, ImmutableList.of(command));
        }

        @NonNull
        public static ProgramFactory.Result withCommands(@NonNull Program program, @NonNull ImmutableList<Command> commands) {
            return new Result(program, ImmutableList.of());
        }

    }
}
