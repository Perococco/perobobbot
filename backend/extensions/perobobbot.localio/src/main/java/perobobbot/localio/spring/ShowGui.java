package perobobbot.localio.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.ExecutionContext;
import perobobbot.common.lang.fp.Consumer1;
import perobobbot.localio.LocalIO;

import java.awt.*;

@RequiredArgsConstructor
public class ShowGui implements Consumer1<ExecutionContext> {

    private final @NonNull LocalIO localIO;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        if (GraphicsEnvironment.isHeadless()) {
            localIO.print("console","No graphic environment for the GUI");
        } else {
            localIO.showGui();
        }
    }
}
