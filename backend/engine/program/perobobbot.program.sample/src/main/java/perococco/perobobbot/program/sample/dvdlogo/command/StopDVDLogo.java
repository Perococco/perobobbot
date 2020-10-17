package perococco.perobobbot.program.sample.dvdlogo.command;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import perobobbot.common.lang.Role;
import perobobbot.program.core.ChatCommand;
import perobobbot.program.core.ExecutionContext;
import perobobbot.program.core.ExecutionPolicy;
import perococco.perobobbot.program.sample.dvdlogo.DVDLogoAction;

@RequiredArgsConstructor
public class StopDVDLogo implements ChatCommand {

    @Getter
    private final ExecutionPolicy executionPolicy = ExecutionPolicy.builder()
                                                                   .requiredRole(Role.ADMINISTRATOR)
                                                                   .build();

    @Getter
    private final @NonNull String name;

    @NonNull
    private final DVDLogoAction dvdLogoAction;


    @Override
    public void execute(@NonNull ExecutionContext executionContext) {
        dvdLogoAction.stopOverlay();
    }

}
