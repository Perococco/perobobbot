package perococco.perobobbot.program.core.manager;

import lombok.NonNull;
import perobobbot.program.core.Program;
import perobobbot.program.core.ProxyProgram;
import perococco.perobobbot.program.core.ManagerIdentity;

public class ManagerProgram extends ProxyProgram  {

    public ManagerProgram(@NonNull ManagerIdentity managerIdentity) {
        super(
                Program.builder(managerIdentity)
                       .name("ProgramManager")
                       .addInstruction(StartProgram::new)
                       .addInstruction(StopProgram::new)
                       .addInstruction(ListPrograms::new)
                       .addInstruction(StartAllPrograms::new)
                       .addInstruction(StopAllPrograms::new)
                       .build()
        );
    }
}
