package perobobbot.dvdlogo;

import lombok.NonNull;
import perobobbot.access.AccessRule;
import perobobbot.access.Policy;
import perobobbot.access.PolicyManager;
import perobobbot.common.lang.Role;
import perobobbot.common.messaging.Command;
import perobobbot.overlay.Overlay;
import perobobbot.program.core.ProgramFactory;
import perobobbot.program.core.ProgramFactoryBase;
import perobobbot.services.Requirement;
import perobobbot.services.Services;

import java.time.Duration;

import static perobobbot.common.messaging.Command.complex;
import static perobobbot.common.messaging.Command.simple;
import static perobobbot.program.core.ProgramExecutor.with;

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
                                        simple("start", policy.createAccessPoint(with(program).execute(DVDLogoProgram::startOverlay))),
                                        simple("stop", policy.createAccessPoint(with(program).execute(DVDLogoProgram::stopOverlay)))
        );

        return Result.withOneCommand(program, command);
    }

    @Override
    public boolean isAutoStart() {
        return true;
    }
}
