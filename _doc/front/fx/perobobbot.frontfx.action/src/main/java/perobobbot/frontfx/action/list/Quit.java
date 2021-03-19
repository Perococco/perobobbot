package perobobbot.frontfx.action.list;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import perobobbot.lang.ApplicationCloser;

@Component
@RequiredArgsConstructor
public class Quit extends ActionOnNilNoResult {

    @NonNull
    private final ApplicationCloser applicationCloser;

    @Override
    public void execute() {
        applicationCloser.execute();
    }

}
