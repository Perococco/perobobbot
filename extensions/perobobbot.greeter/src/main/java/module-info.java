import perobobbot.greeter.spring.GreeterExtensionFactory;
import perobobbot.lang.Plugin;

module perobobbot.greeter {
    requires static lombok;
    requires java.desktop;
    requires com.google.common;
    requires org.apache.logging.log4j;

    requires perobobbot.lang;
    requires perobobbot.chat.core;
    requires perobobbot.messaging;
    requires perobobbot.extension;

    requires spring.context;

    opens perobobbot.greeter.spring to spring.core,spring.beans,spring.context;

    provides Plugin with GreeterExtensionFactory;
}
