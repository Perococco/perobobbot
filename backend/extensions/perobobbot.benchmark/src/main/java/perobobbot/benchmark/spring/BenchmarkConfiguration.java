package perobobbot.benchmark.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.benchmark.BenchmarkExtension;
import perobobbot.command.Command;
import perobobbot.command.CommandBundle;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class BenchmarkConfiguration {

    public static @NonNull Packages provider() {
        return Packages.with(BenchmarkExtension.EXTENSION_NAME, BenchmarkConfiguration.class);
    }

    private final @NonNull Overlay overlay;
    private final @NonNull PolicyManager policyManager;


    @Bean
    public BenchmarkExtension benchmarkExtension() {
        return new BenchmarkExtension(overlay);
    }

    @Bean(name = BenchmarkExtension.EXTENSION_NAME)
    public CommandBundle commandBundle(@NonNull BenchmarkExtension extension) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));

        return Command.factory()
                .add("bm start",policy,new StartBenchmark(extension))
                .add("bm stop", policy, extension::stop)
                .build();
    }
}
