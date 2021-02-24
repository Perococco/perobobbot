package perobobbot.benchmark;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.benchmark.action.StartBenchmark;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Role;
import perobobbot.overlay.api.Overlay;
import perobobbot.plugin.Requirement;
import perobobbot.plugin.ServiceProvider;

import java.time.Duration;

import static perobobbot.benchmark.action.StartBenchmark.NB_PUCKS_PARAMETER;
import static perobobbot.benchmark.action.StartBenchmark.RADIUS_PARAMETER;

public class BenchmarkExtensionFactory extends ExtensionFactoryBase<BenchmarkExtension> {

    public static final String NAME = "benchmark";

    public BenchmarkExtensionFactory() {
        super(NAME);
    }

    @Override
    public @NonNull ImmutableSet<Requirement> getRequirements() {
        return ImmutableSet.of(Requirement.required(Overlay.class));
    }

    @Override
    protected @NonNull BenchmarkExtension createExtension(@NonNull ServiceProvider serviceProvider) {
        return new BenchmarkExtension(serviceProvider.getService(Overlay.class));
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(
            @NonNull BenchmarkExtension extension,
            @NonNull ServiceProvider serviceProvider,
            @NonNull CommandDefinition.Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("bm [%s] [%s]".formatted(NB_PUCKS_PARAMETER, RADIUS_PARAMETER), accessRule, new StartBenchmark(extension)),
                factory.create("bm stop",accessRule,extension::stop)
        );
    }

}
