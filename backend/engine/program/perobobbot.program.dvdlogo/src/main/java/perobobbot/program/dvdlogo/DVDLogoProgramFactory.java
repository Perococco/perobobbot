package perobobbot.program.dvdlogo;

import lombok.NonNull;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.access.core.PolicyManager;
import perobobbot.common.lang.Role;
import perobobbot.common.messaging.ChatController;
import perobobbot.common.messaging.CommandBundleFactory;
import perobobbot.overlay.Overlay;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.time.Duration;

import static com.google.common.collect.ImmutableList.of;
import static perobobbot.common.messaging.ChatCommand.complex;
import static perobobbot.common.messaging.ChatCommand.simple;

public class DVDLogoProgramFactory extends ProgramFactoryBase {

    public static final String PROGRAM_NAME = "dvdlogo";
    public static final Requirement REQUIREMENT = Requirement.allOf(Overlay.class, ChatController.class);

    public DVDLogoProgramFactory() {
        super(PROGRAM_NAME, REQUIREMENT);
    }

    @Override
    public @NonNull Program create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        final Overlay overlay = services.getService(Overlay.class);
        final ChatController chatController = services.getService(ChatController.class);

        final CommandBundleFactory<DVDLogoProgram> bundlerFactory = CommandBundleFactory.with(
                chatController,
                p -> of(
                        complex("dl",
                                simple("start", policy.createAccessPoint(ctx -> p.startOverlay())),
                                simple("stop", policy.createAccessPoint(ctx -> p.stopOverlay()))
                        )
                )
        );

        return new DVDLogoProgram(PROGRAM_NAME, overlay, bundlerFactory);
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }
}
