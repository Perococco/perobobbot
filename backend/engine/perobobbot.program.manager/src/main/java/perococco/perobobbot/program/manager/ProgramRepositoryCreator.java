package perococco.perobobbot.program.manager;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.access.PolicyManager;
import perobobbot.common.lang.MapTool;
import perobobbot.program.core.ProgramFactory;
import perobobbot.program.manager.ProgramData;
import perobobbot.program.manager.ProgramRepository;
import perobobbot.services.Services;

import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProgramRepositoryCreator {

    public static @NonNull ProgramRepository create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        return new ProgramRepositoryCreator(services,policyManager).create();
    }

    private final @NonNull Services services;
    private final @NonNull PolicyManager policyManager;

    private ImmutableMap<String, ImmutableList<ProgramFactory>> programFactories;
    private ImmutableMap<String, ProgramData<?>> programDataMap;

    private PeroProgramRepository create() {
        this.retrieveAllProgramFactories();
        this.warnForAnyDuplicateProgramName();
        this.createProgramDataWithFulfilledRequirements();
        return PeroProgramRepository.create(programDataMap);
    }

    private void retrieveAllProgramFactories() {
        this.programFactories = ServiceLoader.load(ProgramFactory.class)
                                             .stream()
                                             .map(ServiceLoader.Provider::get)
                                             .collect(Collectors.collectingAndThen(
                                                     Collectors.groupingBy(ProgramFactory::getProgramName, ImmutableList.toImmutableList()),
                                                     ImmutableMap::copyOf));
    }

    private void warnForAnyDuplicateProgramName() {
        programFactories.keySet().stream().filter(name -> programFactories.get(name).size() > 1)
                        .forEach(name -> LOG.warn("Multiple programs with name '" + name + "'. All are ignored "));
    }

    private void createProgramDataWithFulfilledRequirements() {
        this.programDataMap = this.programFactories.values()
                                                   .stream()
                                                   .filter(l -> l.size() == 1)
                                                   .map(l -> l.get(0))
                                                   .map(pf -> ProgramDataCreator.create(services, policyManager,pf))
                                                   .flatMap(Optional::stream)
                                                   .collect(MapTool.collector(ProgramData::getProgramName));
    }

}
