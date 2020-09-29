package perobobbot.server.config;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.service.core.Services;

@Configuration
@RequiredArgsConstructor
public class ProgramConfiguration {

    @NonNull
    private final Services services;

    @Bean(destroyMethod = "stop")
    public ProgramExecutor programExecutor() {
        return new MsgProgramExecutor(ProgramExecutor.create(services));
    }

}
