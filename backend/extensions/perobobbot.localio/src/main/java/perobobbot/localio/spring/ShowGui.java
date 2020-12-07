package perobobbot.localio.spring;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.lang.ExecutionContext;
import perobobbot.lang.fp.Consumer1;
import perobobbot.localio.LocalChat;

import java.awt.*;

@RequiredArgsConstructor
public class ShowGui implements Consumer1<ExecutionContext> {

    private final @NonNull LocalChat localIO;

    @Override
    public void f(@NonNull ExecutionContext executionContext) {
        if (GraphicsEnvironment.isHeadless()) {
            localIO.send(LocalChat.CONSOLE,"No graphic environment for the GUI");
        } else {
            localIO.showGui();
        }
    }
}
