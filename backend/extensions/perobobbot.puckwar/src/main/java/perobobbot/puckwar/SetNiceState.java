package perobobbot.puckwar;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;

@RequiredArgsConstructor
public class SetNiceState implements Consumer1<ExecutionContext> {

    private final @NonNull PuckWarExtension extension;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        extension.setNice(Boolean.parseBoolean(executionContext.getParameters()));
    }
}
