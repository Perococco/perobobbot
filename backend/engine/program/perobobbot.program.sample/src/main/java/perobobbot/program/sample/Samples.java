package perobobbot.program.sample;

import com.google.common.collect.ImmutableSet;
import perobobbot.common.lang.Identity;
import perobobbot.common.lang.IdentityHashSet;
import perobobbot.common.lang.Nil;
import perobobbot.program.core.Program;
import perococco.perobobbot.program.sample.EchoInstruction;
import perococco.perobobbot.program.sample.HelloMessageHandler;
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

    public static final Program SAY_HELLO = Program.<Identity<ImmutableSet<String>>>builder(Identity.create(ImmutableSet.of()))
                                                   .name("Say Hello")
                                                   .setMessageHandler(HelloMessageHandler::new)
                                                   .build();
}
