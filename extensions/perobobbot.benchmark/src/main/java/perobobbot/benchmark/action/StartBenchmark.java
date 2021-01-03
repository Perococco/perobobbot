package perobobbot.benchmark.action;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.benchmark.BenchmarkExtension;
import perobobbot.command.CommandAction;
import perobobbot.command.CommandParsing;
import perobobbot.lang.ExecutionContext;

@RequiredArgsConstructor
public class StartBenchmark implements CommandAction {

    public static final String NB_PUCKS_PARAMETER = "nbPucks";
    public static final String RADIUS_PARAMETER = "radius";

    private final @NonNull BenchmarkExtension extension;

    @Override
    public void execute(@NonNull CommandParsing parsing, @NonNull ExecutionContext context) {
        final int nbPucks = parsing.findIntParameter(NB_PUCKS_PARAMETER).orElse(200);
        final int radius = parsing.findIntParameter(RADIUS_PARAMETER).orElse(20);
        extension.stop();
        extension.start(nbPucks,radius);
    }

}
