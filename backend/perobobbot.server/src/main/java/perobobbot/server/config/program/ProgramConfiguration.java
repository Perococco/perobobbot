package perobobbot.server.config.program;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.PolicyManager;
import perobobbot.common.messaging.ChatController;
import perobobbot.program.manager.ProgramData;
import perobobbot.program.manager.ProgramManager;
import perobobbot.program.manager.ProgramRepository;
import perobobbot.services.Services;

@Configuration
@RequiredArgsConstructor
public class ProgramConfiguration {

    @NonNull
    private final Services services;

    @NonNull
    private final PolicyManager policyManager;


    @Bean
    public ProgramRepository programRepository() {
        final ProgramRepository repository = ProgramRepository.create(services, policyManager);
        return services.findService(ChatController.class)
                       .map(c -> wrapProgramRepository(repository, c))
                       .orElse(repository);
    }

    @Bean(destroyMethod = "disable", initMethod = "enable")
    public ProgramManager programExecutor(@NonNull ProgramRepository programRepository) {
        final ProgramData<ProgramManager> programData = ProgramManager.create(services, policyManager, programRepository);
        return services.findService(ChatController.class)
                       .<ProgramManager>map(c -> new ProgramManagerWithCommand(programData, c))
                       .orElseGet(programData::getProgram);
    }


    private ProgramRepository wrapProgramRepository(@NonNull ProgramRepository programRepository, ChatController chatController) {
        return programRepository.wrapProgram(prgData -> new ProgramWithCommand(prgData, chatController));
    }


}
