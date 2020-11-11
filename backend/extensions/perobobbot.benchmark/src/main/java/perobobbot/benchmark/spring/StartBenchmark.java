package perobobbot.benchmark.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.benchmark.BenchmarkExtension;
import perobobbot.common.lang.CastTool;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;

@RequiredArgsConstructor
public class StartBenchmark implements Consumer1<ExecutionContext> {

    private final @NonNull BenchmarkExtension extension;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        final String[] tokens = executionContext.getParameters().split(" +");
        final int nbPucks = parse(tokens,0,200);
        final int radius = parse(tokens,1,20);

        extension.stop();
        extension.start(nbPucks,radius);
    }

    private int parse(@NonNull String[] tokens, int index, int defaultValue) {
        if (tokens.length>=index) {
            return CastTool.castToInt(tokens[index]).orElse(defaultValue);
        }
        return defaultValue;
    }
}
