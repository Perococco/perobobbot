package perobobbot.benchmark;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.benchmark.action.StartBenchmark;
import perobobbot.command.CommandBundle;
import perobobbot.command.CommandBundleLifeCycle;
import perobobbot.command.CommandRegistry;
import perobobbot.extension.Extension;
import perobobbot.extension.ExtensionFactory;
import perobobbot.extension.ExtensionWithCommands;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;

import java.time.Duration;

@RequiredArgsConstructor
public class BenchmarkExtensionFactory implements ExtensionFactory {

    public static final String NAME = "benchmark";

    private final @NonNull Overlay overlay;
    private final @NonNull PolicyManager policyManager;
    private final @NonNull CommandRegistry commandRegistry;

    @Override
    public @NonNull Extension create(@NonNull String userId) {
        final var benchmarkExtension = new BenchmarkExtension(overlay);
        return new ExtensionWithCommands(benchmarkExtension, createCommandBundleLifeCycle(benchmarkExtension));
    }

    private @NonNull CommandBundleLifeCycle createCommandBundleLifeCycle(@NonNull BenchmarkExtension extension) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        return CommandBundle.builder()
                            .add("bm start", policy, new StartBenchmark(extension))
                            .add("bm stop", policy, extension::stop)
                            .build()
                            .createLifeCycle(commandRegistry);
    }

    @Override
    public @NonNull String getExtensionName() {
        return NAME;
    }
}
