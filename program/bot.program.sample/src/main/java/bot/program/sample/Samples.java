package bot.program.sample;

import bot.common.lang.Nil;
import bot.program.core.Program;
import perococco.bot.program.sample.EchoInstruction;
import perococco.bot.program.sample.PingInstruction;

public class Samples {

    public static final Program PING = Program.create(Nil.NIL)
                                              .name("Ping")
                                              .addInstruction(PingInstruction::new)
                                              .build();

    public static final Program ECHO = Program.create(Nil.NIL)
                                              .name("Echo")
                                              .addInstruction(EchoInstruction::new)
                                              .build();
}
