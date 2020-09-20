package perobobbot.program.sample;

import perobobbot.common.lang.Nil;
import perobobbot.program.core.Program;
import perococco.perobobbot.program.sample.EchoInstruction;
import perococco.perobobbot.program.sample.PingInstruction;

public class Samples {

    public static final Program PING = Program.builder(Nil.NIL)
                                              .name("Ping")
                                              .addInstruction(PingInstruction::new)
                                              .build();

    public static final Program ECHO = Program.builder(Nil.NIL)
                                              .name("Echo")
                                              .addInstruction(EchoInstruction::new)
                                              .build();
}
