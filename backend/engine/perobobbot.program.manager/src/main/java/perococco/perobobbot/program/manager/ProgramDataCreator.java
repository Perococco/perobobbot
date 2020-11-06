package perococco.perobobbot.program.manager;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import perobobbot.access.PolicyManager;
import perobobbot.common.lang.ThrowableTool;
import perobobbot.program.core.ProgramFactory;
import perobobbot.program.manager.ProgramData;
import perobobbot.services.Services;

import java.util.Optional;

@Log4j2
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
class ProgramDataCreator {

    private final @NonNull Services services;
    private final @NonNull PolicyManager policyManager;
    private final @NonNull ProgramFactory programFactory;

    private Services filteredServices;
    private ProgramFactory.Result factoryResult;
    private ProgramData<?> programData;

    private @NonNull Optional<ProgramData<?>> create() {
        try {
            this.filterServicesOnRequirements();
            this.createFactoryResultWithFilteredServices();
            this.convertFactoryResultToProgramData();
            return Optional.of(programData);
        } catch (Throwable t) {
            ThrowableTool.interruptThreadIfCausedByInterruption(t);
            if (LOG.isDebugEnabled()) {
                LOG.warn("Could not create program '" + programFactory.getProgramName() + "'", t);
            } else {
                LOG.warn("Could not create program '" + programFactory.getProgramName() + "'");
            }
            return Optional.empty();
        }
    }

    private void filterServicesOnRequirements() {
        this.filteredServices = services.filter(programFactory.getRequirement());
    }

    private void createFactoryResultWithFilteredServices() {
        this.factoryResult = programFactory.create(filteredServices, policyManager);
    }

    private void convertFactoryResultToProgramData() {
        this.programData = ProgramData.from(this.factoryResult, this.programFactory.isAutoStart());
    }


    public static @NonNull Optional<ProgramData<?>> create(@NonNull Services services,
                                                        @NonNull PolicyManager policyManager,
                                                        @NonNull ProgramFactory programFactory) {
        return new ProgramDataCreator(services,policyManager,programFactory).create();
    }


}
