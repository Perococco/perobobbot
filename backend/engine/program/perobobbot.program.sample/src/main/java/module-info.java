import perobobbot.program.core.ProgramFactory;
import perobobbot.program.sample.EchoFactory;
import perobobbot.program.sample.HelloFactory;
import perobobbot.program.sample.PingFactory;

module perobobbot.program.sample {
    requires static lombok;
    requires java.desktop;
    requires perobobbot.program.core;
    requires perobobbot.common.lang;
    requires perobobbot.chat.advanced;

    requires org.apache.logging.log4j;

    requires com.google.common;
    requires perobobbot.service.core;

    exports perobobbot.program.sample;

    provides ProgramFactory with HelloFactory,PingFactory,EchoFactory;
}