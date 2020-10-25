package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.core.PolicyManager;
import perobobbot.program.core.ProgramManager;
import perobobbot.service.core.Services;

@Configuration
@RequiredArgsConstructor
public class ProgramConfiguration {

    @NonNull
    private final Services services;

    @NonNull
    private final PolicyManager policyManager;

    @Bean(destroyMethod = "stopAll")
    public ProgramManager programExecutor() {
        return ProgramManager.create(services,policyManager);
    }

}
