package perococco.perobobbot.program.sample.dvdlogo.command;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.program.core.Execution;
import perobobbot.program.core.ExecutionContext;
import perococco.perobobbot.program.sample.dvdlogo.DVDLogoExecutor;

@RequiredArgsConstructor
public class StopDVDLogo implements Execution {

    @NonNull
    private final DVDLogoExecutor dvdLogoExecutor;


    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        dvdLogoExecutor.stopOverlay();
    }

}
