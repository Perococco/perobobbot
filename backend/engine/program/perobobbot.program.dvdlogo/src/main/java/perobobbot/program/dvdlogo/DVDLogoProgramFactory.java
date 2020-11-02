package perobobbot.program.dvdlogo;

import lombok.NonNull;
import perobobbot.access.core.AccessRule;
import perobobbot.access.core.Policy;
import perobobbot.access.core.PolicyManager;
import perobobbot.common.lang.Role;
import perobobbot.common.messaging.Command;
import perobobbot.overlay.Overlay;
import perobobbot.program.core.ProgramExecutor;
import perobobbot.program.core.ProgramFactory;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.service.core.Requirement;
import perobobbot.service.core.Services;

import java.time.Duration;

import static perobobbot.common.messaging.Command.complex;
import static perobobbot.common.messaging.Command.simple;

public class DVDLogoProgramFactory extends ProgramFactoryBase {

    public static final String PROGRAM_NAME = "dvdlogo";
    public static final Requirement REQUIREMENT = Requirement.allOf(Overlay.class);

    public DVDLogoProgramFactory() {
        super(PROGRAM_NAME, REQUIREMENT);
    }

    @Override
    public @NonNull ProgramFactory.Result create(@NonNull Services services, @NonNull PolicyManager policyManager) {
        final Policy policy = policyManager.createPolicy(AccessRule.create(Role.ADMINISTRATOR, Duration.ofSeconds(1)));
        final Overlay overlay = services.getService(Overlay.class);

        final DVDLogoProgram program = new DVDLogoProgram(PROGRAM_NAME, overlay);

        final Command command = complex("dl",
                                        simple("start", policy.createAccessPoint(ProgramExecutor.with(program, DVDLogoProgram::startOverlay))),
                                        simple("stop", policy.createAccessPoint(ProgramExecutor.with(program, DVDLogoProgram::stopOverlay)))
        );

        return Result.withOneCommand(program, command);
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }
}
