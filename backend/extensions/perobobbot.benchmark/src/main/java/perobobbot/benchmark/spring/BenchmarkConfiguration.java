package perobobbot.benchmark.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.benchmark.BenchmarkExtension;
import perobobbot.benchmark.BenchmarkExtensionFactory;
import perobobbot.benchmark.action.StartBenchmark;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandRegistry;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class BenchmarkConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with(BenchmarkExtensionFactory.NAME, BenchmarkConfiguration.class);
    }

    private final @NonNull Overlay overlay;
    private final @NonNull PolicyManager policyManager;
    private final @NonNull CommandRegistry commandRegistry;

    @Bean
    public BenchmarkExtensionFactory benchmarkExtensionFactory() {
        return new BenchmarkExtensionFactory(overlay,policyManager,commandRegistry);
    }


}
