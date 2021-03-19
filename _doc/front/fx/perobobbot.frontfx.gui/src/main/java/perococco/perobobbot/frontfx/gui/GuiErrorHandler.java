package perococco.perobobbot.frontfx.gui;

import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

@Component
public class GuiErrorHandler implements ErrorHandler {

    @Override
    public void handleError(Throwable t) {
        System.err.println(t);
    }
}
