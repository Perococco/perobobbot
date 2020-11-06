package perococco.perobobbot.program.manager;

import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.common.lang.MapTool;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.common.lang.fp.Function1;
import perobobbot.common.lang.fp.UnaryOperator1;
import perobobbot.common.messaging.Command;
import perobobbot.program.core.Program;
import perobobbot.program.manager.ProgramData;
import perobobbot.program.manager.ProgramRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class PeroProgramRepository implements ProgramRepository {

    public static PeroProgramRepository create(@NonNull ImmutableMap<String, ProgramData<?>> programDataMap) {
        final ImmutableMap<String, Command> commands = programDataMap.values()
                                                                     .stream()
                                                                     .map(ProgramData::getCommands)
                                                                     .flatMap(Collection::stream)
                                                                     .collect(Collectors.collectingAndThen(Collectors.toMap(Command::name, c -> c),
                                                                                                           ImmutableMap::copyOf));
        return new PeroProgramRepository(programDataMap,commands);
    }

    @NonNull
    private final ImmutableMap<String, ProgramData<?>> programDataMap;

    private final ImmutableMap<String, Command> commands;


    @Override
    public @NonNull ProgramRepository wrapProgramData(@NonNull UnaryOperator1<ProgramData<?>> wrapper) {
        return new PeroProgramRepository(
                MapTool.mapValues(programDataMap,wrapper),
                commands
        );
    }

    @Override
    public @NonNull ProgramRepository wrapProgram(@NonNull Function1<? super ProgramData<?>, ? extends Program> wrapper) {
        return wrapProgramData(pd -> new ProgramData<>(wrapper.apply(pd),pd.isAutoStart(),pd.getCommands()));
    }

    @Override
    public @NonNull Optional<Program> findProgram(@NonNull String programName) {
        return Optional.ofNullable(programDataMap.get(programName)).map(ProgramData::getProgram);
    }

    @Override
    public @NonNull Optional<Command> findCommand(@NonNull String commandName) {
        return Optional.ofNullable(commands.get(commandName));
    }

    @Override
    public @NonNull Stream<Program> programStream() {
        return programDataMap.values()
                             .stream()
                             .map(ProgramData::getProgram);
    }

    @Override
    public void forEachProgramData(@NonNull Consumer1<? super ProgramData> consumer) {
        programDataMap.values().forEach(consumer);
    }
}
