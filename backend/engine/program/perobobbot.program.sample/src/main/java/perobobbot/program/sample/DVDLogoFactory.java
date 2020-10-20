package perobobbot.program.sample;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.overlay.Overlay;
import perobobbot.program.core.ExecutionPolicy;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactory;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.sample.dvdlogo.DVDLogoExecutor;
import perococco.perobobbot.program.sample.dvdlogo.command.StartDVDLogo;
import perococco.perobobbot.program.sample.dvdlogo.command.StopDVDLogo;

public class DVDLogoFactory implements ProgramFactory {

    public static final String NAME = "dvdlogo";

    @Override
    public @NonNull String programName() {
        return NAME;
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }

    @Override
    public @NonNull ImmutableSet<Class<?>> requiredServices() {
        return ImmutableSet.of(Overlay.class);
    }

    @Override
    public @NonNull Program create(@NonNull Services services) {
        return Program.builder(DVDLogoExecutor::create)
                      .setName(NAME)
                      .setServices(services)
                      .attachChatCommand("!dlstart", StartDVDLogo::new, ExecutionPolicy.ADMINISTRATOR_NO_COOLDOWN)
                      .attachChatCommand("!dlstop", StopDVDLogo::new, ExecutionPolicy.ADMINISTRATOR_NO_COOLDOWN)
                      .build();
    }
}
