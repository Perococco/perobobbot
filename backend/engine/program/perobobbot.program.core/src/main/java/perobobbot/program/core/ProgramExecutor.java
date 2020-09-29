package perobobbot.program.core;

import lombok.NonNull;
import perobobbot.common.lang.MessageContext;
import perobobbot.service.core.Services;
import perococco.perobobbot.program.core.PerococcoProgramExecutor;

public interface ProgramExecutor extends ProgramAction {

    static ProgramExecutor create(@NonNull Services services) {
        return PerococcoProgramExecutor.create(services);
    }

    /**
     * call this program executor. The provided context
     * is used to determine the right program to call
     * as well as the parameters to pass to this program
     */
    void handleMessage(@NonNull MessageContext messageContext);

    void stop();

    void start();
}
