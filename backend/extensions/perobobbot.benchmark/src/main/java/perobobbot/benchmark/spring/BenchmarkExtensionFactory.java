package perobobbot.benchmark.spring;

import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.benchmark.BenchmarkExtension;
import perobobbot.benchmark.action.StartBenchmark;
import perobobbot.command.CommandBundle;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Packages;
import perobobbot.lang.Role;

import java.time.Duration;
import java.util.Optional;

@Component
public class BenchmarkExtensionFactory extends ExtensionFactoryBase<BenchmarkExtension> {

    public static @NonNull Packages provider() {
        return Packages.with(BenchmarkExtensionFactory.NAME, BenchmarkExtensionFactory.class);
    }

    public static final String NAME = "benchmark";


    public BenchmarkExtensionFactory(@NonNull Parameters parameters) {
        super(NAME, parameters);
    }

    @Override
    protected @NonNull BenchmarkExtension createExtension(@NonNull String userId, @NonNull Parameters parameters) {
        return new BenchmarkExtension(userId,parameters.getOverlay());
    }

    @Override
    protected Optional<CommandBundle> createCommandBundle(@NonNull BenchmarkExtension extension, @NonNull Parameters parameters) {
        final Policy policy = parameters.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        return Optional.of(CommandBundle.builder()
                                        .add("bm start", policy, new StartBenchmark(extension))
                                        .add("bm stop", policy, extension::stop)
                                        .build());
    }

}
