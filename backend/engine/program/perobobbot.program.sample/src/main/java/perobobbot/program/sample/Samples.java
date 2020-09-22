package perobobbot.program.sample;

import perobobbot.common.lang.AsyncIdentity;
import perobobbot.common.lang.Nil;
import perobobbot.program.core.Program;
import perococco.perobobbot.program.sample.hello.HelloGreeter;
import perococco.perobobbot.program.sample.echo.EchoInstruction;
import perococco.perobobbot.program.sample.hello.HelloMessageHandler;
import perococco.perobobbot.program.sample.hello.HelloState;
import perococco.perobobbot.program.sample.ping.PingInstruction;

public class Samples {

    public static final Program PING = Program.builder(Nil.NIL)
                                              .name("Ping")
                                              .addInstruction(PingInstruction::new)
                                              .build();

    public static final Program ECHO = Program.builder(Nil.NIL)
                                              .name("Echo")
                                              .addInstruction(EchoInstruction::new)
                                              .build();

    public static final Program SAY_HELLO = Program.<AsyncIdentity<HelloState>>builder(AsyncIdentity.create(HelloState.empty()))
                                                   .name("Say Hello")
                                                   .addBackgroundExecution(HelloGreeter::new)
                                                   .setMessageHandler(HelloMessageHandler::new)
                                                   .build();
}
