package perobobbot.program.dvdlogo;

import com.google.common.collect.ImmutableSet;
import lombok.NonNull;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.access.core.PolicyManager;
import perobobbot.chat.core.ChatController;
import perobobbot.common.lang.Role;
import perobobbot.overlay.Overlay;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.time.Duration;

public class DVDLogoProgramFactory extends ProgramFactoryBase {

    public static final String PROGRAM_NAME = "dvdlogo";
    public static final ImmutableSet<Requirement> REQUIREMENTS = ImmutableSet.of(
            Requirement.allOf(Overlay.class, ChatController.class)
    );

    public DVDLogoProgramFactory() {
        super(PROGRAM_NAME,REQUIREMENTS);
    }

    @Override
    public @NonNull Program create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        final Overlay overlay = services.getService(Overlay.class);
        final ChatController chatController = services.getService(ChatController.class);

        return new DVDLogoProgram(PROGRAM_NAME, overlay,chatController,policy);
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }
}
