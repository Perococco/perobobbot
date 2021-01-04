package perobobbot.benchmark.spring;

import com.google.common.collect.ImmutableList;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import perobobbot.access.AccessRule;
import perobobbot.benchmark.BenchmarkExtension;
import perobobbot.benchmark.action.StartBenchmark;
import perobobbot.command.CommandDefinition;
import perobobbot.extension.ExtensionFactoryBase;
import perobobbot.lang.Plugin;
import perobobbot.lang.PluginType;
import perobobbot.lang.Role;

import java.time.Duration;

import static perobobbot.benchmark.action.StartBenchmark.NB_PUCKS_PARAMETER;
import static perobobbot.benchmark.action.StartBenchmark.RADIUS_PARAMETER;

@Component
public class BenchmarkExtensionFactory extends ExtensionFactoryBase<BenchmarkExtension> {

    public static @NonNull Plugin provider() {
        return Plugin.with(PluginType.EXTENSION,"Benchmark", BenchmarkExtensionFactory.class);
    }

    public static final String NAME = "benchmark";

    public BenchmarkExtensionFactory(@NonNull Parameters parameters) {
        super(NAME, parameters);
    }

    @Override
    protected @NonNull BenchmarkExtension createExtension(@NonNull Parameters parameters) {
        return new BenchmarkExtension(parameters.getOverlay());
    }

    @Override
    protected @NonNull ImmutableList<CommandDefinition> createCommandDefinitions(
            @NonNull BenchmarkExtension extension,
            @NonNull Parameters parameters,
            @NonNull CommandDefinition.Factory factory) {
        final var accessRule = AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1));
        return ImmutableList.of(
                factory.create("bm start [%s] [%s]".formatted(NB_PUCKS_PARAMETER, RADIUS_PARAMETER), accessRule, new StartBenchmark(extension)),
                factory.create("bm stop",accessRule,extension::stop)
        );
    }

}
